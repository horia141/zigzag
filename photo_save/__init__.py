"""A service which processes the images found for an artifact."""

import argparse
import cStringIO as StringIO
import logging
import os.path
import uuid

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport
from PIL import Image

import common.defines.constants as defines
import common.model.ttypes as model
import fetcher
import photo_save.decoders.gif
import photo_save.decoders.generic_image
import utils.pidfile as pidfile


class Error(Exception):
    pass


class GifTooLargeError(Error):
    pass


class Service(comlink.Service):
    def __init__(self, ser, fetcher_port, photos_dir):
        super(Service, self).__init__()
        client = transport.Client(fetcher_port, ser)
        self._photos_dir = photos_dir
        self._fetcher = fetcher.Service.client(client)
        self._image_decoders = {
            'image/jpeg': photo_save.decoders.generic_image.Decoder(),
            'image/png': photo_save.decoders.generic_image.Decoder(),
            'image/gif': photo_save.decoders.generic_image.Decoder()
        }
        self._video_decoders = {
            'image/gif': photo_save.decoders.gif.Decoder()
        }

    @comlink.call
    def process_one_photo(self, subtitle, description, source_uri):
        logging.info('Processing "%s"' % source_uri)

        logging.info('Fetching from remote source')

        (photo_raw_data, photo_raw_mime_type) = self._fetcher.fetch_url(source_uri)
        if photo_raw_mime_type not in defines.PHOTO_MIMETYPES:
            raise Error('Unrecognized photo type - "%s"' % photo_raw_mime_type)

        logging.info('Saving the original locally')

        (storage_path, original_photo_uri_path) = self._unique_photo_path(photo_raw_mime_type)

        photo_raw_file = open(storage_path, 'w')
        photo_raw_file.write(photo_raw_data)
        photo_raw_file.close()

        logging.info('Decoding image')
        photo = Image.open(StringIO.StringIO(photo_raw_data))

        if self._is_video(photo):
            logging.info('Detected animation')
            screen_config = defines.VIDEO_SCREEN_CONFIG
            photo_data = self._video_decoders[photo_raw_mime_type].decode(
                defines.VIDEO_SCREEN_CONFIG.name, defines.VIDEO_SCREEN_CONFIG, photo,
                storage_path, lambda mime_type: self._unique_photo_path(mime_type))
        else:
            logging.info('Detected regular')
            photo_data = self._image_decoders[photo_raw_mime_type].decode(
                defines.IMAGE_SCREEN_CONFIG.name, defines.IMAGE_SCREEN_CONFIG, photo,
                storage_path, lambda mime_type: self._unique_photo_path(mime_type))

        logging.info('Done')
        return model.PhotoDescription(subtitle.encode('utf-8'), description.encode('utf-8'),
            source_uri, original_photo_uri_path, photo_data)

    def _unique_photo_path(self, mime_type):
        extension = defines.PHOTO_MIMETYPES_TO_EXTENSION[mime_type]
        unique_file_name = '%s.%s' % (str(uuid.uuid4()), extension)
        return (os.path.join(self._photos_dir, unique_file_name), unique_file_name)

    def _is_video(self, photo):
        try:
            photo.seek(1)
        except EOFError:
            return False
        photo.seek(0)
        return True


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--port', type=int, required=True,
        help='Port on which the Comlink server is listening')
    parser.add_argument('--fetcher_port', type=int, required=True,
        help='Port on which the fetcher Comlink server is listening')
    parser.add_argument('--photos_dir', type=str, required=True,
        help='Directory to store the processed photos')
    parser.add_argument('--log_path', type=str, required=True,
        help='Path to the log file')
    parser.add_argument('--pidfile_path', type=str, required=True,
        help='Path for the pidfile')
    args = parser.parse_args()

    pidfile.write_pidfile(args.pidfile_path)

    logging.basicConfig(level=logging.INFO, filename=args.log_path)

    ser = serializer.Serializer()
    photo_save_service = Service(ser, args.fetcher_port, args.photos_dir)

    server = transport.Server(args.port, ser)
    server.add_service(photo_save_service)
    server.start()


if __name__ == '__main__':
    main()
