from PIL import Image


class BaseGenerator(object):
    def __init__(self):
        pass

    def generate(self, g_name, g_config):
        raise NotImplementedError()

    def __enter__(self):
        return self

    def __exit__(self, type, value, traceback):
        return False

    def _resize_to_width(self, image, desired_width):
        (width, height) = image.size
        aspect_ratio = float(height) / float(width)
        desired_height = int(aspect_ratio * desired_width)
        desired_height = desired_height + desired_height % 2 # Always a multiple of 2.
        image_resized = image.resize((desired_width, desired_height), Image.ANTIALIAS)
        return (image_resized, desired_height)
