"""Artifact sources and artifact analyzers."""

import urlparse


class Error(Exception):
    """Error raised by analyzers."""
    pass


class Analyzer(object):
    """Base class for artifact source and artifact analyzers."""

    def __init__(self, source, fetcher_host, fetcher_port, counters):
        """Construct an analyzer."""
        self._source = source
        self._fetcher_host = fetcher_host
        self._fetcher_port = fetcher_port
        self._counters = counters

    def analyze(self):
        raise Exception('Not implemented')

    @property
    def source(self):
        return self._source

    @staticmethod
    def _parse_incomplete_url(incomplete_url):
        return urlparse.urlparse(incomplete_url, scheme='http').geturl()
