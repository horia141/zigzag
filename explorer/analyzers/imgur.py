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

    def __init__(self, source, fetcher_host, fetcher_port, counters):
        super(Analyzer, self).__init__(source, fetcher_host, fetcher_port, counters)
        self._subdomains_pairs = [tuple(s.split(':')) for s in source.subdomains]
        self._page_uri_filter = set()
        self._client = imgur.ImgurClient(defines.IMGUR_CLIENT_ID, defines.IMGUR_CLIENT_SECRET)

    def analyze(self):
        logging.info('Analyzing Imgur')

        logging.info('Fetching main page')

        items = []

        self._counters.inc('/imgur/analyzer/main-page')

        for (section, sort) in self._subdomains_pairs:
            self._counters.inc('/imgur/analyzer/subdomains/found')
            try:
                logging.info('Fetching "%s" and "%s"' % (section, sort))
                items.extend(self._client.gallery(section=section, sort=sort))
            except (ImgurClientRateLimitError, ImgurClientError) as e:
                self._counters.inc('/imgur/analyzer/subdomains/imgur-error')
                logging.error('Imgur error "%s"' % str(e))
                continue

        logging.info('Found %d possible artifacts' % len(items))

        artifact_descs = []

        for item in items:
            self._counters.inc('/imgur/analyzer/items/found')

            logging.info('Scanning artifact "%s"' % item.title)
            if item.link in self._page_uri_filter:
                self._counters.inc('/imgur/analyzer/items/already-exist-local-filter')
                logging.info('Already processed artifact in this run')
                continue

            try:
                if item.is_album:
                    artifact_descs.append(self._analyze_album(item))
                else:
                    artifact_descs.append(self._analyze_image(item))
                self._page_uri_filter.add(item.link)
            except analyzers.Error as e:
                self._counters.inc('/imgur/analyzer/items/analysis-error')
                logging.error('Could not process because "%s"' % str(e))
                continue

            self._counters.inc('/imgur/analyzer/items/added')

        # Reset the page_uri_filter.
        if len(self._page_uri_filter) > Analyzer.PAGE_URI_FILTER_MAX_SIZE:
            self._page_uri_filter = set(list(self._page_uri_filter)[0:Analyzer.PAGE_URI_FILTER_MAX_SIZE])

        return artifact_descs

    def analyze_by_id(self, item_id, title=None, counter_prefix='imgur'):
        logging.info('Analyzing artifact by id "%s" (%s)' % (item_id, title))

        self._counters.inc('/imgur/analyzer-by-id/items/found')

        try:
            logging.info('Trying to analyze artifact as image')
            image = self._client.get_image(item_id)
            self._counters.inc('/imgur/analyzer-by-id/items/added-as-image')
            return self._analyze_image(image, title)
        except (ImgurClientRateLimitError, ImgurClientError) as e:
            logging.info('Artifact was not an image')

        try:
            logging.info('Trying to analyze artifact as album')
            album = self._client.get_album(item_id)
            self._counters.inc('/imgur/analyzer-by-id/items/added-as-album')
            return self._analyze_album(album, title)
        except (ImgurClientRateLimitError, ImgurClientError) as e:
            logging.info('Artifact was not an album')

        self._counters.inc('/imgur/analyzer-by-id/items/failed-as-image-and-album')
        raise analyzers.Error('Artifact was neither image nor album -- wierd')

    def _analyze_image(self, image, title=None):
        logging.info('Analyzing artifact as image')

        self._counters.inc('/imgur/analyzer/items/image/found')
        page_uri = self._get_str_attribute(image, image, 'link')
        title = self._get_str_attribute(image, image, 'title', title)

        self._counters.inc('/imgur/analyzer/items/image/added')

        return {
            'page_uri': page_uri,
            'title': title,
            'photo_description': [{
                'subtitle': '',
                'description': image.description if image.description else '',
                'uri_path': page_uri
            }]
        }

    def _analyze_album(self, album_shallow, title=None):
        logging.info('Found artifact of %d images' % album_shallow.images_count)

        self._counters.inc('/imgur/analyzer/items/album/found')

        try:
            album = self._client.get_album(album_shallow.id)
        except (ImgurClientRateLimitError, ImgurClientError) as e:
            self._counters.inc('/imgur/analyzer/items/album/imgur-error')
            raise analyzers.Error('Imgur error "%s"' % str(e))

        if not hasattr(album, 'link') and not hasattr(album_shallow, 'link'):
            self._counters.inc('/imgur/analyzer/items/album/no-link-error')
            raise analyzers.Error('Cannot find artifact link')

        page_uri = self._get_str_attribute(album, album_shallow, 'link')
        title = self._get_str_attribute(album, album_shallow, 'title', title)

        self._counters.inc('/imgur/analyzer/items/album/added')

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

    def _get_str_attribute(self, item, item_shallow, name, default_value=None):
        if not hasattr(item, name) and not hasattr(album_shallow, name):
            raise analyzers.Error('Cannot find field %s' % name)

        field = getattr(item, name, getattr(item_shallow, name, None))

        if field is None:
            field = default_value

        if field is None:
            raise analyzers.Error('Field %s is none' % name)

        if field.isspace():
            raise analyzers.Error('Field %s is empty' % name)

        return field.strip()
