import argparse

import yaml

import generators.android
import generators.ios


PLATFORMS = set(['android', 'ios'])


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--config_path', type=str, required=True,
        help='Configuration path for messages assets')
    parser.add_argument('--android_output_path', type=str, required=True,
        help='Output file for Android messages')
    parser.add_argument('--ios_output_path', type=str, required=True,
        help='Output file for iOS messages')
    args = parser.parse_args()

    config_for_platform = _load_config(args.config_path)

    with generators.android.Generator(args.android_output_path) as generator:
        for k, m in config_for_platform['android'].iteritems():
            generator.generate(k, m)

    with generators.ios.Generator(args.ios_output_path) as generator:
        for k, m in config_for_platform['ios'].iteritems():
            generator.generate(k, m)


def _load_config(config_path):
    config_for_platform = dict((k, {}) for k in PLATFORMS)

    with open(config_path) as config_file:
        config = yaml.load(config_file)

        if not isinstance(config, dict):
            raise Exception('Invalid format for config file')

        for k, m in config.iteritems():
            if not isinstance(k, basestring):
                raise Exception('Key "%s" is not a string' % str(k))

            if not isinstance(m, dict):
                raise Exception('Message "%s" is not a correct format' % k)

            if 'text' not in m or 'desc' not in m or 'platform' not in m:
                raise Exception('Message "%s" has no text, desc or platform keys' % k)

            if not isinstance(m['text'], basestring):
                raise Exception('Message "%s" has no string for key text' % k)

            if not isinstance(m['desc'], basestring):
                raise Exception('Message "%s" has no string for key desc' % k)

            if not isinstance(m['platform'], basestring):
                raise Exception('Invalid format for platform for "%s"' % k)

            platforms = m['platform'].split('+')

            if any(p not in PLATFORMS for p in platforms):
                raise Exception('Invalid platforms for "%s"' % k)

            m['platform'] = frozenset(platforms)

            for p in platforms:
                config_for_platform[p][k] = _clean(m)

        config_keys = set(config.iterkeys())

        if len(config_keys) != len(config):
            raise Exception('Duplicate keys in "%s"' % messages_path)

    return config_for_platform


def _clean(m):
    return {
        'text': m['text'].strip(),
        'desc': m['desc'].strip(),
        'platform': m['platform']
        }


if __name__ == '__main__':
    main()
