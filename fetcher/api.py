"""API used by clients of the fetcher service to retrieve web documents."""

import json

import common.comlink as comlink


class Client(comlink.RpcClient):
    def __init__(self):
        super(self, Client).__init__()

    def fetch(page_url):
        request_serialized = self._serialize_fetch(page_url)
        response_serialized = self.remote_call('fetch', request_serialized)
        response = self._parse_fetch(response_serialized)
