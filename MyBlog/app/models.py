import logging, hashlib, forgery_py
from datetime import datetime
from random import seed, randint
from sqlalchemy.exc import IntegrityError
from werkzeug.security import generate_password_hash, check_password_hash
from itsdangerous import TimedJSONWebSignatureSerializer as Serializer
from flask_login import UserMixin, AnonymousUserMixin
from . import db, login_manager
from flask import current_app, request

logger = logging.getLogger('root')


class Permission:
    READ = 0x01
    COMMENT = 0x02
    ADMINISTER = 0x04


class Role(db.Model):
    __tablename__ = 'roles'
    mysql_charset='utf8'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(64), unique=True)
    default = db.Column(db.Boolean, default=False, index=True)
    permissions = db.Column(db.Integer)
    users = db.relationship('User', backref='role')

    @staticmethod
    def insert_roles():
        logger.info('Start inserting roles...')
        roles = {
            'User': (Permission.READ | Permission.COMMENT, True),
            'Administrator': (Permission.READ | Permission.COMMENT | Permission.ADMINISTER, False)
        }
        for r in roles:
            role = Role.query.filter_by(name=r).first()
            if role is None:
                role = Role(name=r)
            role.permissions = roles[r][0]
            role.default = roles[r][1]
            db.session.add(role)
        db.session.commit()
        logger.info('Finish inserting roles.')

    def __repr__(self):
        return 'Role <%r>' % self.name


class PostThumbing(db.Model):
    __tablename__ = 'postthumbs'
    post_id = db.Column(db.Integer, db.ForeignKey('posts.id'), primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('users.id'), primary_key=True)
    timestamp = db.Column(db.DateTime, default=datetime.utcnow)


class CommentThumbing(db.Model):
    __tablename__ = 'commentthumbs'
    comment_id = db.Column(db.Integer, db.ForeignKey('comments.id'), primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('users.id'), primary_key=True)
    timestamp = db.Column(db.DateTime, default=datetime.utcnow)


class User(UserMixin, db.Model):
    __tablename__ = 'users'
    mysql_charset = 'utf8'

    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(64), unique=True, index=True)
    username = db.Column(db.String(64, collation='utf8_bin'), unique=True, index=True)
    password_hash = db.Column(db.String(128))
    role_id = db.Column(db.Integer, db.ForeignKey('roles.id'))
    posts = db.relationship('Post', backref='author', lazy='dynamic')
    comments = db.relationship('Comment', backref='author', lazy='dynamic')
    messages = db.relationship('Message', backref='author', lazy='dynamic')
    avatar_hash = db.Column(db.String(32))
    member_since = db.Column(db.DateTime(), default=datetime.utcnow)
    post_thumb_up = db.relationship('PostThumbing', foreign_keys=[PostThumbing.user_id],
                            backref=db.backref('user', lazy='joined'), lazy='dynamic',
                            cascade='all, delete-orphan')
    comment_thumb_up = db.relationship('CommentThumbing', foreign_keys=[CommentThumbing.user_id],
                            backref=db.backref('user', lazy='joined'), lazy='dynamic',
                            cascade='all, delete-orphan')

    def __init__(self, **kwargs):
        logger.info('Start initiating user...')
        super(User, self).__init__(**kwargs)
        if self.role is None:
            if self.email == current_app.config['FLASKY_ADMIN']:
                logger.info('Initiating user as admin...')
                self.role = Role.query.filter_by(permissions=0x07).first()
            if self.role is None:
                self.role = Role.query.filter_by(default=True).first()
        if self.email is not None and self.avatar_hash is None:
            self.avatar_hash = hashlib.md5(self.email.encode('utf-8')).hexdigest()
        logger.info('Finish initiating user of {}'.format(self.role))

    @property
    def password(self):
        raise AttributeError('Unreadable attribute!')

    @password.setter
    def password(self, password):
        self.password_hash = generate_password_hash(password)

    def verify_password(self, password):
        return check_password_hash(self.password_hash, password)

    def can(self, permissions):
        return self.role is not None and (self.role.permissions & permissions) == permissions

    def is_administrator(self):
        return self.can(Permission.ADMINISTER)

    @staticmethod
    def generate_fake(count=100):
        seed()
        for i in range(count):
            u = User(email=forgery_py.internet.email_address(),
                     username=forgery_py.internet.user_name(True),
                     password=forgery_py.lorem_ipsum.word(),
                     member_since=forgery_py.date.date(True),
                     role_id=2)
            db.session.add(u)
            try:
                db.session.commit()
            except IntegrityError:
                db.session.rollback()

    def gravatar(self, size=100, default='identicon', rating='g'):
        logger.info('Generating gravatar url for {}.'.format(self))
        if request.is_secure:
            url = 'https://secure.gravatar.com/avatar'
        else:
            url = 'http://www.gravatar.com/avatar'
        hash = self.avatar_hash or hashlib.md5(self.email.encode('utf-8')).hexdigest()
        return '{url}/{hash}?s={size}&d={default}&r={rating}'.format(url=url, hash=hash,
                                                                     size=size, default=default, rating=rating)

    def is_thumbing_post(self, post):
        return self.post_thumb_up.filter_by(post_id=post.id).first() is not None

    def thumb_post(self, post):
        logger.info('User {} is thumbing post {}.'.format(self.username, post.id))
        if not self.is_thumbing_post(post):
            t = PostThumbing(post_id=post.id, user_id=self.id)
            db.session.add(t)

    def is_thumbing_comment(self, comment):
        return self.comment_thumb_up.filter_by(comment_id=comment.id).first() is not None

    def thumb_comment(self, comment):
        logger.info('User {} is thumbing comment {}.'.format(self.username, comment.id))
        if not self.is_thumbing_comment(comment):
            t = CommentThumbing(comment_id=comment.id, user_id=self.id)
            db.session.add(t)
        else:
            t = CommentThumbing.query.filter_by(comment_id=comment.id, user_id=self.id).first()
            db.session.delete(t)

    def __repr__(self):
        return 'User <%r>' % self.username


