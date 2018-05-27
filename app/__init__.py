#!/usr/bin/env python3
import logging, logging.config
from flask import Flask
from flask_bootstrap import Bootstrap
from flask_sqlalchemy import SQLAlchemy
from flask_moment import Moment
from flask_login import LoginManager
from flask_debugtoolbar import DebugToolbarExtension
from confs.config import config

bootstrap = Bootstrap()
db = SQLAlchemy()
moment = Moment()
login_manager = LoginManager()
login_manager.session_protection = 'strong'
login_manager.login_view = 'auth.login'
toolbar = DebugToolbarExtension()


def create_app(config_name):
    app = Flask(__name__)
    app.config.from_object(config[config_name])

    logging.config.dictConfig({
        'version': 1,
        'formatters': {'default': {
            'format': '%(asctime)s : %(levelname)s : %(module)s : %(funcName)s : %(message)s',
        }},
        'handlers': {
            'h1': {
                # The values below are popped from this dictionary and
                # used to create the handler, set the handler's level and
                # its formatter.
                'class': 'logging.FileHandler',
                'level': 'INFO',
                'formatter': 'default',
                # The values below are passed to the handler creator callable
                # as keyword arguments.
                'filename': './logs/log.txt',
                'mode': 'a+',
                'encoding': 'utf-8'
            },
            'h2': {
                'class': 'logging.StreamHandler',
                'level': 'INFO',
                'formatter': 'default'
            }
        },
        'root': {
            'level': 'INFO',
            'handlers': ['h1', 'h2']
        }
    })
    logging.getLogger().info('logger initialization & configuration done.')

    bootstrap.init_app(app)
    db.init_app(app)
    moment.init_app(app)
    login_manager.init_app(app)
    # toolbar.init_app(app)

    from .main import main as main_blueprint
    app.register_blueprint(main_blueprint)

    from .auth import auth as auth_blueprint
    app.register_blueprint(auth_blueprint, url_prefix='/auth')

    return app
