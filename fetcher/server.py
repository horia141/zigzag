"""The single service used for fetching web resources from the external world."""

import argparse
import logging
import urllib2

from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.server import TServer

import common.defines.constants as defines
import fetcher
import fetcher.Service
import fetcher.ttypes as fetcher_types
import utils.pidfile as pidfile


BOT_HEADERS = {
    'User-Agent': 'ZigZagBot'
}


class _HeadRequest(urllib2.Request):
    def get_method(self):
        return 'HEAD'


class ServiceHandler(object):
    def __init__(self):
        pass

    def fetch_url(self, page_url):
        logging.info('Trying to fetch "%s"' % page_url)

        try:
            request = urllib2.Request(page_url, headers=BOT_HEADERS)
            file_obj = urllib2.urlopen(request)
            content = file_obj.read()
            mime_type = file_obj.info().gettype()
            file_obj.close()
        except (ValueError, IOError) as e:
            raise fetcher_types.IOError(message=str(e))

        logging.info('Successfully fetched "%s"' % page_url)

        return fetcher_types.Response(mime_type=mime_type, content=content)

    def fetch_url_mimetype(self, page_url):
        logging.info('Trying to fetch "%s"' % page_url)

        try:
            request = _HeadRequest(page_url, headers=BOT_HEADERS)
            file_obj = urllib2.urlopen(request)
            mime_type = file_obj.info().gettype()
            file_obj.close()
        except (ValueError, IOError) as e:
            raise fetcher_types.IOError(message=str(e))

        logging.info('Successfully fetched mimetype for "%s"' % page_url)

        return fetcher_types.Response(mime_type=mime_type)

    def fetch_photo(self, photo_url):
        logging.info('Trying to fetch photo "%s"' % photo_url)

        try:
            request = urllib2.Request(photo_url, headers=BOT_HEADERS)
            file_obj = urllib2.urlopen(request)
            content_length = int(file_obj.headers['content-length'], 10)
            if content_length > defines.MAXIMUM_FETCHED_PHOTO_SIZE_IN_BYTES:
                raise fetcher_types.PhotoTooBigError(message='Photo at "%s" is too large at %d bytes' % (photo_url, content_length))
            content = file_obj.read()
            mime_type = file_obj.info().gettype()
            file_obj.close()
        except (ValueError, IOError) as e:
            raise fetcher_types.IOError(message=str(e))

        logging.info('Successfully fetched photo "%s"' % photo_url)

        return fetcher_types.Response(mime_type=mime_type, content=content)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--host', type=str, required=True,
        help='Host on which the server is listening')
    parser.add_argument('--port', type=int, required=True,
        help='Port on which the server is listening')
    parser.add_argument('--log_path', type=str, required=True,
        help='Path to the log file')
    parser.add_argument('--pidfile_path', type=str, required=True,
        help='Path for the pidfile')
    args = parser.parse_args()

    pidfile.write_pidfile(args.pidfile_path)

    logging.basicConfig(level=logging.INFO, filename=args.log_path)

    service_handler = ServiceHandler()
    processor = fetcher.Service.Processor(service_handler)
    transport = TSocket.TServerSocket(host=args.host, port=args.port)
    transport_factory = TTransport.TBufferedTransportFactory()
    protocol_factory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, transport_factory, protocol_factory)
    server.serve()


if __name__ == '__main__':
    main()
