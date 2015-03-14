"""A service which processes the images found for an artifact."""

import logging
import uuid

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport

import common.defines as defines
import fetcher


class Error(Exception):
    pass


class Service(comlink.Service):
    def __init__(self, ser):
        super(Service, self).__init__()
        client = transport.Client(defines.FETCHER_PORT, ser)
        self._fetcher = fetcher.Service.client(client)

    @comlink.call
    def process_artifact_images(self, images_raw_description):
        logging.info('Processing "%d" images, first of which is "%s"' % (
            len(images_raw_description), images_raw_description[0]['url_path']))

        images_description = []
        for image_raw_description in images_raw_description:
            images_description.append(self._process_one_image(image_raw_description))

        return images_description

    def _process_one_image(self, image_raw_description):
        subtitle = images_raw_description['subtitle']
        description = images_raw_description['description']
        source_uri = images_raw_description['uri_path']

        logging.info('Processing "%s"' % source_uri)

        (image_raw_data, image_raw_mime_type) = self._fetcher.fetch_url(source_url)
        if image_raw_mime_type not in defines.IMAGE_MIMETYPES:
            raise Error('Unrecognized image type - "%s"' % image_raw_mime_type)

        (storage_path, original_image_uri_path) = self._unique_image_path(image_raw_mime_type)

        image_raw_file = open(storage_path, 'w')
        image_raw_file.write(image_raw_data)
        image_raw_file.close()

        return image_raw_description

    def _unique_image_path(self, mime_type):
        extension = defines.IMAGE_MIMETYPES_TO_EXTENSION[mime_type]
        unique_file_name = '%s.%s' % (str(uuid.uuid4()), extension)

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

