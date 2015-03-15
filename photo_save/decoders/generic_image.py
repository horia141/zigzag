"""A generic decoder for images. Saves as JPEG. Breaks images into tiles."""

import logging
import cStringIO as StringIO

import common.defines as defines
import photo_save.decoders as decoders
from PIL import Image


class Decoder(object):
    def decode(self, screen_config_key, screen_config, image_raw_data, unique_image_path_fn):
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

        (full_storage_path, full_uri_path) = unique_image_path_fn('image/jpeg')
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
            (tile_storage_path, tile_uri_path) = unique_image_path_fn('image/jpeg')
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