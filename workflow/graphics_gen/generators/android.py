import os
import os.path
import shutil

from PIL import Image

import generators


class _Profile(object):
    def __init__(self, out_path, rescale_factor):
        self.out_path = out_path
        self.rescale_factor = rescale_factor


_PROFILES = {
    'none': _Profile('drawable', None),
    'mdpi': _Profile('drawable-mdpi', 48.0 / 144.0),
    'hdpi': _Profile('drawable-hdpi', 72.0 / 144.0),
    'xhdpi': _Profile('drawable-xhdpi', 96.0 / 144.0),
    'xxhdpi': _Profile('drawable-xxhdpi', 144.0 / 144.0)
}

class _XmlHandler(generators._Handler):
    def __init__(self):
        super(_XmlHandler, self).__init__('.xml')

    def __call__(self, output_dir_path, g_name, g_config, path):
        out_path = os.path.join(output_dir_path, _PROFILES['none'].out_path, g_name)
        shutil.copy2(path, out_path)


class _PngHandler(generators._Handler):
    def __init__(self):
        super(_PngHandler, self).__init__('.png')

    def __call__(self, output_dir_path, g_name, g_config, path):
        for p_name, profile in _PROFILES.iteritems():
            if profile.rescale_factor is None:
                continue

            out_path = os.path.join(output_dir_path, profile.out_path, g_name)
            image = Image.open(path)
            desired_width = int(image.size[0] * profile.rescale_factor)
            image_resized = generators._resize_to_width(image, desired_width)
            image_resized[0].save(out_path)


_HANDLERS = {
    '.xml': _XmlHandler(),
    '.png': _PngHandler()
}


class Generator(generators.BaseGenerator):
    def __init__(self, output_dir_path):
        super(Generator, self).__init__(output_dir_path)

    def __enter__(self):
        # Only create the root resources directory if it does not exist. In Android, this directory
        # is shared with other resources, not all of them controlled by this application, so we
        # don't want to mess with it if it exists already.
        if not os.path.exists(self.output_dir_path):
            os.mkdir(self.output_dir_path)

        # Generate the associated directory for each drawable. In Android, the full set of resources
        # is spread amongst quality specific directories. These are under our control, so we
        # recreate them.
        for p_name, profile in _PROFILES.iteritems():
            profile_path = os.path.join(self.output_dir_path, profile.out_path)
            generators._recreate_directory(profile_path)

        return self

    def generate(self, g_name, g_config):
        path = g_config['path']
        path_type = os.path.splitext(path)[1]
        _HANDLERS[path_type](self.output_dir_path, g_name, g_config, path)
