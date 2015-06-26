"""A decoder for GIF images. Splits out the frames and stores them separately."""

import cStringIO as StringIO
import logging
import math
import os
import subprocess
import tempfile

from PIL import Image

import common.defines.constants as defines
import common.model.ttypes as model
import photo_save_pb.ttypes as photo_save_types
import photo_save.decoders as decoders
import rest_api.models as datastore
import utils.photos as photos


class Decoder(decoders.Decoder):
    def decode(self, screen_config_key, screen_config, video, video_raw_content, unique_video_path_fn):
        logging.info('Handling the photo as an animation for screen config "%s"' % screen_config_key)

        (width, height) = video.size

        desired_width = screen_config.width
        assert desired_width % 2 == 0
        assert desired_width <= defines.PHOTO_MAX_WIDTH

        logging.info('Checking that the video does not exist')
        dedup_hash = photo_dedup.dedup_hash(video)
        photo_hash = datastore.photo_exists_by_dedup_hash(dedup_hash)
        if photo_hash is not None:
            datastore.increment_photo_dup_count(photo_hash)
            raise photo_save_types.PhotoAlreadyExists('Photo already exists in database')

        logging.info('Saving first frame')
        (first_frame_storage_path, first_frame_uri_path) = unique_video_path_fn('image/jpeg')
        (first_frame, desired_height) = photos.resize_to_width(video.convert('RGBA'), desired_width)

        if desired_height > defines.PHOTO_MAX_HEIGHT:
            return model.PhotoData(too_big_photo_data=model.TooBigPhotoData())

        first_frame.save(first_frame_storage_path, quality=defines.IMAGE_SAVE_JPEG_OPTIONS_QUALITY,
                           optimize=defines.IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE,
                           progressive=defines.IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE)

        logging.info('Generating storage and uri path')
        (video_storage_path, video_uri_path) = unique_video_path_fn('video/mp4')

        logging.info('Converting image sequence to video')
        if 'duration' in video.info and video.info['duration'] > 0:
            time_between_frames_ms = video.info['duration']
        else:
            time_between_frames_ms = defines.DEFAULT_TIME_BETWEEN_FRAMES_MS
        frames_per_sec = math.ceil(1000.0 / time_between_frames_ms)
        try:
            (tmp_video_fd, tmp_video_path) = tempfile.mkstemp()
            with os.fdopen(tmp_video_fd, 'w') as tmp_video_file:
                tmp_video_file.write(video_raw_content)
            cmd_line = ['sources/photo_save/decoders/gif_to_mp4.sh', tmp_video_path,
                '%d' % frames_per_sec, '%d' % desired_width, '%d' % desired_height, 
                defines.VIDEO_SAVE_BITRATE, video_storage_path]
            logging.info('Conversion command line "%s"', ' '.join(cmd_line))
            subprocess.check_output(cmd_line)
        except (IOError, subprocess.CalledProcessError) as e:
            raise photo_save_types.Error(message='Could not decode gif because "%s"' % str(e))
        finally:
            try:
                os.remove(tmp_video_path)
            except Exception as e:
                pass

        # Mark the video as "existing" only after we're sure of it being properly processed. One
        # can argue that we can still lose it in downstream errors, and that is true. But then we'd 
        # need something like a transaction. A garbage collector would be better here.
        datastore.mark_photo_as_existing(dedup_hash, video_uri_path)

        first_frame = model.TileData(desired_width, desired_height, first_frame_uri_path)
        video = model.TileData(desired_width, desired_height, video_uri_path)
        return model.PhotoData(video_photo_data=model.VideoPhotoData(first_frame, video,
            frames_per_sec, time_between_frames_ms))
