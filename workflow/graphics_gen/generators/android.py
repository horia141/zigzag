import os
import os.path
import shutil

from PIL import Image

import generators


class Generator(generators.BaseGenerator):
    class Profile(object):
        def __init__(self, out_path, rescale_factor):
            self.out_path = out_path
            self.rescale_factor = rescale_factor

    _PROFILES = {
        'none': Profile('drawable', None),
        'mdpi': Profile('drawable-mdpi', 48.0 / 144.0),
        'hdpi': Profile('drawable-hdpi', 72.0 / 144.0),
        'xhdpi': Profile('drawable-xhdpi', 96.0 / 144.0),
        'xxhdpi': Profile('drawable-xxhdpi', 144.0 / 144.0)
    }

    def __init__(self, output_dir_path):
        super(Generator, self).__init__()
        self._output_dir_path = output_dir_path

    def __enter__(self):
        if not os.path.exists(self._output_dir_path):
            os.mkdir(self._output_dir_path)
        for p_name, profile in self._PROFILES.iteritems():
            profile_path = os.path.join(self._output_dir_path, profile.out_path)
            if os.path.exists(profile_path):
                if os.path.isfile(profile_path):
                    os.remove(profile_path)
                else:
                    shutil.rmtree(profile_path)
            os.mkdir(profile_path)
        return self

    def generate(self, g_name, g_config):
        path = g_config['path']
        path_type = os.path.splitext(path)[1]

        if path_type == '.xml':
            out_path = os.path.join(self._output_dir_path, self._PROFILES['none'].out_path, g_name)
            shutil.copy2(path, out_path)
        elif path_type == '.png':
            for p_name, profile in self._PROFILES.iteritems():
                if profile.rescale_factor is None:
                    continue

                out_path = os.path.join(self._output_dir_path, profile.out_path, g_name)
                image = Image.open(path)
                desired_width = int(image.size[0] * profile.rescale_factor)
                image_resized = self._resize_to_width(image, desired_width)
                image_resized[0].save(out_path)
        else:
            raise Exception('Unsupported asset type "%s"' % path_type)
