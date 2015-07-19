import re

import generators


_RECORD_TEMPLATE = u'"{0}" = "{1}"'
_COMMENT_TEMPLATE = u'/* {0} */'
_PATTERN_RE = re.compile('[{](\d+)[}]')


class Generator(generators.BaseGenerator):
    def __init__(self, output_path):
        super(Generator, self).__init__(output_path)

    def generate(self, k, m):
        mc = self._clean_message(m)
        self.output_file.write(_COMMENT_TEMPLATE.format(mc['desc']).encode('utf-8'))
        self.output_file.write('\n')
        self.output_file.write(_RECORD_TEMPLATE.format(k, mc['text']).encode('utf-8'))
        self.output_file.write('\n\n')

    def _clean_message(self, m):
        m_text_1 = m['text'].replace('"', '\\"')
        m_text_2 = re.sub(_PATTERN_RE, r'%\1$@', m_text_1)
        return {
            'text': m_text_1,
            'desc': m['desc'],
            'platform': m['platform']
            }
