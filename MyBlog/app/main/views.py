import logging, json
from enum import Enum
from .. import db
from . import main
from .forms import PostForm, CommentForm
from ..models import User, Post, Permission, Category, Comment
from flask_login import login_required, current_user
from flask import render_template, redirect, url_for, current_app, request, abort, jsonify


@main.route('/')
def index():
    page = request.args.get('page', 1, type=int)
    pagination = Post.query.order_by(Post.timestamp.desc()).paginate(page,
                                                                     per_page=current_app.config['FLASKY_POSTS_PER_PAGE'],
                                                                     error_out=False)
    posts = pagination.items
    return render_template('index.html', posts=posts, pagination=pagination)


@main.route('/blog/<blog_id>', methods=['GET', 'POST'])
@login_required
def blog(blog_id):
    post = Post.query.filter_by(id=blog_id).first()
    form = CommentForm()
    if current_user.can(Permission.COMMENT) and form.validate_on_submit():
        body = form.body.data
        author = current_user._get_current_object()
        comment = Comment(body=body, author=author, post=post)
        db.session.add(comment)
        return redirect(url_for('main.blog', blog_id=blog_id))
    return render_template('blog.html', post=post, form=form)


@main.route('/manage-blog')
@login_required
def manage_blog():
    page = request.args.get('page', 1, type=int)
    pagination = Post.query.order_by(Post.timestamp.desc()).paginate(page,
                                                                     per_page=current_app.config['FLASKY_POSTS_PER_PAGE'],
                                                                     error_out=False)
    posts = pagination.items
    return render_template('manage_blog.html', posts=posts, pagination=pagination)


@main.route('/manage-comment')
@login_required
def manage_comment():
    page = request.args.get('page', 1, type=int)
    pagination = Comment.query.order_by(Comment.timestamp.desc()).paginate(page,
                                                                     per_page=current_app.config['FLASKY_POSTS_PER_PAGE'],
                                                                     error_out=False)
    comments = pagination.items
    return render_template('manage_comment.html', comments=comments, pagination=pagination)


@main.route('/manage-user')
@login_required
def manage_user():
    page = request.args.get('page', 1, type=int)
    pagination = User.query.order_by(User.member_since.desc()).paginate(page,
                                                                     per_page=current_app.config['FLASKY_POSTS_PER_PAGE'],
                                                                     error_out=False)
    users = pagination.items
    return render_template('manage_user.html', users=users, pagination=pagination)


@main.route('/write-blog', methods=['GET', 'POST'])
@login_required
def write_blog():
    # form = PostForm()
    if current_user.can(Permission.ADMINISTER) and request.method == 'POST':
        form = request.form
        title, body = form.get('title', type=str, default=None), form.get('content', type=str, default=None)
        author = current_user._get_current_object()

        category = form.get('category', type=str, default=None)
        category = Category.query.filter_by(name=category).first().id

        post = Post(title=title, category_id=category, body=body, author=author)

        # tags = request.values.get('tags', 5)
        # logging.warning('tags: %s' % tags)

        db.session.add(post)
        return jsonify({'url': url_for('main.manage_blog')})
    url = url_for('main.write_blog')
    return render_template('write_blog.html', form=json.dumps({'url': url}))


@main.route('/edit/<blog_id>', methods=['GET', 'POST'])
@login_required
def edit_blog(blog_id):
    if not current_user.can(Permission.ADMINISTER):
        abort(403)
    post = Post.query.filter_by(id=blog_id).first_or_404()
    if request.method == 'POST':
        form = request.form
        post.title, post.body = form.get('title', type=str, default=None), form.get('content', type=str, default=None)
        post.author = current_user._get_current_object()

        category = form.get('category', type=str, default=None)
        category = Category.query.filter_by(name=category).first().id
        post.category_id = category

        # tags = request.values.get('tags', 5)
        # logging.warning('tags: %s' % tags)

        db.session.add(post)
        return jsonify({'url': url_for('main.manage_blog')})
    url = url_for('main.edit_blog', blog_id=post.id)
    form = {'url': url, 'title': post.title, 'category': post.category_id, 'content': post.body}
    return render_template('write_blog.html', form=json.dumps(form))


@main.route('/delete/<blog_id>')
@login_required
def delete_blog(blog_id):
    if not current_user.can(Permission.ADMINISTER):
        abort(403)
    post = Post.query.filter_by(id=blog_id).first_or_404()
    db.session.delete(post)
    return redirect(url_for('main.manage_blog'))
