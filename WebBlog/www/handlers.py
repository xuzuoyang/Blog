import re
import time
import json
import logging
logging.basicConfig(level=logging.INFO)
import hashlib
import base64
import asyncio
from www.apis import *
from www.coroweb import *
from www.models.models import *
from www.confs.config import *


@get('/blog')
def handler_url_blog():
    return web.Response(body=b'<h1>Awesome: /blog</h1>', content_type='text/html', charset='UTF-8')


@get('/')
def handler_url_index():
    summary = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
    blogs = [
        Blog(id='1', name='Test Blog', summary=summary, created_at=time.time() - 120),
        Blog(id='2', name='Something New', summary=summary, created_at=time.time() - 3600),
        Blog(id='3', name='Learn Swift', summary=summary, created_at=time.time() - 7200)
    ]
    return {
        '__template__': 'blogs.html',
        'blogs': blogs
    }


@get('/register')
def register():
    logging.info('register handler function')
    return {'__template__': 'register.html'}


@get('/signin')
def signin():
    return {'__template__': 'signin.html'}


_RE_EMAIL = re.compile(r'^[a-z0-9\.\-\_]+\@[a-z0-9\-\_]+(\.[a-z0-9\-\_]+){1,4}$')
_RE_SHA1 = re.compile(r'^[0-9a-f]{40}$')
COOKIE_NAME = 'awesession'
_COOKIE_KEY = configs.session.secret


def user2cookie(user, max_age):
    """Generate cookie string by user
       in the format of id-expires-sha1(id, password, expires, secret_key)
    """
    expires = str(int(time.time() + max_age))
    s = '%s-%s-%s-%s' % (user.id, user.password, expires, _COOKIE_KEY)
    cookie = [user.id, expires, hashlib.sha1(s.encode('utf-8')).hexdigest()]
    return '-'.join(cookie)


@asyncio.coroutine
def cookie2user(cookie):
    """parse cookie and load user if cookie is valid"""
    if not cookie:
        return None
    try:
        s = cookie.split('-')
        if len(s) != 3:
            return None
        uid, expires, sha1 = s
        if int(expires) < time.time():
            return None
        user = yield from User.find(uid)
        if user is None:
            return None
        string = '%s-%s-%s-%s' % (uid, user.password, expires, _COOKIE_KEY)
        if sha1 != hashlib.sha1(string.encode('utf-8')).hexdigest():
            logging.info('invalid sha1')
            return None
        user.password = '******'
    except Exception as e:
        logging.exception(e)
        return None


@post('/api/users')
async def api_register_user(*, email, name, password):
    logging.warning('invoking register api...')
    if not name or not name.strip():
        raise APIError('name')
    if not email or not _RE_EMAIL.match(email):
        raise APIError('email')
    if not password or not _RE_SHA1.match(password):
        raise APIError('password')
    users = await User.findAll('email=?', [email])
    if len(users) > 0:
        raise APIError('register:failed', 'email', 'Email is already in use.')
    # save user to db
    uid = next_id()
    sha1_password = '%s:%s' %(uid, password)
    user = User(id=uid, name=name.strip(), email=email,
                password=hashlib.sha1(sha1_password.encode('utf-8')).hexdigest(),
                image='http://www.gravatar.com/avatar/%s?d=mm&s=120' % hashlib.md5(email.encode('utf-8')).hexdigest())
    await user.save()
    # make session cookie
    response = web.Response()
    response.set_cookie(COOKIE_NAME, user2cookie(user, 86400), max_age=86400, httponly=True)
    user.password = '******'
    response.content_type = 'application/json'
    response.body = json.dumps(user, ensure_ascii=False).encode('utf-8')
    return response


@post('/api/authenticate')
async def authenticate(*, email, password):
    if not email:
        raise APIError('email', 'Invalid email')
    if not password:
        raise APIError('password', 'Invalid password')
    users = await User.findAll('email=?', [email])
    if len(users) == 0:
        raise APIValueError('email', 'Email not exist.')
    # always will be first one
    user = users[0]
    sha1 = hashlib.sha1()
    sha1.update(user.id.encode('utf-8'))
    sha1.update(b':')
    sha1.update(password.encode('utf-8'))
    if user.password != sha1.hexdigest():
        raise APIValueError('password', 'Invalid password.')
    response = web.Response()
    response.set_cookie(COOKIE_NAME, user2cookie(user, 86400), max_age=86400, httponly=True)
    user.password = '******'
    response.content_type = 'application/json'
    response.body = json.dumps(user, ensure_ascii=False).encode('utf-8')
    return response


@get('/signout')
def signout(request):
    referer = request.headers.get('Referer')
    response = web.HTTPFound(referer or '/')
    response.set_cookie(COOKIE_NAME, '-deleted-', max_age=0, httponly=True)
    logging.info('user signed out.')
    return response












