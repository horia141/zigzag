"""Artifact sources and artifact analyzers."""

import urlparse

import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport

import fetcher


class Error(Exception):
    """Error raised by analyzers."""
    pass


class Analyzer(object):
    """Base class for artifact source and artifact analyzers."""

    def __init__(self, source, fetcher_port):
        """Construct an analyzer."""
        ser = serializer.Serializer()
        client = transport.Client(fetcher_port, ser)
        self._fetcher = fetcher.Service.client(client)
        self._source = source

    def analyze(self):
        raise Exception('Not implemented')

    @property
    def source(self):
        return self._source

    @staticmethod
    def _parse_incomplete_url(incomplete_url):
        return urlparse.urlparse(incomplete_url, scheme='http').geturl()