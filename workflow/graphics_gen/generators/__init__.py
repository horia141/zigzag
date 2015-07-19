import os
import shutil

from PIL import Image


class BaseGenerator(object):
    def __init__(self, output_dir_path):
        self._output_dir_path = output_dir_path

    def generate(self, g_name, g_config):
        raise NotImplementedError()

    def __enter__(self):
        raise NotImplementedError()

    def __exit__(self, type, value, traceback):
        return False

    @property
    def output_dir_path(self):
        return self._output_dir_path


class _Handler(object):
    def __init__(self, extension):
        self._extension = extension

    def __call__(self):
        raise NotImplementedError()


def _resize_to_width(image, desired_width):
    (width, height) = image.size
    aspect_ratio = float(height) / float(width)
    desired_height = int(aspect_ratio * desired_width)
    desired_height = desired_height + desired_height % 2 # Always a multiple of 2.
    image_resized = image.resize((desired_width, desired_height), Image.ANTIALIAS)
    return (image_resized, desired_height)


def _recreate_directory(dir_path):
    if os.path.exists(dir_path):
        if os.path.isfile(dir_path):
            os.remove(dir_path)
        else:
            shutil.rmtree(dir_path)
    os.mkdir(dir_path)
