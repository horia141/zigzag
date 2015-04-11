"""The single service used for fetching web resources from the external world."""

import logging
import urllib2

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport

import common.defines.constants as defines


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
    def fetch_url_mimetype(self, page_url):
        logging.info('Trying to fetch "%s"' % page_url)

        request = _HeadRequest(page_url, headers=BOT_HEADERS)
        file_obj = urllib2.urlopen(request)
        file_obj.close()

        logging.info('Successfully fetched mimetype for "%s"' % page_url)

        return file_obj.info().gettype()


def main():
    logging.basicConfig(level=logging.INFO, filename=defines.FETCHER_LOG_PATH)

    ser = serializer.Serializer()
    fetcher_service = Service()

    server = transport.Server(defines.FETCHER_PORT, ser)
    server.add_service(fetcher_service)
    server.start()

if __name__ == '__main__':
    main()
