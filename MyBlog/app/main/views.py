import logging
from .. import db
from . import main
from .forms import PostForm
from ..models import User, Post, Permission, Type
from flask_login import login_required, current_user
from flask import render_template, redirect, url_for, current_app, request, abort

@main.route('/')
def index():
    page = request.args.get('page', 1, type=int)
    pagination = Post.query.order_by(Post.timestamp.desc()).paginate(page,
                                                                     per_page=current_app.config['FLASKY_POSTS_PER_PAGE'],
                                                                     error_out=False)
    posts = pagination.items
    return render_template('index.html', posts=posts, pagination=pagination)


@main.route('/blog/<blog_id>')
def blog(blog_id):
    post = Post.query.filter_by(id=blog_id).first()
    return render_template('blog.html', post=post)


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
    return render_template('manage_comment.html')


@main.route('/write-blog', methods=['GET', 'POST'])
@login_required
def write_blog():
    form = PostForm()
    if current_user.can(Permission.ADMINISTER) and form.validate_on_submit():
        title, body = form.title.data, form.body.data
        author = current_user._get_current_object()
        type = form.type.data
        # logging.warning(type)
        type = Type.query.filter_by(name=type).first()
        post = Post(title=title, type=type, body=body, author=author)
        db.session.add(post)
        return redirect(url_for('main.blog'))
    return render_template('write_blog.html', form=form)


@main.route('/edit/<blog_id>', methods=['GET', 'POST'])
@login_required
def edit_blog(blog_id):
    post = Post.query.filter_by(id=blog_id).first_or_404()
    if not current_user.can(Permission.ADMINISTER):
        abort(403)
    form = PostForm()
    if form.validate_on_submit():
        post.title, post.body = form.title.data, form.body.data
        type = form.type.data
        post.type_id = Type.query.filter_by(name=type).first().id
        db.session.add(post)
        return redirect(url_for('main.blog', blog_id=post.id))
    form.title.data, form.type.data, form.body.data = post.title, post.type, post.body
    return render_template('write_blog.html', form=form)
