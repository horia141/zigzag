"""The 9gag analyzer."""

import logging
import urllib2

import BeautifulSoup as bs

import common.defines.constants as defines
import explorer.analyzers as analyzers


class Analyzer(analyzers.Analyzer):
    """Class for performing analysis of the 9gag artifact source."""

    def __init__(self, source):
        super(Analyzer, self).__init__(source)

    def analyze(self):
        logging.info('Analyzing 9gag')

        initial_artifact_links = []

        for category in self.source.subdomains:
            logging.info('Analyzing category "%s"', category)

            category_url = self.source.start_page_uri % category
            logging.info('Fetching main page at "%s"', category_url)
            try:
                (category_page_raw_content, category_page_type) = self._fetcher.fetch_url(category_url)
                if category_page_type not in defines.WEBPAGE_MIMETYPES:
                    logging.warn('Main page is of wrong MIME type')
                    continue
            except (urllib2.URLError, ValueError) as e:
                logging.warn('Could not fetch - %s', str(e))
                continue

            logging.info('Parse structure')
            soup = bs.BeautifulSoup(category_page_raw_content, convertEntities=bs.BeautifulSoup.HTML_ENTITIES)

            if soup is None:
                raise analyzers.Error('Could not parse structure')

            list_view_2 = soup.find('section', id='list-view-2')
            if list_view_2 is None:
                raise analyzers.Error('Could not find links information')

            possible_artifacts_things = list_view_2.findAll('article')
            num_possible_artifact_urls = 0

            for possible_thing in possible_artifacts_things:
                possible_artifact_url = possible_thing.get('data-entry-url')
                if possible_artifact_url is None:
                    continue
                title_link = possible_thing.find('a')
                title = title_link.text.strip()
                num_possible_artifact_urls += 1
                initial_artifact_links.append((title, possible_artifact_url))

            logging.info('Found %d possible artifact URLs', num_possible_artifact_urls)

        logging.info('Found %d possible artifact URLs', len(initial_artifact_links))

        artifact_descs = []

        for (title, possible_artifact_url) in initial_artifact_links:
            try:
                artifact_desc = self._analyze_artifact_link(title, possible_artifact_url)
            except analyzers.Error as e:
                logging.warn('Could not analyze "%s" - "%s', possible_artifact_url, str(e))
                continue
            artifact_descs.append(artifact_desc)

        return artifact_descs

    def _analyze_artifact_link(self, title, artifact_page_url):
        logging.info('Analyzing "%s"', artifact_page_url)

        try:
            (page_raw_content, page_mime_type) = self._fetcher.fetch_url(artifact_page_url)
            if page_mime_type not in defines.WEBPAGE_MIMETYPES:
                raise analyzers.Error('Page is of wrong MIME type')
        except (urllib2.URLError, ValueError) as e:
            raise analyzers.Error('Could not fetch - %s' % str(e))

        logging.info('Parse structure')
        soup = bs.BeautifulSoup(page_raw_content, convertEntities=bs.BeautifulSoup.HTML_ENTITIES)

        if soup is None:
            raise analyzers.Error('Could not parse structure')

        animated_div = soup.find('div', {'class': 'badge-animated-container-animated post-view'})

        if animated_div is not None:
            logging.info('Found an animation')
            url_path_raw = animated_div.get('data-image')
        else:
            logging.info('Found an regular image')
            img = soup.find('img', {'class': 'badge-item-img'})
            url_path_raw = img.get('src')

        if url_path_raw is None:
            raise analyzers.Error('Could not find URL to resource')

        url_path = self._parse_incomplete_url(url_path_raw)

        return {
            'page_uri': artifact_page_url,
            'title': title,
            'photo_description': [{
                'subtitle': '',
                'description': '',
                'uri_path': url_path
            }]
        }
