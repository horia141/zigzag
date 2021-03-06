"""The 9gag analyzer."""

import logging
import urllib2

import bs4 as bs

import common.defines.constants as defines
import explorer.analyzers as analyzers
import fetcher_pb.Service
import fetcher_pb.ttypes as fetcher_types
import utils.rpc as rpc


class Analyzer(analyzers.Analyzer):
    """Class for performing analysis of the 9gag artifact source."""

    def __init__(self, source, fetcher_host, fetcher_port, counters):
        super(Analyzer, self).__init__(source, fetcher_host, fetcher_port, counters)

    def analyze(self):
        logging.info('Analyzing 9gag')

        initial_artifact_links = []

        self._counters.inc('/9gag/analyzer/main-page')

        for category in self.source.subdomains:
            self._counters.inc('/9gag/analyzer/subdomains/found')
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
                self._counters.inc('/9gag/analyzer/subdomains/9gag-error')
                logging.warn('Could not fetch - %s', str(e))
                continue

            logging.info('Parse structure')
            soup = bs.BeautifulSoup(category_page.content)

            if soup is None:
                self._counters.inc('/9gag/analyzer/subdomains/unparsable-structure')
                raise analyzers.Error('Could not parse structure')

            list_view_2 = soup.find('section', id='list-view-2')
            if list_view_2 is None:
                self._counters.inc('/9gag/analyzer/subdomains/no-links')
                raise analyzers.Error('Could not find links information')

            possible_artifacts_things = list_view_2.findAll('article')
            num_possible_artifact_urls = 0

            for possible_thing in possible_artifacts_things:
                self._counters.inc('/9gag/analyzer/items/possible-found')
                possible_artifact_url = possible_thing.get('data-entry-url')
                if possible_artifact_url is None:
                    self._counters.inc('/9gag/analyzer/items/no-data-entry-url')
                    continue
                title_link = possible_thing.find('a')
                title = title_link.text.strip()
                num_possible_artifact_urls += 1
                initial_artifact_links.append((title, possible_artifact_url))

            logging.info('Found %d possible artifact URLs', num_possible_artifact_urls)

        logging.info('Found %d possible artifact URLs', len(initial_artifact_links))

        artifact_descs = []

        for (title, possible_artifact_url) in initial_artifact_links:
            self._counters.inc('/9gag/analyzer/items/found')
            try:
                artifact_desc = self._analyze_artifact_link(title, possible_artifact_url)
            except analyzers.Error as e:
                self._counters.inc('/9gag/analyzer/items/analysis-error')
                logging.warn('Could not analyze "%s" - "%s', possible_artifact_url, str(e))
                continue

            self._counters.inc('/9gag/analyzer/items/added')
            artifact_descs.append(artifact_desc)

        return artifact_descs

    def _analyze_artifact_link(self, title, artifact_page_url):
        logging.info('Analyzing "%s"', artifact_page_url)

        self._counters.inc('/9gag/analyzer/items/link/found')

        try:
            with rpc.to(fetcher_pb.Service, self._fetcher_host, self._fetcher_port) as fetcher_client:
                page = fetcher_client.fetch_url(artifact_page_url)
                if page.mime_type not in defines.WEBPAGE_MIMETYPES:
                    raise analyzers.Error('Page is of wrong MIME type')
        except (urllib2.URLError, ValueError) as e:
            self._counters.inc('/9gag/analyzer/items/link/fetch-error')
            raise analyzers.Error('Could not fetch - %s' % str(e))

        logging.info('Parse structure')
        soup = bs.BeautifulSoup(page.content)

        if soup is None:
            self._counters.inc('/9gag/analyzer/items/link/unparsable-structure')
            raise analyzers.Error('Could not parse structure')

        animated_div = soup.find('div', {'class': 'badge-animated-container-animated post-view'})

        if animated_div is not None:
            self._counters.inc('/9gag/analyzer/items/link/animation')
            logging.info('Found an animation')
            url_path_raw = animated_div.get('data-image')
        else:
            self._counters.inc('/9gag/analyzer/items/link/image')
            logging.info('Found an regular image')
            img = soup.find('img', {'class': 'badge-item-img'})
            if img is None:
                self._counters.inc('/9gag/analyzer/items/link/image-no-div')
                raise analyzers.Error('Could not find img div')
            url_path_raw = img.get('src')

        if url_path_raw is None:
            self._counters.inc('/9gag/analyzer/items/link/no-url-to-resource')
            raise analyzers.Error('Could not find URL to resource')

        url_path = self._parse_incomplete_url(url_path_raw)

        self._counters.inc('/9gag/analyzer/items/link/added')

        return {
            'page_uri': artifact_page_url,
            'title': title,
            'photo_description': [{
                'subtitle': '',
                'description': '',
                'uri_path': url_path
            }]
        }
