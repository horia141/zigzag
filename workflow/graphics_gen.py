import argparse

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
    def __init__(self, output_dir_path):
        super(AndroidGenerator, self).__init__()
        self._output_dir_path = output_dir_path

    def generate(self, g_name, g_config):
        print g_name


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

            for p in platforms:
                config_for_platform[p][g_name] = g_config

    return config_for_platform


if __name__ == '__main__':
    main()
