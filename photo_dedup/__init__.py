import hashlib

import numpy
import scipy.fftpack as fftpack

import common.defines.constants as defines
import utils.photos as photos


def dedup_hash(photo):
    hasher = hashlib.md5()

    if not photos.is_video(photo):
        # Mark the fact that this is in the images space.
        hasher.update('IMAGE')
        # Add the height of the image to the photo hash.
        _, height = photo.size
        hasher.update('%d' % (height / 5))

        # Add the contents of the single frame to the hash.
        _frame_hash(hasher, photo)
    else:
        # Mark the fact that this is in the video space.
        hasher.update('VIDEO')
        # Add the height of the video to the photo hash.
        _, height = photo.size
        hasher.update('%d' % (height / 5))

        # Add the contents of each key frame to the hash. Algorithm is kind of ugly.
        # We can only seek to consecutive frames. So this means we have to do the
        # sequence 0,1,2,... . Whenever we encounter a key frame, we hash it. We stop if
        # ther are no more frames in the video or no more key frames.
        frame_idx = 0
        key_frame_idx = 0
        while True:
            try:
                photo.seek(frame_idx)
            except EOFError:
                break
            if frame_idx == defines.PHOTO_DEDUP_KEY_FRAMES[key_frame_idx]:
                _frame_hash(hasher, photo)
                key_frame_idx += 1
                if key_frame_idx >= len(defines.PHOTO_DEDUP_KEY_FRAMES):
                    break
            frame_idx += 1
        photo.seek(0)

    return hasher.hexdigest()


def _frame_hash(hasher, frame):
    def extract_coeff(coeff):
        return max(min(int(coeff), 1023), -1024) / 16

    frame_gray = frame.convert('L')
    frame_small = photos.resize_to_width(frame_gray, defines.PHOTO_DEDUP_SMALL_WIDTH)
    mat = numpy.array(frame_small, dtype=numpy.float) - 128
    mat_dct = fftpack.dct(fftpack.dct(mat, norm='ortho').T, norm='ortho').T
    hasher.update('%04d' % extract_coeff(mat_dct[0][0]))
    hasher.update('%04d' % extract_coeff(mat_dct[0][1]))
    hasher.update('%04d' % extract_coeff(mat_dct[1][0]))
    hasher.update('%04d' % extract_coeff(mat_dct[1][1]))
