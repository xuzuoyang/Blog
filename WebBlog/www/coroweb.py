import os
import logging
logging.basicConfig(level=logging.INFO)
import functools
import inspect
import asyncio
from inspect import Parameter
from urllib import parse
from aiohttp import web
from www.apis import APIError


def get(path):
    """
    Define decorator @get('/path')
    :param path:
    :return:
    """
    def decorator(function):

        @functools.wraps(function)
        def wrapper(*args, **kwargs):
            return function(*args, **kwargs)
        wrapper.__method__ = 'GET'
        wrapper.__route__ = path
        return wrapper

    return decorator


def post(path):
    """
    Define decorator @post('/path')
    :param path:
    :return:
    """
    def decorator(function):

        @functools.wraps(function)
        def wrapper(*args, **kwargs):
            return function(*args, **kwargs)
        wrapper.__method__ = 'POST'
        wrapper.__route__ = path
        return wrapper

    return decorator


class RequestHandler:

    def __init__(self, function):
        self._function = asyncio.coroutine(function)

    async def __call__(self, request):
        required_args = inspect.signature(self._function).parameters
        logging.info('required args: %s' % required_args)

        kw = {arg: value for arg, value in request.__data__.items() if arg in required_args}

        kw.update(request.match_info)

        if 'request' in required_args:
            kw['request'] = request

        for key, arg in required_args.items():
            logging.info('start checking...')
            logging.info(key)
            logging.info(arg)
            if key == 'request' and arg.kind in (Parameter.VAR_POSITIONAL, Parameter.VAR_KEYWORD):
                logging.info('bad1...')
                return web.HTTPBadRequest(text='request parameter cannot be the var argument.')
            if arg.kind not in (Parameter.VAR_POSITIONAL, Parameter.VAR_KEYWORD):
                if arg.default == Parameter.empty and arg.name not in kw:
                    logging.info('bad2...')
                    return web.HTTPBadRequest(text='Missing argument: %s' % arg.name)

        logging.info('call with args: %s' % kw)
        try:
            return await self._function(**kw)
        except APIError as e:
            return dict(error=e.error, data=e.data, message=e.message)


def add_static(app):
    path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'static')
    app.router.add_static('/static/', path)
    logging.warning('add static %s => %s' % ('/static/', path))


def add_route(app, function):
    method = getattr(function, '__method__', None)
    path = getattr(function, '__route__', None)
    if path is None or method is None:
        raise ValueError('@get or @post not defined in %s.' % str(function))
    if not asyncio.iscoroutinefunction(function) and not inspect.isgeneratorfunction(function):
        function = asyncio.coroutine(function)
    logging.info(
        'add route %s %s => %s(%s)' % (method, path, function.__name__, ', '.join(inspect.signature(function).parameters.keys())))
    app.router.add_route(method, path, RequestHandler(function))


def add_routes(app, module_name):
    """前面是从可能很长的路径中解析模块的名称,得到mod之后,再从mod中循环查找function并注册
    """
    n = module_name.rfind('.')
    if n == (-1):
        mod = __import__(module_name, globals(), locals())
    else:
        name = module_name[n+1:]
        mod = getattr(__import__(module_name[:n], globals(), locals(), [name]), name)
    for attr in dir(mod):
        if attr.startswith('_'):
            continue
        fn = getattr(mod, attr)
        if callable(fn):
            method = getattr(fn, '__method__', None)
            path = getattr(fn, '__route__', None)
            if method and path:
                add_route(app, fn)



























