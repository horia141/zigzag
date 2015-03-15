"""A service which processes the images found for an artifact."""

import logging
import uuid
import cStringIO as StringIO

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport
from PIL import Image

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

        (storage_path, original_image_uri_path) = self._unique_image_path(image_raw_mime_type)

        image_raw_file = open(storage_path, 'w')
        image_raw_file.write(image_raw_data)
        image_raw_file.close()

        image_data = {}

        logging.info('Producing optimized, tiled and deframed versions')

        for (screen_config_key, screen_config) in defines.PHOTO_SAVE_SCREEN_CONFIGS.iteritems():
            if image_raw_mime_type == 'image/gif':
                image_data[screen_config_key] = self._process_one_image_as_animation_set(
                    screen_config_key, screen_config, image_raw_data)
            else:
                image_data[screen_config_key] = self._process_one_image_as_image_set(
                    screen_config_key, screen_config, image_raw_data)

        logging.info('Done')

        return {
            'subtitle': subtitle,
            'description': description,
            'source_uri': source_uri,
            'original_image_uri_path': original_image_uri_path,
            'image_data': image_data
        }

    def _process_one_image_as_image_set(self, screen_config_key, screen_config, image_raw_data):
        logging.info('Handling the image as a (possibly tiled) image for screen config "%s"' % screen_config_key)

        logging.info('Decoding image')

        image = Image.open(StringIO.StringIO(image_raw_data))
        (width, height) = image.size
        aspect_ratio = float(height) / float(width)

        logging.info('Resizing to screen config width')

        desired_width = screen_config['width']
        assert desired_width <= defines.IMAGE_MAX_WIDTH
        desired_height = int(aspect_ratio * desired_width)
        image_resized = image.resize((desired_width, desired_height), Image.ANTIALIAS)

        logging.info('Optimizing and saving full image')

        (full_storage_path, full_uri_path) = self._unique_image_path('image/jpeg')
        image_resized.save(full_storage_path, **defines.PHOTO_SAVE_JPEG_OPTIONS)

        tile_count = desired_height / defines.IMAGE_MAX_HEIGHT + 1
        tiles_desc = []

        logging.info('Extracting %d tiles' % tile_count)

        for ii in range(tile_count):
            logging.info('Processing tile %d' % (ii + 1))
            # Lazy copy from the resized image.
            tile_upper = ii * defines.IMAGE_MAX_HEIGHT
            tile_lower = min((ii + 1) * defines.IMAGE_MAX_HEIGHT, desired_height)
            tile_height = tile_lower - tile_upper
            image_tile = image_resized.crop((0, tile_upper, desired_width, tile_lower))
            logging.info('Optimizing and saving tile')
            (tile_storage_path, tile_uri_path) = self._unique_image_path('image/jpeg')
            image_tile.save(tile_storage_path, **defines.PHOTO_SAVE_JPEG_OPTIONS)

            tiles_desc.append({
                'width': desired_width,
                'height': tile_height,
                'uri_path': tile_uri_path
            })

        return {
            'type': defines.IMAGE_IMAGE_SET,
            'full_image_desc': {
                'width': desired_width,
                'height': desired_height,
                'uri_path': full_uri_path
            },
            'tiles_desc': tiles_desc
        }

    def _process_one_image_as_animation_set(self, screen_config_key, screen_config, image_raw_data):
        logging.info('Handling the image as an animation for screen config "%s"' % screen_config_key)

        image = Image.open(StringIO.StringIO(image_raw_data))

        (width, height) = image.size
        aspect_ratio = float(height) / float(width)

        desired_width = screen_config['width']
        assert desired_width <= defines.IMAGE_MAX_WIDTH
        desired_height = int(aspect_ratio * desired_width)

        if desired_height > defines.IMAGE_MAX_HEIGHT:
            return {
                'type': defines.IMAGE_TOO_LARGE
            }

        frame = 0
        frames_desc = []

        while True:
            try:
                image.seek(frame)
                logging.info('Processing frame %d' % (frame + 1))
                image_resized = image.resize((desired_width, desired_height), Image.ANTIALIAS)
                image_resized_p = image_resized.convert('RGBA')

                logging.info('Optimizing and saving full image')
                (frame_storage_path, frame_uri_path) = self._unique_image_path('image/jpeg')
                image_resized_p.save(frame_storage_path, **defines.PHOTO_SAVE_JPEG_OPTIONS)

                frames_desc.append({
                    'width': desired_width,
                    'height': desired_height,
                    'uri_path': frame_uri_path
                })

                frame += 1
            except EOFError:
                break

        return {
            'type': defines.IMAGE_ANIMATION_SET,
            'time_between_frames_ms': image.info['duration'],
            'frames_desc': frames_desc
        }

    def _unique_image_path(self, mime_type):
        extension = defines.IMAGE_MIMETYPES_TO_EXTENSION[mime_type]
        unique_file_name = '%s.%s' % (str(uuid.uuid4()), extension)

        return (defines.PHOTO_SAVE_IMAGE_STORAGE_PATH % unique_file_name, unique_file_name)

def main():
    logging.basicConfig(level=logging.INFO) #, filename=defines.PHOTO_SAVE_LOG_PATH)

    ser = serializer.Serializer()
    photo_save_service = Service(ser)

    server = transport.Server(defines.PHOTO_SAVE_PORT, ser)
    server.add_service(photo_save_service)
    server.start()

if __name__ == '__main__':
    main()

