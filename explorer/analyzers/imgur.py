"""The Imgur analyzer."""

import logging

import imgurpython as imgur
from imgurpython.helpers.error import ImgurClientError
from imgurpython.helpers.error import ImgurClientRateLimitError

import common.defines.constants as defines
import explorer.analyzers as analyzers


class Analyzer(analyzers.Analyzer):
    """Class for performing analysis of the Imgur artifact source."""

    def __init__(self, source, fetcher_host, fetcher_port):
        super(Analyzer, self).__init__(source, fetcher_host, fetcher_port)
        self._subdomains_pairs = [tuple(s.split(':')) for s in source.subdomains]

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
            if item.is_album:
                artifact_descs.append(self._analyze_album(client, item))
            else:
                artifact_descs.append(self._analyze_image(item))

        return artifact_descs

    def _analyze_album(self, client, album_shallow):
        logging.info('Found artifact of %d images' % album_shallow.images_count)

        try:
            album = client.get_album(album_shallow.id)
        except (ImgurClientRateLimitError, ImgurClientError) as e:
            raise analyzers.Error('Imgur error "%s"' % str(e))

        photo_descriptions = []

        for image in album.images:
            photo_description = {}
            if 'title' in image:
                photo_description['title'] = image['title']
            if 'description' in image:
                photo_description['description'] = image['description']
            photo_description['uri_path'] = image['link']
            photo_descriptions.append(photo_description)

        return {
            'page_uri': album.link,
            'title': album.title,
            'photo_description': photo_descriptions
        }

    def _analyze_image(self, image):
        return {
            'page_uri': image.link,
            'title': image.title,
            'photo_description': {
                'subtitle': '',
                'description': image.description if image.description else '',
                'uri_path': image.link
            }
        }
