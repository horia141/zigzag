"""The Imgur analyzer."""

import logging
import urllib2
import urlparse

import bs4 as bs

import common.defines.constants as defines
import explorer.analyzers as analyzers


class Analyzer(analyzers.Analyzer):
    """Class for performing analysis of the Imgur artifact source."""

    def __init__(self, source, fetcher):
        super(Analyzer, self).__init__(source, fetcher)

    def analyze(self):
        logging.info('Analyzing Imgur')

        initial_artifact_links = []

        logging.info('Fetching main page')

        try:
            main_page = self._fetcher.fetch_url(self.source.start_page_uri)
            if main_page.mime_type not in defines.WEBPAGE_MIMETYPES:
                logging.warn('Main page is of wrong MIME type')
                return []
        except (urllib2.URLError, ValueError) as e:
            logging.warn('Could not fetch - %s', str(e))
            return []

        logging.info('Parse structure')
        soup = bs.BeautifulSoup(main_page.content)

        if soup is None:
            raise analyzers.Error('Could not parse structure')

        posts = soup.findAll('div', 'post')

        for post in posts:
            link = post.find('a', 'image-list-link')
            if link is None:
                continue
            possible_artifact_url_path = link.get('href')
            if possible_artifact_url_path is None:
                continue
            possible_artifact_url = urlparse.urljoin(
                self.source.start_page_uri, possible_artifact_url_path)
            initial_artifact_links.append(possible_artifact_url)

        logging.info('Found %d possible artifact URLs', len(initial_artifact_links))

        artifact_descs = []

        for initial_artifact_link in initial_artifact_links:
            try:
                artifact_desc = self._analyze_artifact_link(initial_artifact_link)
            except analyzers.Error as e:
                logging.warn('Could not analyze "%s" - %s', initial_artifact_link, str(e))
                continue
            artifact_descs.append(artifact_desc)

        return artifact_descs

    def _analyze_artifact_link(self, artifact_page_url):
        logging.info('Analyzing "%s"', artifact_page_url)

        try:
            page = self._fetcher.fetch_url(artifact_page_url)
            if page.mime_type not in defines.WEBPAGE_MIMETYPES:
                raise analyzers.Error('Page is of wrong MIME type')
        except (urllib2.URLError, ValueError) as e:
            raise analyzers.Error('Could not fetch - %s' % str(e))

        logging.info('Parse structure')
        try:
            soup = bs.BeautifulSoup(page.content)
        except TypeError as e:
            # TODO(horia141): I think there's a bug in BeautifulSoup.
            raise analyzers.Error('Could not process - %s' % str(e))

        if soup is None:
            raise analyzers.Error('Could not parse structure')

        album_truncated = soup.find('div', id='album-truncated')

        if album_truncated is not None:
            # The album was truncated. Need to get to the real deal.
            new_artifact_page_url_elem = album_truncated.find('a')
            if new_artifact_page_url_elem is not None:
                logging.info('A very big album. Rescanning album')
                new_artifact_page_url = self._parse_incomplete_url(
                    new_artifact_page_url_elem.get('href'))
                return self._analyze_artifact_link_as_album(new_artifact_page_url)
            else:
                # Still continue with the regular analysis if we couldn't find the
                # actual link.
                pass

        title_elem = soup.find('h1', id='image-title')

        if title_elem is None:
            raise analyzers.Error('Could not find title')

        title = title_elem.text

        main_image = soup.find('div', 'main-image')

        if main_image is None:
            raise analyzers.Error('Could not find image')

        image = main_image.find(id='image')

        if image is None:
            raise analyzers.Error('Could not find image')

        actual_images = image.findAll('div', {'class': 'album-image'})

        if len(actual_images) == 0:
            # Sometimes, a single image is present, and that is "image".
            actual_images = [image]

        photo_description = []

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
            url_path = self._parse_incomplete_url(url_path_raw.get('src'))

            photo_description.append({
                'subtitle': subtitle,
                'description': description,
                'uri_path': url_path
            })

        if len(photo_description) == 0:
            raise analyzers.Error('Could not find images')

        logging.info('Found title and %d images', len(photo_description))

        return {
            'page_uri': artifact_page_url,
            'title': title,
            'photo_description': photo_description
        }

    def _analyze_artifact_link_as_album(self, artifact_page_url):
        logging.info('Analyzing "%s"', artifact_page_url)

        try:
            page = self._fetcher.fetch_url(artifact_page_url)
            if page.mime_type not in defines.WEBPAGE_MIMETYPES:
                raise analyzers.Error('Page is of wrong MIME type')
        except (urllib2.URLError, ValueError) as e:
            raise analyzers.Error('Could not fetch - %s' % str(e))

        logging.info('Parse structure')
        soup = bs.BeautifulSoup(page.content)

        if soup is None:
            raise analyzers.Error('Could not parse structure')

        title_elem_container = soup.find('div', {'class': 'album-description'})

        if title_elem_container is None:
            raise analyzers.Error('Could not find title container')

        title_elem = title_elem_container.find('h1')

        if title_elem is None:
            raise analyzers.Error('Could not find title')

        title = title_elem.text

        main_image = soup.find('div', id='image-container')

        if main_image is None:
            raise analyzers.Error('Could not find set of images')

        actual_images = main_image.findAll('div', {'class': 'image'})
        photo_description = []

        for actual_image in actual_images:
            subtitle_raw = actual_image.find('h2', {'class': 'first'})

            if subtitle_raw is None:
                subtitle = ''
            else:
                subtitle = subtitle_raw.text
            description_raw = actual_image.find('p', {'class': 'description textbox'})
            if description_raw is None:
                description = ''
            else:
                description = description_raw.text
            url_path_container = actual_image.find('div', {'class': 'options'})
            if url_path_container is None:
                continue
            url_path_raw = url_path_container.find('a')
            if url_path_raw is None:
                continue
            url_path = self._parse_incomplete_url(url_path_raw.get('href'))

            photo_description.append({
                'subtitle': subtitle,
                'description': description,
                'uri_path': url_path
            })

        if len(photo_description) == 0:
            raise analyzers.Error('Could not find images')

        logging.info('Found title and %d images', len(photo_description))

        return {
            'page_uri': artifact_page_url,
            'title': title,
            'photo_description': photo_description
        }
