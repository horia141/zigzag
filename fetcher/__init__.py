"""The single service used for fetching web resources from the external world."""

import argparse
import logging
import urllib2

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport

import common.defines.constants as defines
import utils.pidfile as pidfile


BOT_HEADERS = {
    'User-Agent': 'ZigZagBot'
}


class _HeadRequest(urllib2.Request):
    def get_method(self):
        return 'HEAD'


class Service(comlink.Service):
    @comlink.call
    def fetch_url(self, page_url):
        logging.info('Trying to fetch "%s"' % page_url)

        request = urllib2.Request(page_url, headers=BOT_HEADERS)
        file_obj = urllib2.urlopen(request)
        raw_content = file_obj.read()
        file_obj.close()

        logging.info('Successfully fetched "%s"' % page_url)

        return (raw_content, file_obj.info().gettype())

    @comlink.call
    def fetch_photo(self, photo_url):
        logging.info('Trying to fetch photo "%s"' % photo_url)

        request = urllib2.Request(photo_url, headers=BOT_HEADERS)
        file_obj = urllib2.urlopen(request)
        content_length = int(file_obj.headers['content-length'], 10)
        if content_length > defines.MAXIMUM_FETCHED_PHOTO_SIZE_IN_BYTES:
            raise IOError('Photo at "%s" is too large at %d bytes' % (photo_url, content_length))
        raw_content = file_obj.read()
        file_obj.close()

        logging.info('Successfully fetched photo "%s"' % photo_url)

        return (raw_content, file_obj.info().gettype())

    @comlink.call
    def fetch_url_mimetype(self, page_url):
        logging.info('Trying to fetch "%s"' % page_url)

        request = _HeadRequest(page_url, headers=BOT_HEADERS)
        file_obj = urllib2.urlopen(request)
        file_obj.close()

        logging.info('Successfully fetched mimetype for "%s"' % page_url)

        return file_obj.info().gettype()


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--port', type=int, required=True,
        help='Port on which the Comlink server is listening')
    parser.add_argument('--log_path', type=str, required=True,
        help='Path to the log file')
    parser.add_argument('--pidfile_path', type=str, required=True,
        help='Path for the pidfile')
    args = parser.parse_args()

    pidfile.write_pidfile(args.pidfile_path)

    logging.basicConfig(level=logging.INFO, filename=args.log_path)

    ser = serializer.Serializer()
    fetcher_service = Service()

    server = transport.Server(args.port, ser)
    server.add_service(fetcher_service)
    server.start()

if __name__ == '__main__':
    main()
