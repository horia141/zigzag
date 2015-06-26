"""A generic decoder for images. Saves as JPEG. Breaks images into tiles."""

import logging
import cStringIO as StringIO

from PIL import Image

import common.defines.constants as defines
import common.model.ttypes as model
import photo_dedup
import photo_save.decoders as decoders
import photo_save_pb.ttypes as photo_save_types
import rest_api.models as datastore
import utils.photos as photos


class Decoder(decoders.Decoder):
    def decode(self, screen_config_key, screen_config, image, unique_photo_path_fn):
        logging.info('Handling the photo as a (possibly tiled) image for screen config "%s"' % screen_config_key)

        image_rgba = image.convert('RGBA')

        logging.info('Resizing to screen config width')

        desired_width = screen_config.width
        assert desired_width <= defines.PHOTO_MAX_WIDTH
        image_resized = photos.resize_to_width(image_rgba, desired_width)

        tile_count = desired_height / defines.PHOTO_MAX_HEIGHT + 1
        tiles = []

        logging.info('Extracting %d tiles' % tile_count)

        dedup_hash = None
        first_tile_uri_path = None

        for ii in range(tile_count):
            logging.info('Processing tile %d' % (ii + 1))
            # Lazy copy from the resized image.
            tile_upper = ii * defines.PHOTO_MAX_HEIGHT
            tile_lower = min((ii + 1) * defines.PHOTO_MAX_HEIGHT, desired_height)
            tile_height = tile_lower - tile_upper
            image_tile = image_resized.crop((0, tile_upper, desired_width, tile_lower))
            logging.info('Generating storage and uri path')
            (tile_storage_path, tile_uri_path) = unique_photo_path_fn('image/jpeg')
            if ii == 0:
                logging.info('Checking that the image does not exist')
                dedup_hash = photo_dedup.dedup_hash(image_tile)
                if datastore.photo_exists_by_dedup_hash(dedup_hash):
                    raise photo_save_types.PhotoAlreadyExists('Photo already exists in database')
                first_tile_uri_path = tile_uri_path
            logging.info('Optimizing and saving tile')
            image_tile.save(tile_storage_path, quality=defines.IMAGE_SAVE_JPEG_OPTIONS_QUALITY,
                           optimize=defines.IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE,
                           progressive=defines.IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE)

            tiles.append(model.TileData(desired_width, tile_height, tile_uri_path))

        # Mark the image as "existing" only after we're sure of it being properly processed. One
        # can argue that we can still lose it in downstream errors, and that is true. But then we'd 
        # need something like a transaction. A garbage collector would be better here.
        datastore.mark_photo_as_existing(dedup_hash, first_tile_uri_path)

        return model.PhotoData(image_photo_data=model.ImagePhotoData(tiles))
