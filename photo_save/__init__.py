"""A service which processes the images found for an artifact."""

import logging
import uuid

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport
import photo_save.decoders.gif
import photo_save.decoders.generic_image

import common.defines as defines
import fetcher


class Error(Exception):
    pass


class GifTooLargeError(Error):
    pass


class Service(comlink.Service):
    def __init__(self, ser):
        super(Service, self).__init__()
        client = transport.Client(defines.FETCHER_PORT, ser)
        self._fetcher = fetcher.Service.client(client)
        self._decoders = {
            'image/jpeg': photo_save.decoders.generic_image.Decoder(),
            'image/png': photo_save.decoders.generic_image.Decoder(),
            'image/gif': photo_save.decoders.gif.Decoder()
        }

    @comlink.call
    def process_artifact_images(self, images_raw_description):
        logging.info('Processing %d images, first of which is "%s"' % (
            len(images_raw_description), images_raw_description[0]['uri_path']))

        images_description = []
        for image_raw_description in images_raw_description:
            images_description.append(self._process_one_image(image_raw_description))

        return images_description

    def _process_one_image(self, image_raw_description):
        subtitle = image_raw_description['subtitle']
        description = image_raw_description['description']
        source_uri = image_raw_description['uri_path']

        logging.info('Processing "%s"' % source_uri)

        logging.info('Fetching from remote source')

        (image_raw_data, image_raw_mime_type) = self._fetcher.fetch_url(source_uri)
        if image_raw_mime_type not in defines.IMAGE_MIMETYPES:
            raise Error('Unrecognized image type - "%s"' % image_raw_mime_type)

        logging.info('Saving the original locally')

        (storage_path, original_image_uri_path) = self._unique_image_path(image_raw_mime_type, '0000')

        image_raw_file = open(storage_path, 'w')
        image_raw_file.write(image_raw_data)
        image_raw_file.close()

        image_data = {}

        logging.info('Producing optimized, tiled and deframed versions')

        for (screen_config_key, screen_config) in defines.PHOTO_SAVE_SCREEN_CONFIGS.iteritems():
            image_data[screen_config_key] = self._decoders[image_raw_mime_type].decode(
                screen_config_key, screen_config, image_raw_data,
                lambda mime_type, extra_pattern: self._unique_image_path(mime_type, extra_pattern))

        logging.info('Done')

        return {
            'subtitle': subtitle,
            'description': description,
            'source_uri': source_uri,
            'original_image_uri_path': original_image_uri_path,
            'image_data': image_data
        }

    def _unique_image_path(self, mime_type, extra_pattern):
        extension = defines.IMAGE_MIMETYPES_TO_EXTENSION[mime_type]
        unique_file_name = '%s.%s.%s' % (str(uuid.uuid4()), extra_pattern, extension)
        return (defines.PHOTO_SAVE_IMAGE_STORAGE_PATH % unique_file_name, unique_file_name)


def main():
    logging.basicConfig(level=logging.INFO, filename=defines.PHOTO_SAVE_LOG_PATH)

    ser = serializer.Serializer()
    photo_save_service = Service(ser)

    server = transport.Server(defines.PHOTO_SAVE_PORT, ser)
    server.add_service(photo_save_service)
    server.start()


if __name__ == '__main__':
    main()
