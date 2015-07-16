import argparse
import os
import os.path
import shutil

from PIL import Image
import yaml


PLATFORMS = frozenset(['android', 'ios'])
ACTIONS = frozenset(['copy', 'transcode'])


class GraphicsGenerator(object):
    def __init__(self):
        pass

    def generate(self, g_name, g_config):
        raise NotImplementedError()

    def __enter__(self):
        return self

    def __exit__(self, type, value, traceback):
        return False


class AndroidGenerator(GraphicsGenerator):
    class Profile(object):
        def __init__(self, out_path, target_width):
            self.out_path = out_path
            self.target_width = target_width

    _PROFILES = {
        'none': Profile('drawable', None),
        'mdpi': Profile('drawable-mdpi', 48),
        'hdpi': Profile('drawable-hdpi', 72),
        'xhdpi': Profile('drawable-xhdpi', 96),
        'xxhdpi': Profile('drawable-xxhdpi', 144)
    }

    def __init__(self, output_dir_path):
        super(AndroidGenerator, self).__init__()
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
        for p_name, profile in self._PROFILES.iteritems():
            path = g_config['path']
            out_path = os.path.join(self._output_dir_path, profile.out_path, g_name)

            if g_config['action'] == 'copy':
                shutil.copy2(path, out_path)
            elif profile.target_width is not None:
                image = Image.open(path)
                image_resized = _resize_to_width(image, profile.target_width)
                image_resized[0].save(out_path)


class IOSGenerator(GraphicsGenerator):
    def __init__(self, output_dir_path):
        super(IOSGenerator, self).__init__()
        self._output_dir_path = output_dir_path

    def generate(self, g_name, g_config):
        print g_name


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--config_path', type=str, required=True,
       help='Configuration path for graphics assets')
    parser.add_argument('--android_output_dir_path', type=str, required=True,
       help='Output directory for Android graphics. Will be cleared')
    parser.add_argument('--ios_output_dir_path', type=str, required=True,
       help='Output directory for iOS graphics. Will be cleared')
    args = parser.parse_args()

    config_for_platform = _load_config(args.config_path)

    with AndroidGenerator(args.android_output_dir_path) as generator:
        for g_name, g_config in config_for_platform['android'].iteritems():
            generator.generate(g_name, g_config)

    with IOSGenerator(args.ios_output_dir_path) as generator:
        for g_name, g_config in config_for_platform['ios'].iteritems():
            generator.generate(g_name, g_config)


def _load_config(config_path):
    config_for_platform = dict((k, {}) for k in PLATFORMS)

    with open(config_path) as config_file:
        config = yaml.load(config_file)

        if not isinstance(config, dict):
            raise Exception('Invalid format for config file')

        for g_name, g_config in config.iteritems():
            if not isinstance(g_name, basestring):
                raise Exception('Key "%s" is not a string' % str(g_name))

            if not isinstance(g_config, dict):
                raise Exception('Invalid format for "%s"' % g_name)

            if 'platform' not in g_config or 'action' not in g_config:
                raise Exception('Required keys missing for "%s"' % g_name)

            if not isinstance(g_config['platform'], basestring):
                raise Exception('Invalid format for platform for "%s"' % g_name)

            platforms = g_config['platform'].split('+')

            if any(p not in PLATFORMS for p in platforms):
                raise Exception('Invalid platforms for "%s"' % g_name)

            g_config['platform'] = frozenset(platforms)

            if not isinstance(g_config['action'], basestring):
                raise Exception('Invalid format for action for "%s"' % g_name)

            if g_config['action'] not in ACTIONS:
                raise Exception('Invalid action for "%s' % g_name)

            g_config['path'] = os.path.join(os.path.dirname(config_path), g_name)

            for p in platforms:
                config_for_platform[p][g_name] = g_config

    return config_for_platform


def _resize_to_width(image, desired_width):
    (width, height) = image.size
    aspect_ratio = float(height) / float(width)
    desired_height = int(aspect_ratio * desired_width)
    desired_height = desired_height + desired_height % 2 # Always a multiple of 2.
    image_resized = image.resize((desired_width, desired_height), Image.ANTIALIAS)
    return (image_resized, desired_height)


if __name__ == '__main__':
    main()
