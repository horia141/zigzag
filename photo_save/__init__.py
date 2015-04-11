"""A service which processes the images found for an artifact."""

import cStringIO as StringIO
import logging
import uuid

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport
from PIL import Image

import common.defines.constants as defines
import fetcher
import photo_save.decoders.gif
import photo_save.decoders.generic_image


class Error(Exception):
    pass


class GifTooLargeError(Error):
    pass


class Service(comlink.Service):
    def __init__(self, ser):
        super(Service, self).__init__()
        client = transport.Client(defines.FETCHER_PORT, ser)
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
        photo_data = {}

        if self._is_video(photo):
            logging.info('Detected animation')
            for (screen_config_key, screen_config) in defines.VIDEO_SCREEN_CONFIG.iteritems():
                photo_data[screen_config_key] = self._video_decoders[photo_raw_mime_type].decode(
                    screen_config_key, screen_config, photo, storage_path,
                    lambda mime_type: self._unique_photo_path(mime_type))
        else:
            logging.info('Detected regular')
            for (screen_config_key, screen_config) in defines.IMAGE_SCREEN_CONFIG.iteritems():
                photo_data[screen_config_key] = self._image_decoders[photo_raw_mime_type].decode(
                    screen_config_key, screen_config, photo, storage_path,
                    lambda mime_type: self._unique_photo_path(mime_type))

        logging.info('Done')

        return {
            'subtitle': subtitle,
            'description': description,
            'source_uri': source_uri,
            'original_photo_uri_path': original_photo_uri_path,
            'photo_data': photo_data
        }

    def _unique_photo_path(self, mime_type):
        extension = defines.PHOTO_MIMETYPES_TO_EXTENSION[mime_type]
        unique_file_name = '%s.%s' % (str(uuid.uuid4()), extension)
        return (defines.PHOTO_SAVE_STORAGE_PATH % unique_file_name, unique_file_name)

    def _is_video(self, photo):
        try:
            photo.seek(1)
        except EOFError:
            return False
        photo.seek(0)
        return True


def main():
    logging.basicConfig(level=logging.INFO, filename=defines.PHOTO_SAVE_LOG_PATH)

    ser = serializer.Serializer()
    photo_save_service = Service(ser)

    server = transport.Server(defines.PHOTO_SAVE_PORT, ser)
    server.add_service(photo_save_service)
    server.start()


if __name__ == '__main__':
    main()
