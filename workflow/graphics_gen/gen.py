import argparse
import json
import os
import os.path
import shutil

from PIL import Image
import yaml


PLATFORMS = frozenset(['android', 'ios'])
USAGES = frozenset(['appicon', 'launcher', 'asset'])


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
                image_resized = _resize_to_width(image, desired_width)
                image_resized[0].save(out_path)
        else:
            raise Exception('Unsupported asset type "%s"' % path_type)


class IOSGenerator(GraphicsGenerator):
    class Profile(object):
        def __init__(self, out_path_mod, rescale_factor):
            self.out_path_mod = out_path_mod
            self.rescale_factor = rescale_factor

        def modify_path(self, path):
            if self.out_path_mod == '1x':
                (root_path, ext_path) = os.path.splitext(path)
                return '%s%s' % (root_path, '.png')
            else:
                (root_path, ext_path) = os.path.splitext(path)
                return '%s@%s%s' % (root_path, self.out_path_mod, '.png')

    _PROFILES = {
        '1x': Profile('1x', 29.0 / 60.0),
        '2x': Profile('2x', 40.0 / 60.0),
        '3x': Profile('3x', 60.0 / 60.0)
        }

    _MODIFIER_FOR_USAGE = {
        'appicon': 'appiconset',
        'launcher': 'launchimage',
        'asset': 'imageset'
        }

    def __init__(self, output_dir_path):
        super(IOSGenerator, self).__init__()
        self._output_dir_path = output_dir_path

    def __enter__(self):
        if os.path.exists(self._output_dir_path):
            if os.path.exists(self._output_dir_path):
                if os.path.isfile(self._output_dir_path):
                    os.remove(self._output_dir_path)
                else:
                    shutil.rmtree(self._output_dir_path)
        os.mkdir(self._output_dir_path)
        return self

    def generate(self, g_name, g_config):
        (g_name_base, g_name_ext) = os.path.splitext(g_name)
        path = g_config['path']
        path_type = os.path.splitext(path)[1]

        if path_type == '.png':
            dir_path = os.path.join(self._output_dir_path,
                '%s.%s' % (g_name_base, self._MODIFIER_FOR_USAGE[g_config['usage']]))
            base_out_path = os.path.join(dir_path, g_name)
            os.mkdir(dir_path)

            metadata = {
                'images': [],
                'info': {
                    'version': 1,
                    'author': 'xcode' # Should be the assets pipeline.
                    }
                }

            for g_name, profile in self._PROFILES.iteritems():
                out_path = profile.modify_path(base_out_path)
                image = Image.open(path)
                desired_width = int(image.size[0] * profile.rescale_factor)
                image_resized = _resize_to_width(image, desired_width)
                image_resized[0].save(out_path)
                metadata['images'].append({
                        'idiom': 'universal',
                        'scale': profile.out_path_mod,
                        'filename': os.path.basename(out_path)
                        })

            with open(os.path.join(dir_path, 'Contents.json'), 'w') as contents_file:
                contents_file.write(json.dumps(metadata))
        elif path_type == '.gif':
            dir_path_pattern = os.path.join(self._output_dir_path,
                '%s%%02d.imageset' % g_name_base)
            g_name_pattern = '%s%%02d%s' % (g_name_base, g_name_ext)
            base_out_path_pattern = os.path.join(dir_path_pattern, g_name_pattern)

            fr = 0
            image = Image.open(path)
            while True:
                try:
                    image.seek(fr)
                except EOFError:
                    break

                dir_path = dir_path_pattern % fr
                os.mkdir(dir_path)
                metadata = {
                    'images': [],
                    'info': {
                        'version': 1,
                        'author': 'xcode' # Should be the assets pipeline.
                        }
                    }

                for g_name, profile in self._PROFILES.iteritems():
                    out_path = profile.modify_path(base_out_path_pattern % (fr, fr))
                    frame = image.copy()
                    desired_width = int(frame.size[0] * profile.rescale_factor)
                    frame_resized = _resize_to_width(image, desired_width)
                    frame_resized[0].save(out_path)
                    metadata['images'].append({
                            'idiom': 'universal',
                            'scale': profile.out_path_mod,
                            'filename': os.path.basename(out_path)
                            })

                    with open(os.path.join(dir_path, 'Contents.json'), 'w') as contents_file:
                        contents_file.write(json.dumps(metadata))
                
                fr = fr + 1


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

            if 'platform' not in g_config or 'usage' not in g_config:
                raise Exception('Required keys missing for "%s"' % g_name)

            if not isinstance(g_config['platform'], basestring):
                raise Exception('Invalid format for platform for "%s"' % g_name)

            platforms = g_config['platform'].split('+')

            if any(p not in PLATFORMS for p in platforms):
                raise Exception('Invalid platforms for "%s"' % g_name)

            g_config['platform'] = frozenset(platforms)

            if not isinstance(g_config['usage'], basestring):
                raise Exception('Invalid format for usage for "%s"' % g_name)

            if g_config['usage'] not in USAGES:
                raise Exception('Invalid usage for "%s"' % g_name)

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
