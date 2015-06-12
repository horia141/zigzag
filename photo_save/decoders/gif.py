"""A decoder for GIF images. Splits out the frames and stores them separately."""

import cStringIO as StringIO
import logging
import math
import subprocess

import common.defines.constants as defines
import common.model.ttypes as model
import photo_save_pb.ttypes as photo_save_types
import photo_save.decoders as decoders
from PIL import Image


class Decoder(decoders.Decoder):
    def decode(self, screen_config_key, screen_config, video, video_path, unique_video_path_fn):
        logging.info('Handling the photo as an animation for screen config "%s"' % screen_config_key)

        (width, height) = video.size
        aspect_ratio = float(height) / float(width)

        desired_width = screen_config.width
        assert desired_width % 2 == 0
        assert desired_width <= defines.PHOTO_MAX_WIDTH
        desired_height = int(aspect_ratio * desired_width)
        # Ensure that desired_height is a multiple of 2, by rounding up.
        desired_height += desired_height % 2

        if desired_height > defines.PHOTO_MAX_HEIGHT:
            return model.PhotoData(too_big_photo_data=model.TooBigPhotoData())

        logging.info('Saving first frame')
        (first_frame_storage_path, first_frame_uri_path) = unique_video_path_fn('image/jpeg')
        first_frame = video.convert('RGBA').resize((desired_width, desired_height), Image.ANTIALIAS)
        first_frame.save(first_frame_storage_path, quality=defines.IMAGE_SAVE_JPEG_OPTIONS_QUALITY,
                           optimize=defines.IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE,
                           progressive=defines.IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE)

        logging.info('Converting image sequence to video')
        (video_storage_path, video_uri_path) = unique_video_path_fn('video/mp4')
        if 'duration' in video.info and video.info['duration'] > 0:
            time_between_frames_ms = video.info['duration']
        else:
            time_between_frames_ms = defines.DEFAULT_TIME_BETWEEN_FRAMES_MS
        frames_per_sec = math.ceil(1000.0 / time_between_frames_ms)
        try:
            cmd_line = ['sources/photo_save/decoders/gif_to_mp4.sh', video_path,
                '%d' % frames_per_sec, '%d' % desired_width, '%d' % desired_height, 
                defines.VIDEO_SAVE_BITRATE, video_storage_path]
            logging.info('Conversion command line "%s"', ' '.join(cmd_line))
            subprocess.check_output(cmd_line)
        except subprocess.CalledProcessError as e:
            raise photo_save_types.Error(message='Could not decode gif')

        first_frame = model.TileData(desired_width, desired_height, first_frame_uri_path)
        video = model.TileData(desired_width, desired_height, video_uri_path)
        return model.PhotoData(video_photo_data=model.VideoPhotoData(first_frame, video,
            frames_per_sec, time_between_frames_ms))
