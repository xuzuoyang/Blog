from . import auth
from .. import db
from ..models import User
from flask import render_template, redirect, request, url_for, flash, current_app
from flask_login import login_user, logout_user, login_required, current_user


@auth.route('/register', methods=['GET', 'POST'])
def register():
    username = request.form.get('username')
    if username:
        flash('valid username: {}'.format(username))
        return redirect(url_for('auth.login'))
    return render_template('register.html')


@auth.route('/login')
def login():
    return render_template('login.html')
