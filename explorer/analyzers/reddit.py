"""The Reddit analyzer."""

import logging
import urllib2

import bs4 as bs

import common.defines.constants as defines
import explorer.analyzers as analyzers
import explorer.analyzers.imgur as imgur
import fetcher_pb.Service
import fetcher_pb.ttypes as fetcher_types
import utils.rpc as rpc


class Analyzer(analyzers.Analyzer):
    """Class for performing analysis of the Reddit artifact source."""

    def __init__(self, source, fetcher_host, fetcher_port):
        super(Analyzer, self).__init__(source, fetcher_host, fetcher_port)
        # self._imgur_analyzer = imgur.Analyzer(defines.ARTIFACT_SOURCES[2], fetcher_host, fetcher_port)

    def analyze(self):
        logging.info('Analyzing Reddit')

        initial_artifact_links = []

        for category in self.source.subdomains:
            logging.info('Analyzing category "%s"', category)

            category_url = self.source.start_page_uri % category
            logging.info('Fetching main page at "%s"', category_url)
            try:
                with rpc.to(fetcher_pb.Service, self._fetcher_host, self._fetcher_port) as fetcher_client:
                    category_page = fetcher_client.fetch_url(category_url)
                    if category_page.mime_type not in defines.WEBPAGE_MIMETYPES:
                        logging.warn('Main page is of wrong MIME type')
                        continue
            except (urllib2.URLError, ValueError) as e:
                logging.warn('Could not fetch - %s', str(e))
                continue

            logging.info('Parse structure')
            soup = bs.BeautifulSoup(category_page.content)

            if soup is None:
                raise analyzers.Error('Could not parse structure')

            site_table = soup.find(id='siteTable')
            if site_table is None:
                raise analyzers.Error('Could not find links information')

            possible_artifacts_things = site_table.findAll('div', 'thing')

            num_possible_artifact_urls = 0

            for possible_thing in possible_artifacts_things:
                rank = possible_thing.find('span', 'rank')
                if rank.string is None:
                    # This is a sticky / unranked element
                    continue
                link = possible_thing.find('a', 'title')
                possible_artifact_url = link.get('href')
                title = link.text
                if possible_artifact_url is None:
                    continue
                num_possible_artifact_urls += 1
                initial_artifact_links.append((title, possible_artifact_url))

            logging.info('Found %d possible artifact URLs', num_possible_artifact_urls)

        logging.info('Found %d possible artifact URLs', len(initial_artifact_links))

        artifact_descs = []

        for (title, possible_artifact_url) in initial_artifact_links:
            try:
                artifact_desc = self._analyze_artifact_link(title, possible_artifact_url)
            except analyzers.Error as e:
                logging.warn('Could not analyze "%s" - %s', possible_artifact_url, str(e))
                continue
            artifact_descs.append(artifact_desc)

        return artifact_descs

    def _analyze_artifact_link(self, title, artifact_page_url):
        logging.info('Analyzing "%s"', artifact_page_url)

        try_other_analyzer = False

        try:
            with rpc.to(fetcher_pb.Service, self._fetcher_host, self._fetcher_port) as fetcher_client:
                image = fetcher_client.fetch_url_mimetype(artifact_page_url)
                if image.mime_type not in defines.PHOTO_MIMETYPES:
                    # Try to parse things with the Imgur analyzer.
                    try_other_analyzer = True
                    logging.warn('Could not fetch as image, trying with another analyzer')
        except (urllib2.URLError, ValueError) as e:
            raise analyzers.Error('Could not fetch - %s' % str(e))

        if not try_other_analyzer:
            return {
                'page_uri': artifact_page_url,
                'title': title,
                'photo_description': [{
                    'subtitle': '',
                    'description': '',
                    'uri_path': artifact_page_url
                }]
            }
        else:
            return self._imgur_analyzer._analyze_artifact_link(artifact_page_url)
