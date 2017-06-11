#!/usr/bin/env python3
# -*- coding:utf-8 -*-
"""Web framework components.

Encapsulate request handler and router functions of aiohttp.
"""
import os
import logging
import functools
import inspect
import asyncio
from inspect import Parameter
from urllib import parse
from aiohttp import web
from apis import APIError

logging.basicConfig(level=logging.INFO)


class RequestHandler:
    """Unified request handler interface

    Provide a class to acquire and justify params for handler functions.
    And construct the third parameter of app.router.add_route function of aiohttp.

    Attributes:
        _function: coroutine handler function referring to specific path.
    """
    def __init__(self, function):
        """Initiate request handler."""
        self._function = asyncio.coroutine(function)

    async def __call__(self, request):
        """make a callable function."""
        required_args = inspect.signature(self._function).parameters  # required arguments of the handler function.
        logging.info('required args: %s' % required_args)

        # make params
        kw = {arg: value for arg, value in request.__data__.items() if arg in required_args}  # params for the handler function.
        kw.update(request.match_info)  # params in route path such as '/blog/{id}'.
        if 'request' in required_args:  # request object itself for user cookie and etc.
            kw['request'] = request

        # check params
        for key, arg in required_args.items():
            if key == 'request' and arg.kind in (Parameter.VAR_POSITIONAL, Parameter.VAR_KEYWORD):
                return web.HTTPBadRequest(text='request parameter cannot be the var argument.')
            if arg.kind not in (Parameter.VAR_POSITIONAL, Parameter.VAR_KEYWORD):
                if arg.default == Parameter.empty and arg.name not in kw:
                    return web.HTTPBadRequest(text='Missing argument: %s' % arg.name)

        logging.info('call with args: %s' % kw)
        try:
            return await self._function(**kw)  # execute handler function.
        except APIError as e:
            return dict(error=e.error, data=e.data, message=e.message)


def get(path):
    """Define decorator @get('/path').

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
    """Define decorator @post('/path').

    """
    def decorator(function):

        @functools.wraps(function)
        def wrapper(*args, **kwargs):
            return function(*args, **kwargs)
        wrapper.__method__ = 'POST'
        wrapper.__route__ = path
        return wrapper

    return decorator


def add_static(app):
    """app.router.add_static('/prefix', path_to_static_folder).

    """
    path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'static')
    app.router.add_static('/static/', path)
    logging.info('add static %s => %s' % ('/static/', path))


def add_route(app, function):
    """app.router.add_route('*', '/path', all_handler).

    """
    method = getattr(function, '__method__', None)
    path = getattr(function, '__route__', None)
    if path is None or method is None:
        raise ValueError('@get or @post not defined in %s.' % str(function))
    if not asyncio.iscoroutinefunction(function) and not inspect.isgeneratorfunction(function):
        function = asyncio.coroutine(function)
    logging.info('add route %s %s => %s(%s)' % (method, path, function.__name__, ', '.join(inspect.signature(function).parameters.keys())))
    app.router.add_route(method, path, RequestHandler(function))


def add_routes(app, module_name):
    """Iterate functions in handlers module and add handler functions to app.router.

    """
    n = module_name.rfind('.')
    if n == (-1):
        mod = __import__(module_name, globals(), locals())
    else:
        name = module_name[n + 1:]
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
