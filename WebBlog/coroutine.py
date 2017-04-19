import threading
import asyncio


@asyncio.coroutine
def hello():
    print('Hello world! (%s)' % threading.current_thread())
    yield from asyncio.sleep(1)
    print('Hello again! (%s)' % threading.current_thread())

# loop = asyncio.get_event_loop()
# tasks = [hello(), hello()]
# loop.run_until_complete(asyncio.wait(tasks))
# loop.close()


@asyncio.coroutine
def wget(host):
    print('wet %s' % host)
    connect = asyncio.open_connection(host, 80)
    reader, writer = yield from connect
    header = 'GET / HTTP/1.0\r\nHost: %s\r\n\r\n' % host
    writer.write(header.encode('utf-8'))
    yield from writer.drain()
    while True:
        line = yield from reader.readline()
        yield from asyncio.sleep(2)
        if line == '\r\n':
            break
        print('%s header > %s' % (host, line.decode('utf-8').rstrip()))
    writer.close()

loop = asyncio.get_event_loop()
tasks = [wget(host) for host in ['www.sina.com.cn', 'www.sohu.com', 'www.163.com']]
loop.run_until_complete(asyncio.wait(tasks))
loop.close()