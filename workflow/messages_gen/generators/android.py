import re

import generators


_MASTER_TEMPLATE_HEADER = u'<?xml version="1.0" encoding="utf-8"?>\n<resources>'
_MASTER_TEMPLATE_FOOTER = u'</resources>'
_RECORD_TEMPLATE = u'<string name="{0}">{1}</string>'
_PATTERN_RE = re.compile('[{](\d+)[}]')


class Generator(generators.BaseGenerator):
    def __init__(self, output_path):
        super(Generator, self).__init__(output_path)

    def __enter__(self):
        self.output_file.write(_MASTER_TEMPLATE_HEADER)
        self.output_file.write('\n')
        return self

    def __exit__(self, type, value, traceback):
        self.output_file.write(_MASTER_TEMPLATE_FOOTER)
        self.output_file.write('\n')
        self.output_file.close()
        return False

    def generate(self, k, m):
        mc = self._clean_message(m)
        self.output_file.write('  ')
        self.output_file.write(_RECORD_TEMPLATE.format(k, mc['text']).encode('utf-8'))
        self.output_file.write('\n')

    def _clean_message(self, m):
        m_text_1 = m['text'].replace("'", "\\'")
        m_text_2 = m_text_1.replace('"', '\\"')
        m_text_3 = re.sub(_PATTERN_RE, r'%\1$s', m_text_2)
        return {
            'text': m_text_3,
            'desc': m['desc'],
            'platform': m['platform']
            }
