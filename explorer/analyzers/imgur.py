"""The Imgur analyzer."""

import logging

import imgurpython as imgur
from imgurpython.helpers.error import ImgurClientError
from imgurpython.helpers.error import ImgurClientRateLimitError

import common.defines.constants as defines
import explorer.analyzers as analyzers


class Analyzer(analyzers.Analyzer):
    """Class for performing analysis of the Imgur artifact source."""

    PAGE_URI_FILTER_MAX_SIZE = 20000

    def __init__(self, source, fetcher_host, fetcher_port):
        super(Analyzer, self).__init__(source, fetcher_host, fetcher_port)
        self._subdomains_pairs = [tuple(s.split(':')) for s in source.subdomains]
        self._page_uri_filter = set()

    def analyze(self):
        logging.info('Analyzing Imgur')

        logging.info('Fetching main page')

        items = []

        try:
            client = imgur.ImgurClient(defines.IMGUR_CLIENT_ID, defines.IMGUR_CLIENT_SECRET)

            for (section, sort) in self._subdomains_pairs:
                items.extend(client.gallery(section=section, sort=sort))
        except (ImgurClientRateLimitError, ImgurClientError) as e:
            raise analyzers.Error('Imgur error "%s"' % str(e))

        logging.info('Found %d possible artifacts')

        artifact_descs = []

        for item in items:
            logging.info('Scanning artifact "%s"' % item.title)
            if item.link in self._page_uri_filter:
                logging.info('Already processed artifact in this run')
                continue

            try:
                if item.is_album:
                    artifact_descs.append(self._analyze_album(client, item))
                else:
                    artifact_descs.append(self._analyze_image(item))
                self._page_uri_filter.add(item.link)
            except analyzers.Error as e:
                logging.info('Could not process because "%s"' % str(e))

        # Reset the page_uri_filter.
        if len(self._page_uri_filter) > Analyzer.PAGE_URI_FILTER_MAX_SIZE:
            self._page_uri_filter = set(list(self._page_uri_filter)[0:Analyzer.PAGE_URI_FILTER_MAX_SIZE])

        return artifact_descs

    def _analyze_album(self, client, album_shallow):
        logging.info('Found artifact of %d images' % album_shallow.images_count)

        try:
            album = client.get_album(album_shallow.id)
        except (ImgurClientRateLimitError, ImgurClientError) as e:
            raise analyzers.Error('Imgur error "%s"' % str(e))

        if not hasattr(album, 'link') and not hasattr(album_shallow, 'link'):
            raise analyzers.Error('Cannot find artifact link')

        page_uri = self._get_str_attribute(album, album_shallow, 'link')
        title = self._get_str_attribute(album, album_shallow, 'title')

        photo_descriptions = []

        for image in album.images:
            photo_description = {}
            if 'title' in image and image['title'] is not None:
                photo_description['subtitle'] = image['title']
            else:
                photo_description['subtitle'] = ''
            if 'description' in image and image['description'] is not None:
                photo_description['description'] = image['description']
            else:
                photo_description['description'] = ''
            if 'link' not in image or image['link'] is None:
                raise analyzers.Error('Found image with no link')
            photo_description['uri_path'] = image['link']
            photo_descriptions.append(photo_description)

        return {
            'page_uri': page_uri,
            'title': title,
            'photo_description': photo_descriptions
        }

    def _analyze_image(self, image):
        page_uri = self._get_str_attribute(image, image, 'link')
        title = self._get_str_attribute(image, image, 'title')

        return {
            'page_uri': page_uri,
            'title': title,
            'photo_description': [{
                'subtitle': '',
                'description': image.description if image.description else '',
                'uri_path': page_uri
            }]
        }

    def _get_str_attribute(self, item, item_shallow, name):
        if not hasattr(item, name) and not hasattr(album_shallow, name):
            raise analyzers.Error('Cannot find field %s' % name)

        field = getattr(item, name, getattr(item_shallow, name, None))

        if field is None:
            raise analyzers.Error('Field %s is none' % name)

        if field.isspace():
            raise analyzers.Error('Field %s is empty' % name)

        return field.strip()
