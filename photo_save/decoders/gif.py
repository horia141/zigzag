"""A decoder for GIF images. Splits out the frames and stores them separately."""

import cStringIO as StringIO
import logging
import math
import subprocess

import common.defines as defines
import photo_save.decoders as decoders
from PIL import Image


class Decoder(decoders.Decoder):
    def decode(self, screen_config_key, screen_config, image_raw_data, unique_image_path_fn):
        logging.info('Handling the image as an animation for screen config "%s"' % screen_config_key)

        logging.info('Decoding image')

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
        last_frame_uri_path = None

        (frame_storage_path_pattern, frame_uri_path_pattern) = unique_image_path_fn('image/jpeg', '%04d')

        while True:
            try:
                image.seek(frame)
                logging.info('Processing frame %d' % (frame + 1))
                image_resized = image.convert('RGBA').resize((desired_width, desired_height), Image.ANTIALIAS)

                logging.info('Optimizing and saving full image')
                frame_storage_path = frame_storage_path_pattern % frame
                frame_uri_path = frame_uri_path_pattern % frame
                image_resized.save(frame_storage_path, **defines.PHOTO_SAVE_JPEG_OPTIONS)
                last_frame_uri_path = frame_uri_path

                frames_desc.append({
                    'width': desired_width,
                    'height': desired_height,
                    'uri_path': frame_uri_path
                })

                frame += 1
            except EOFError:
                break

        if frame > 1:
            logging.info('Converting image sequence to video')
            (video_storage_path_pattern, video_uri_path_pattern) = unique_image_path_fn('video/avi', '0000')
            if 'duration' in image.info and image.info['duration'] > 0:
                time_between_frames_ms = image.info['duration']
            else:
                time_between_frames_ms = defines.DEFAULT_TIME_BETWEEN_FRAMES_MS
            framerate = math.ceil(1000.0 / time_between_frames_ms)
            subprocess.call(['avconv', '-i', frame_storage_path_pattern, '-s', 
                             '%dx%d' % (desired_width, desired_height), '-r', '%d' % framerate,
                             '-loglevel', 'panic', '-nostats', video_storage_path_pattern])

            return {
                'type': defines.IMAGE_ANIMATION_SET,
                'full_image_desc': {
                    'width': desired_width,
                    'height': desired_height,
                    'uri_path': last_frame_uri_path
                },
                'video_desc': {
                    'width': desired_width,
                    'height': desired_height,
                    'uri_path': video_uri_path_pattern
                },
                'time_between_frames_ms': time_between_frames_ms,
                'frames_desc': frames_desc
            }
        else:
            return {
                'type': defines.IMAGE_IMAGE_SET,
                'full_image_desc': {
                    'width': desired_width,
                    'height': desired_height,
                    'uri_path': last_frame_uri_path
                },
                'tiles_desc': frames_desc
            }
