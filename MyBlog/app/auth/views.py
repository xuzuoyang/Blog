import logging
from . import auth
from .. import db
from ..models import User
from .forms import RegistrationForm, LoginForm
from flask import render_template, url_for, flash, jsonify, request, redirect
from flask_login import login_user, logout_user, login_required, current_user


@auth.route('/test/register', methods=['GET', 'POST'])
def test_register():
    logging.warning(request.headers)
    logging.warning(request.form)
    logging.warning(request.args)
    logging.warning(request.values)
    logging.warning(request.json)
    name = request.form.get('name', None)
    if name is not None:
        return 'success'
    else:
        return jsonify(code=0, msg='failure')


@auth.route('/register', methods=['GET', 'POST'])
def register():
    form = RegistrationForm()
    if form.validate_on_submit():
        # logging.warning('post')
        username, email, password = form.username.data, form.email.data, form.password.data
        user = User(username=username, email=email, password=password)
        db.session.add(user)
        flash('Register successful! You can now login.')
        return redirect(url_for('auth.login'))
    logging.warning('method: %s' % request.method)
    return render_template('register.html', form=form)


@auth.route('/login', methods=['GET', 'POST'])
def login():
    form = LoginForm()
    if form.validate_on_submit():
        email, password = form.email.data, form.password.data
        user = User.query.filter_by(email=email).first()
        if user is not None and user.verify_password(password):
            login_user(user, form.remember_me.data)
            return redirect(request.args.get('next') or url_for('main.index'))
        flash('The Password is invalid.')
        return redirect(url_for('auth.login'))
    return render_template('login.html', form=form)


@auth.route('/logout')
@login_required
def logout():
    logout_user()
    flash('You have been logged out.')
    return redirect(url_for('main.index'))


@auth.route('/change-password')
def change_password():
    return render_template('change_password.html')


@auth.route('/users')
def get_users(page='1'):
    pass


@auth.route('/users/<username>')
def get_user(username):
    pass
