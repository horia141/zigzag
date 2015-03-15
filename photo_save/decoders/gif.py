"""A decoder for GIF images. Splits out the frames and stores them separately."""

import logging
import cStringIO as StringIO

import common.defines as defines
import photo_save.decoders as decoders
from PIL import Image


class Decoder(decoders.Decoder):
    def decode(self, screen_config_key, screen_config, image_raw_data, unique_image_path_fn):
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
                (frame_storage_path, frame_uri_path) = unique_image_path_fn('image/jpeg')
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
