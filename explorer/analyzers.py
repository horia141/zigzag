"""Artifact sources and artifact analyzers."""

import BeautifulSoup as bs
import logging
import urllib2
import urlparse

import common.defines as defines


class Error(Exception):
    """Error raised by analyzers."""
    pass


class Analyzer(object):
    """Base class for artifact source and artifact analyzers."""

    def __init__(self):
        """Construct an analyzer."""
        pass

    def analyze(self):
        raise Exception('Not implemented')

    def _fetch_as_bot(self, page_url):
        request = urllib2.Request(page_url, headers=defines.EXPLORER_BOT_HEADERS)
        file_obj = urllib2.urlopen(request)
        raw_content = file_obj.read()
        file_obj.close()

        return (raw_content, file_obj.info().gettype())

class Reddit(Analyzer):
    """Class for performing analysis of the Reddit artifact source."""

    def __init__(self):
        super(Reddit, self).__init__()

        self._category_start_page_url_pattern = 'http://reddit.com/r/%s'
        self._categories = ('pics',)
        self._imgur_analyzer = Imgur()

    def analyze(self):
        logging.info('Analyzing Reddit')

        initial_artifact_links = []

        for category in self._categories:
            logging.info('Analyzing category "%s"', category)

            category_url = self._category_start_page_url_pattern % category
            logging.info('Fetching main page at "%s"', category_url)
            try:
                (category_page_raw_content, category_page_type) = self._fetch_as_bot(category_url)
                if category_page_type not in defines.WEBPAGE_MIMETYPES:
                    logging.warn('Main page is of wrong MIME type')
                    continue
            except urllib2.URLError as e:
                logging.warn('Could not fetch - %s', str(e))
                continue

            logging.info('Parse structure')
            soup = bs.BeautifulSoup(category_page_raw_content, convertEntities=bs.BeautifulSoup.HTML_ENTITIES)

            if soup is None:
                raise Error('Could not parse structure')

            site_table = soup.find(id='siteTable')
            if site_table is None:
                raise Error('Could not find links information')

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
            except Error as e:
                logging.warn('Could not analyze "%s" - %s', possible_artifact_url, str(e))
                continue
            artifact_descs.append(artifact_desc)

        return artifact_descs

    def _analyze_artifact_link(self, title, artifact_page_url):
        logging.info('Analyzing "%s"', artifact_page_url)

        try_other_analyzer = False

        try:
            (image_raw_content, image_mime_type) = self._fetch_as_bot(artifact_page_url)
            if image_mime_type not in defines.IMAGE_MIMETYPES:
                # Try to parse things with the Imgur analyzer.
                try_other_analyzer = True
                logging.warn('Could not fetch as image, trying with another analyzer')
        except urllib2.URLError as e:
            raise Error('Could not fetch - %s' % str(e))

        if not try_other_analyzer:
            return {
                'page_url': artifact_page_url,
                'title': title,
                'images_description': [{
                    'subtitle': '',
                    'description': '',
                    'url_path': artifact_page_url
                }]
            }
        else:
            return self._imgur_analyzer._analyze_artifact_link(artifact_page_url)


class Imgur(Analyzer):
    """Class for performing analysis of the Imgur artifact source."""

    def __init__(self):
        super(Imgur, self).__init__()

        self._main_page_url = 'http://imgur.com'

    def analyze(self):
        logging.info('Analyzing Imgur')

        initial_artifact_links = []

        logging.info('Fetching main page')

        try:
            (main_page_raw_content, main_page_mime_type) = self._fetch_as_bot(self._main_page_url)
            if main_page_mime_type not in defines.WEBPAGE_MIMETYPES:
                logging.warn('Main page is of wrong MIME type')
                return []
        except urllib2.URLError as e:
            logging.warn('Could not fetch - %s', str(e))
            return []

        logging.info('Parse structure')
        soup = bs.BeautifulSoup(main_page_raw_content, convertEntities=bs.BeautifulSoup.HTML_ENTITIES)

        if soup is None:
            raise Error('Could not parse structure')

        posts = soup.findAll('div', 'post')

        for post in posts:
            link = post.find('a', 'image-list-link')
            if link is None:
                continue
            possible_artifact_url_path = link.get('href')
            if possible_artifact_url_path is None:
                continue
            possible_artifact_url = urlparse.urljoin(
                self._main_page_url, possible_artifact_url_path)
            initial_artifact_links.append(possible_artifact_url)

        logging.info('Found %d possible artifact URLs', len(initial_artifact_links))

        artifact_descs = []

        for initial_artifact_link in initial_artifact_links:
            try:
                artifact_desc = self._analyze_artifact_link(initial_artifact_link)
            except Error as e:
                logging.warn('Could not analyze "%s" - %s', initial_artifact_link, str(e))
                continue
            artifact_descs.append(artifact_desc)

        return artifact_descs

    def _analyze_artifact_link(self, artifact_page_url):
        logging.info('Analyzing "%s"', artifact_page_url)

        try:
            (page_raw_content, page_mime_type) = self._fetch_as_bot(artifact_page_url)
            if page_mime_type not in defines.WEBPAGE_MIMETYPES:
                raise Error('Page is of wrong MIME type')
        except urllib2.URLError as e:
            raise Error('Could not fetch - %s' % str(e))

        logging.info('Parse structure')
        soup = bs.BeautifulSoup(page_raw_content, convertEntities=bs.BeautifulSoup.HTML_ENTITIES)

        if soup is None:
            raise Error('Could not parse structure')

        # panel = soup.find('div', {'class': 'panel'})

        # if panel is not None:
        #     # Might be one of them panels.
        #     title_elem = soup.find('h1', {'class': 'image-title'})

        #     if title_elem is None:
        #         raise Error('Could not find title')

        #     title = title_elem.text

        #     url_path_raw = panel.find('img')
        #     if url_path_raw is None:
        #         raise Error('Could not find url')
        #     url_path = urlparse.urlparse(url_path_raw.get('src'), scheme='http').geturl()

        #     return {
        #         'page_url': artifact_page_url,
        #         'title': title,
        #         'images_description': [{
        #             'subtitle': '',
        #             'description': '',
        #             'url_path': url_path
        #         }]
        #     }

        title_elem = soup.find('h1', id='image-title')

        if title_elem is None:
            raise Error('Could not find title')

        title = title_elem.text

        main_image = soup.find('div', 'main-image')

        if main_image is None:
            raise Error('Could not find image')

        image = main_image.find(id='image')

        if image is None:
            raise Error('Could not find image')

        actual_images = image.findAll('div', {'class': 'album-image'})

        if len(actual_images) == 0:
            # Sometimes, a single image is present, and that is "image".
            actual_images = [image]

        images_description = []

        for actual_image in actual_images:
            subtitle_raw = actual_image.find('h2', {'class': 'image-title small-margin-bottom'})
            if subtitle_raw is None:
                subtitle = ''
            else:
                subtitle = subtitle_raw.text
            description_raw = actual_image.find('p', {'class': 'description textbox'})
            if description_raw is None:
                description = ''
            else:
                description = description_raw.text
            url_path_raw = actual_image.find('img')
            if url_path_raw is None:
                continue
            url_path = urlparse.urlparse(url_path_raw.get('src'), scheme='http').geturl()

            images_description.append({
                'subtitle': subtitle,
                'description': description,
                'url_path': url_path
            })

        if len(images_description) == 0:
            raise Error('Could not find images')

        logging.info('Found title and %d images', len(images_description))

        return {
            'page_url': artifact_page_url,
            'title': title,
            'images_description': images_description
        }
