"""A service which processes the images found for an artifact."""

import logging

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport

import common.defines as defines


class Service(comlink.Service):
    @comlink.call
    def process_artifact_images(self, images_raw_description):
        logging.info('Processing "%d" images, first of which is "%s"' % (
            len(images_raw_description), images_raw_description[0]['url_path']))

        return images_raw_description

def main():
    logging.basicConfig(level=logging.INFO, filename=defines.PHOTO_SAVE_LOG_PATH)

    ser = serializer.Serializer()
    photo_save_service = Service()

    server = transport.Server(defines.PHOTO_SAVE_PORT, ser)
    server.add_service(photo_save_service)
    server.start()

if __name__ == '__main__':
    main()

