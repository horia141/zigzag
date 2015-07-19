import argparse
import re

import yaml


class BaseGenerator(object):
    def __init__(self, output_path):
        self._output_path = output_path
        self._output_file = open(self._output_path, 'w')

    def __enter__(self):
        return self

    def __exit__(self, type, value, traceback):
        self._output_file.close()
        return False

    def generate(self, messages):
        raise NotImplementedError()

    @property
    def output_path(self):
        return self._output_path

    @property
    def output_file(self):
        assert self._output_file is not None
        return self._output_file


class AndroidGenerator(BaseGenerator):
    _MASTER_TEMPLATE_HEADER = u"""<?xml version="1.0" encoding="utf-8"?>
<resources>"""
    _MASTER_TEMPLATE_FOOTER = u"""</resources>"""
    _RECORD_TEMPLATE = u'<string name="{0}">{1}</string>'
    _PATTERN_RE = re.compile('[{](\d+)[}]')

    def __init__(self, output_path):
        super(AndroidGenerator, self).__init__(output_path)

    def __enter__(self):
        self.output_file.write(self._MASTER_TEMPLATE_HEADER)
        self.output_file.write('\n')
        return self

    def __exit__(self, type, value, traceback):
        self.output_file.write(self._MASTER_TEMPLATE_FOOTER)
        self.output_file.write('\n')
        self.output_file.close()
        return False

    def generate(self, k, m):
        mc = self._clean_message(m)
        self.output_file.write('  ')
        self.output_file.write(self._RECORD_TEMPLATE.format(k, mc['text']).encode('utf-8'))
        self.output_file.write('\n')

    def _clean_message(self, m):
        m_text_1 = m['text'].replace("'", "\\'")
        m_text_2 = m_text_1.replace('"', '\\"')
        m_text_3 = re.sub(self._PATTERN_RE, r'%\1$s', m_text_2)
        return {
            'text': m_text_3,
            'desc': m['desc'],
            'platform': m['platform']
            }


class IOSGenerator(BaseGenerator):
    _RECORD_TEMPLATE = u'"{0}" = "{1}"'
    _COMMENT_TEMPLATE = u'/* {0} */'
    _PATTERN_RE = re.compile('[{](\d+)[}]')

    def __init__(self, output_path):
        super(IOSGenerator, self).__init__(output_path)

    def generate(self, k, m):
        mc = self._clean_message(m)
        self.output_file.write(self._COMMENT_TEMPLATE.format(mc['desc']).encode('utf-8'))
        self.output_file.write('\n')
        self.output_file.write(self._RECORD_TEMPLATE.format(k, mc['text']).encode('utf-8'))
        self.output_file.write('\n\n')

    def _clean_message(self, m):
        m_text_1 = m['text'].replace('"', '\\"')
        m_text_2 = re.sub(self._PATTERN_RE, r'%\1$@', m_text_1)
        return {
            'text': m_text_1,
            'desc': m['desc'],
            'platform': m['platform']
            }


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

    with AndroidGenerator(args.android_output_path) as generator:
        for k, m in config_for_platform['android'].iteritems():
            generator.generate(k, m)

    with IOSGenerator(args.ios_output_path) as generator:
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
