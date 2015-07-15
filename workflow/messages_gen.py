import argparse
import re

import yaml


class MessagesGenerator(object):
    def __init__(self):
        pass

    def generate(self, messages):
        raise NotImplementedError()


class AndroidGenerator(MessagesGenerator):
    _MASTER_TEMPLATE_HEADER = u"""<?xml version="1.0" encoding="utf-8"?>
<resources>"""
    _MASTER_TEMPLATE_FOOTER = u"""</resources>"""
    _RECORD_TEMPLATE = u'<string name="{0}">{1}</string>'

    def __init__(self, output_path):
        super(AndroidGenerator, self).__init__()
        self._output_path = output_path

    def generate(self, messages):
        with open(self._output_path, 'w') as output_file:
            output_file.write(self._MASTER_TEMPLATE_HEADER)
            output_file.write('\n')

            for key in sorted(messages.iterkeys()):
                message = messages[key]
                output_file.write('  ')
                output_file.write(self._RECORD_TEMPLATE.format(key, message).encode('utf-8'))
                output_file.write('\n')
            
            output_file.write(self._MASTER_TEMPLATE_FOOTER)
            output_file.write('\n')

    def _clean_message(self, message):
        return message


class IOSGenerator(MessagesGenerator):
    _RECORD_TEMPLATE = u'"{0}" = "{1}"'
    _PATTERN_RE = re.compile('[{](\d+)[}]')

    def __init__(self, output_path):
        super(IOSGenerator, self).__init__()
        self._output_path = output_path

    def generate(self, messages):
        with open(self._output_path, 'w') as output_file:
            for key in sorted(messages.iterkeys()):
                message = self._clean_message(messages[key])
                output_file.write(self._RECORD_TEMPLATE.format(key, message).encode('utf-8'))
                output_file.write('\n')

    def _clean_message(self, message):
        message_1 = message.replace('"', '\\"')
        message_2 = re.sub(self._PATTERN_RE, r'%\1$@', message_1)
        return message_2


PLATFORMS = set(['android', 'ios'])

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--android_output_path', type=str, required=True,
        help='Output file for Android messages')
    parser.add_argument('--ios_output_path', type=str, required=True,
        help='Output file for iOS messages')
    parser.add_argument('messages_files', type=str, nargs='+',
        help='Messages and platforms to generate for')
    args = parser.parse_args()

    GENERATORS = {
        'android': AndroidGenerator(args.android_output_path),
        'ios': IOSGenerator(args.ios_output_path)
        }

    platform_messages_files = _split_messages_by_platform(args.messages_files)

    for platform, messages_files in platform_messages_files.iteritems():
        messages = _clean_messages(_load_messages(messages_files))
        GENERATORS[platform].generate(messages)


def _split_messages_by_platform(messages_files):
    gen_file_pairs_map = dict((p, []) for p in PLATFORMS)

    for fgs in messages_files:
        fgs_ls = fgs.split('=')
        assert len(fgs_ls) == 2
        f, gs = fgs_ls
        for g in gs.split(':'):
            if g not in PLATFORMS:
                raise Exception('Unknown platform "%s"' % g)
            gen_file_pairs_map[g].append(f)

    return gen_file_pairs_map


def _load_messages(messages_paths):
    messages_keys = set()
    messages_maps = {}

    for messages_path in messages_paths:
        with open(messages_path) as messages_file:
            messages_map = yaml.load(messages_file)

            if messages_map is None:
                continue

            if not isinstance(messages_map, dict):
                raise Exception('Bad format for "%s"' % messages_path)

            for k, m in messages_map.iteritems():
                if not isinstance(k, basestring):
                    raise Exception('Key "%s" is not a string for "%s"' % (str(k), messages_path))

                if not isinstance(m, basestring):
                    raise Exception('Message "%s" is not a string "%s"' % (str(m), messages_path))

            messages_map_keys = set(messages_map.iterkeys())

            if len(messages_map_keys) != len(messages_map):
                raise Exception('Duplicate keys in "%s"' % messages_path)

            common_keys = messages_keys & messages_map_keys

            if len(common_keys) != 0:
                raise Exception('Duplicate keys with other files in "%s"' % messages_path)

            messages_keys |= messages_map_keys
            messages_maps.update(messages_map)    

    return messages_maps


def _clean_messages(messages):
    def clean(m):
        return m.strip()

    cleaned_messages = {}

    for k, m in messages.iteritems():
        cleaned_messages[k] = clean(m)

    return cleaned_messages


if __name__ == '__main__':
    main()
