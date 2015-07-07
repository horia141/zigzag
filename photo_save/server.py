"""A service which processes the images found for an artifact."""

import argparse
import cStringIO as StringIO
import logging
import os.path
import uuid

from thrift.protocol import TBinaryProtocol
from thrift.server import TServer
from thrift.transport import TSocket
from thrift.transport import TTransport
from PIL import Image

import common.defines.constants as defines
import common.model.ttypes as model
import fetcher_pb.Service
import fetcher_pb.ttypes as fetcher_types
import photo_save_pb.Service
import photo_save_pb.ttypes as photo_save_types
import photo_save.decoders.gif
import photo_save.decoders.generic_image
import utils.pidfile as pidfile
import utils.rpc as rpc
import utils.photos as photos


class ServiceHandler(object):
    def __init__(self, fetcher_host, fetcher_port, photos_dir):
        self._fetcher_host = fetcher_host
        self._fetcher_port = fetcher_port
        self._photos_dir = photos_dir
        self._image_decoders = {
            'image/jpeg': photo_save.decoders.generic_image.Decoder(),
            'image/png': photo_save.decoders.generic_image.Decoder(),
            'image/gif': photo_save.decoders.generic_image.Decoder()
        }
        self._video_decoders = {
            'image/gif': photo_save.decoders.gif.Decoder()
        }

    def process_one_photo(self, source_uri):
        logging.info('Processing "%s"' % source_uri)

        logging.info('Fetching from remote source')

        try:
            with rpc.to(fetcher_pb.Service, self._fetcher_host, self._fetcher_port) as fetcher_client:
                photo_raw_data = fetcher_client.fetch_photo(source_uri)
        except fetcher_types.PhotoTooBigError as e:
            logging.error(e.message)
            raise photo_save_types.Error(message=e.message)
    
        if photo_raw_data.mime_type not in defines.PHOTO_MIMETYPES:
            logging.error('Unrecognized photo type - "%s"' % photo_raw_data.mime_type)
            raise photo_save_types.Error(message='Unrecognized photo type - "%s"' % photo_raw_data.mime_type)

        logging.info('Decoding image')
        try:
            photo = Image.open(StringIO.StringIO(photo_raw_data.content))
        except Exception as e:
            logging.error('Could not parse image "%s"' % str(e))
            raise photo_save_types.Error(message='Could not parse image "%s"' % str(e))

        if photos.is_video(photo):
            logging.info('Detected animation')
            screen_config = defines.VIDEO_SCREEN_CONFIG
            photo_data = self._video_decoders[photo_raw_data.mime_type].decode(
                defines.VIDEO_SCREEN_CONFIG.name, defines.VIDEO_SCREEN_CONFIG, photo, photo_raw_data.content,
                lambda mime_type: self._unique_photo_path(self._photos_dir, mime_type))
        else:
            logging.info('Detected regular')
            photo_data = self._image_decoders[photo_raw_data.mime_type].decode(
                defines.IMAGE_SCREEN_CONFIG.name, defines.IMAGE_SCREEN_CONFIG, photo,
                lambda mime_type: self._unique_photo_path(self._photos_dir, mime_type))

        logging.info('Done')
        return photo_data

    def _unique_photo_path(self, photos_dir, mime_type):
        extension = defines.PHOTO_MIMETYPES_TO_EXTENSION[mime_type]
        unique_file_name = '%s.%s' % (str(uuid.uuid4()), extension)
        return (os.path.join(photos_dir, unique_file_name), unique_file_name)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--host', type=str, required=True,
        help='Host on which the server is listening')
    parser.add_argument('--port', type=int, required=True,
        help='Port on which the server is listening')
    parser.add_argument('--fetcher_host', type=str, required=True,
        help='Host on which the fetcher server is listening')
    parser.add_argument('--fetcher_port', type=int, required=True,
        help='Port on which the fetcher server is listening')
    parser.add_argument('--photos_dir', type=str, required=True,
        help='Directory to store the processed/derived photos')
    parser.add_argument('--log_path', type=str, required=True,
        help='Path to the log file')
    parser.add_argument('--pidfile_path', type=str, required=True,
        help='Path for the pidfile')
    args = parser.parse_args()

    pidfile.write_pidfile(args.pidfile_path)

    logging.basicConfig(level=logging.INFO, filename=args.log_path)

    service_handler = ServiceHandler(args.fetcher_host, args.fetcher_port, args.photos_dir)
    processor = photo_save_pb.Service.Processor(service_handler)
    transport = TSocket.TServerSocket(host=args.host, port=args.port)
    transport_factory = TTransport.TBufferedTransportFactory()
    protocol_factory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, transport_factory, protocol_factory)
    server.serve()


if __name__ == '__main__':
    main()
