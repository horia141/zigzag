"""Entry point for the task responsible for fetching web documents from the web."""

import fetcher.api as api


class Server(comlink.RpcServer):
    def __init__(self):
        super(self, Server).__init__()

    def fetch(request_serialized):
        request = api._parse_fetch(request_serialized)


def main():
    server = Server()
    server.start()


if __name__ == '__main__':
    main()
