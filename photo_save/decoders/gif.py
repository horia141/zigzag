"""A decoder for GIF images. Splits out the frames and stores them separately."""

import cStringIO as StringIO
import logging
import math
import subprocess

import common.defines as defines
import photo_save.decoders as decoders
from PIL import Image


class Decoder(decoders.Decoder):
    def decode(self, screen_config_key, screen_config, video, video_path, unique_video_path_fn):
        logging.info('Handling the photo as an animation for screen config "%s"' % screen_config_key)

        (width, height) = video.size
        aspect_ratio = float(height) / float(width)

        desired_width = screen_config['width']
        assert desired_width <= defines.PHOTO_MAX_WIDTH
        desired_height = int(aspect_ratio * desired_width)

        if desired_height > defines.PHOTO_MAX_HEIGHT:
            return {
                'type': defines.PHOTO_TOO_LARGE
            }

        logging.info('Saving first frame')
        (first_frame_storage_path, first_frame_uri_path) = unique_video_path_fn('image/jpeg')
        first_frame = video.convert('RGBA').resize((desired_width, desired_height), Image.ANTIALIAS)
        first_frame.save(first_frame_storage_path, **defines.IMAGE_SAVE_JPEG_OPTIONS)

        logging.info('Converting image sequence to video')
        (video_storage_path, video_uri_path) = unique_video_path_fn('video/mp4')
        if 'duration' in video.info and video.info['duration'] > 0:
            time_between_frames_ms = video.info['duration']
        else:
            time_between_frames_ms = defines.DEFAULT_TIME_BETWEEN_FRAMES_MS
        framerate = math.ceil(1000.0 / time_between_frames_ms)
        subprocess.check_output(['photo_save/decoders/gif_to_mp4.sh', video_path, '%d' % framerate,
                                 '%d' % desired_width, '%d' % desired_height, defines.VIDEO_SAVE_BITRATE,
                                 video_storage_path])

        return {
            'type': defines.PHOTO_VIDEO,
            'first_frame_desc': {
                'width': desired_width,
                'height': desired_height,
                'uri_path': first_frame_uri_path
            },
            'video_desc': {
                'width': desired_width,
                'height': desired_height,
                'uri_path': video_uri_path
            },
            'time_between_frames_ms': time_between_frames_ms,
            'framerate': framerate
        }
