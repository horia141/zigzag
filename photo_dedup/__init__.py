import hashlib

import numpy
import scipy.fftpack as fftpack

import common.defines.constants as defines
import utils.photos as photos


def photo_hash(photo):
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

        # Add the contents of each key frame to the hash.
        for key_frame in defines.PHOTO_DEDUP_KEY_FRAMES:
            try:
                photo.seek(key_frame)
                _frame_hash(hasher, photo)
            except EOFError:
                break
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