class AnonymousUser(AnonymousUserMixin):
    def can(self, permissions):
        return False

    def is_administrator(self):
        return False


login_manager.anonymous_user = AnonymousUser


@login_manager.user_loader
def load_user(user_id):
    return User.query.get(int(user_id))


class Tagging(db.Model):
    __tablename__ = 'taggings'
    post_id = db.Column(db.Integer, db.ForeignKey('posts.id'), primary_key=True)
    tag_id = db.Column(db.Integer, db.ForeignKey('tags.id'), primary_key=True)
    timestamp = db.Column(db.DateTime, default=datetime.utcnow)


class Post(db.Model):
    __tablename__ = 'posts'
    mysql_charset='utf8'

    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(128, collation='utf8_bin'))
    category_id = db.Column(db.Integer, db.ForeignKey('categories.id'))
    body = db.Column(db.Text(collation='utf8_bin'))
    author_id = db.Column(db.Integer, db.ForeignKey('users.id'))
    timestamp = db.Column(db.DateTime, index=True, default=datetime.utcnow)
    last_edit = db.Column(db.DateTime, index=True, default=datetime.utcnow)
    comments = db.relationship('Comment', backref='post', lazy='dynamic')
    tagging = db.relationship('Tagging', foreign_keys=[Tagging.post_id],
                           backref=db.backref('post', lazy='joined'), lazy='dynamic',
                           cascade='all, delete-orphan')
    thumb_up = db.relationship('PostThumbing', foreign_keys=[PostThumbing.post_id],
                               backref=db.backref('post', lazy='joined'), lazy='dynamic',
                               cascade='all, delete-orphan')

    @staticmethod
    def generate_fake(count=100):
        seed()
        user_count = User.query.count()
        for i in range(count):
            u = User.query.offset(randint(0, user_count - 1)).first()
            p = Post(title=forgery_py.lorem_ipsum.title(),
                     body=forgery_py.lorem_ipsum.sentences(quantity=20),
                     timestamp=forgery_py.date.date(True),
                     last_edit=forgery_py.date.date(True),
                     category_id=randint(1, 4),
                     author=u)
            db.session.add(p)
            try:
                db.session.commit()
            except IntegrityError:
                db.session.rollback()

    def refresh(self):
        logger.info('Editing blog {}.'.format(self.id))
        last_edit = datetime.utcnow()
        db.session.add(self)


class Tag(db.Model):
    __tablename__ = 'tags'
    mysql_charset='utf8'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(128, collation='utf8_bin'))
    tagging = db.relationship('Tagging', foreign_keys=[Tagging.tag_id],
                            backref=db.backref('tag', lazy='joined'), lazy='dynamic',
                            cascade='all, delete-orphan')

    def is_tagging(self, post):
        return self.tagging.filter_by(post_id=post.id).first() is not None

    def tag_post(self, post):
        logger.info('Tagging post {} as {}.'.format(post.id, self.name))
        if not self.is_tagging(post):
            t = Tagging(post_id=post.id, tag_id=self.id)
            db.session.add(t)


class Category(db.Model):
    __tablename__ = 'categories'
    mysql_charset='utf8'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(128))
    label = db.Column(db.String(128))
    posts = db.relationship('Post', backref='category')

    @staticmethod
    def insert_categories():
        logger.info('Starting inserting categories...')
        data = [('tech', '技术'), ('life', '生活'), ('other', '其他')]
        for item in data:
            category = Category()
            category.name = item[0]
            category.label = item[1]
            db.session.add(category)
        db.session.commit()
        logger.info('Finish inserting categories.')


class Comment(db.Model):
    __tablename__ = 'comments'
    mysql_charset='utf8'

    id = db.Column(db.Integer, primary_key=True)
    body = db.Column(db.Text(collation='utf8_bin'))
    timestamp = db.Column(db.DateTime, default=datetime.utcnow)
    author_id = db.Column(db.Integer, db.ForeignKey('users.id'))
    post_id = db.Column(db.Integer, db.ForeignKey('posts.id'))
    parent_id = db.Column(db.Integer)
    thumb_up = db.relationship('CommentThumbing', foreign_keys=[CommentThumbing.comment_id],
                               backref=db.backref('comment', lazy='joined'), lazy='dynamic',
                               cascade='all, delete-orphan')


class Message(db.Model):
    __tablename__ = 'messages'
    mysql_charset = 'utf-8'

    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(128, collation='utf8_bin'))
    body = db.Column(db.Text(collation='utf8_bin'))
    timestamp = db.Column(db.DateTime, default=datetime.utcnow)
    author_id = db.Column(db.Integer, db.ForeignKey('users.id'))
