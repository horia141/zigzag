"""Common Thrift RPC and proper handling for it."""

from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket
from thrift.transport import TTransport

class to(object):
    def __init__(self, service_class, host, port):
        self._service_class = service_class
        self._host = host
        self._port = port
        self._transport = None

    def __enter__(self):
        self._transport = TSocket.TSocket(self._host, self._port)
        self._transport = TTransport.TBufferedTransport(self._transport)
        protocol = TBinaryProtocol.TBinaryProtocol(self._transport)
        client = self._service_class.Client(protocol)
        self._transport.open()
        return client

    def __exit__(self, type, value, traceback):
        self._transport.close()
        return False
