"""A generic decoder for images. Saves as JPEG. Breaks images into tiles."""

import logging
import cStringIO as StringIO

import common.defines.constants as defines
import common.model.ttypes as model
import photo_save.decoders as decoders
from PIL import Image


class Decoder(decoders.Decoder):
    def decode(self, screen_config_key, screen_config, image, unique_photo_path_fn):
        logging.info('Handling the photo as a (possibly tiled) image for screen config "%s"' % screen_config_key)

        image_rgba = image.convert('RGBA')
        (width, height) = image_rgba.size
        aspect_ratio = float(height) / float(width)

        logging.info('Resizing to screen config width')

        desired_width = screen_config.width
        assert desired_width <= defines.PHOTO_MAX_WIDTH
        desired_height = int(aspect_ratio * desired_width)
        image_resized = image_rgba.resize((desired_width, desired_height), Image.ANTIALIAS)

        tile_count = desired_height / defines.PHOTO_MAX_HEIGHT + 1
        tiles = []

        logging.info('Extracting %d tiles' % tile_count)

        for ii in range(tile_count):
            logging.info('Processing tile %d' % (ii + 1))
            # Lazy copy from the resized image.
            tile_upper = ii * defines.PHOTO_MAX_HEIGHT
            tile_lower = min((ii + 1) * defines.PHOTO_MAX_HEIGHT, desired_height)
            tile_height = tile_lower - tile_upper
            image_tile = image_resized.crop((0, tile_upper, desired_width, tile_lower))
            logging.info('Optimizing and saving tile')
            (tile_storage_path, tile_uri_path) = unique_photo_path_fn('image/jpeg')
            image_tile.save(tile_storage_path, quality=defines.IMAGE_SAVE_JPEG_OPTIONS_QUALITY,
                           optimize=defines.IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE,
                           progressive=defines.IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE)

            tiles.append(model.TileData(desired_width, tile_height, tile_uri_path))

        return model.PhotoData(image_photo_data=model.ImagePhotoData(tiles))
