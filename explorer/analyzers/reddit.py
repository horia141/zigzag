"""The Reddit analyzer."""

import logging
import os.path
import urllib2
import urlparse

import praw
import praw.errors

import common.defines.constants as defines
import explorer.analyzers as analyzers
import explorer.analyzers.imgur as imgur
import fetcher_pb.Service
import fetcher_pb.ttypes as fetcher_types
import utils.rpc as rpc

class Analyzer(analyzers.Analyzer):
    """Class for performing analysis of the Reddit artifact source."""

    SUBREDDIT_FETCH_SIZE = 25
    PAGE_URI_FILTER_MAX_SIZE = 20000

    def __init__(self, source, fetcher_host, fetcher_port):
        super(Analyzer, self).__init__(source, fetcher_host, fetcher_port)
        self._page_uri_filter = set()
        self._imgur = imgur.Analyzer(defines.ARTIFACT_SOURCES[2], fetcher_host, fetcher_port)

    def analyze(self):
        logging.info('Analyzing Reddit')

        logging.info('Fetching main page')

        items = []

        client = praw.Reddit(user_agent=defines.EXPLORER_USER_AGENT)

        for subreddit in self._source.subdomains:
            try:
                logging.info('Fetching "%s"' % subreddit)
                items.extend(client.get_subreddit(subreddit).get_hot(limit=Analyzer.SUBREDDIT_FETCH_SIZE))
            except praw.errors.PRAWException as e:
                logging.error('Reddit error "%s"' % str(e))
                continue

        logging.info('Found %d possible artifacts' % len(items))

        artifact_descs = []

        for item in items:
            logging.info('Scanning artifact "%s"' % item.title)
            if item.permalink in self._page_uri_filter:
                logging.info('Already processed artifact in this run')
                continue

            try:
                submission_type = None
                if item.domain == 'imgur.com':
                    submission_type = 'imgur'
                else:
                    with rpc.to(fetcher_pb.Service, self._fetcher_host, self._fetcher_port) as fetcher_client:
                        logging.info('Trying to determine MIME type')
                        image = fetcher_client.fetch_url_mimetype(item.url)
                        if image.mime_type in defines.PHOTO_MIMETYPES:
                            submission_type = 'image'

                if submission_type == 'image':
                    artifact_descs.append(self._analyze_image(item))
                elif submission_type == 'imgur':
                    artifact_descs.append(self._analyze_imgur(item))
                else:
                    raise analyzers.Error('Unknown submission type')
                self._page_uri_filter.add(item.permalink)
            except (urllib2.URLError, ValueError, analyzers.Error) as e:
                logging.error('Could not process because "%s"' % str(e))

        # Reset the page_uri_filter.
        if len(self._page_uri_filter) > Analyzer.PAGE_URI_FILTER_MAX_SIZE:
            self._page_uri_filter = set(list(self._page_uri_filter)[0:Analyzer.PAGE_URI_FILTER_MAX_SIZE])

        return artifact_descs

    def _analyze_image(self, image):
        logging.info('Trying to analyze an raw image submission')
        return {
            'page_uri': image.permalink,
            'title': image.title,
            'photo_description': [{
                'subtitle': '',
                'description': image.selftext,
                'uri_path': image.url
            }]
        }

    def _analyze_imgur(self, imgur):
        logging.info('Trying to analyze an imgur submission')
        # Try to retrieve imgur id.
        try:
            path = urlparse.urlparse(imgur.url).path
            imgur_id = os.path.basename(path)
        except Exception as e:
            raise analyzers.Error('Could not extrac id from "%s" because "%s"' % (imgur.url, str(e)))
        return self._imgur.analyze_by_id(imgur_id, imgur.title)
