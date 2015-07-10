class Counters(object):
    def __init__(self):
        self._counters = {}

    def inc(self, name, value=1):
        key_name = name.lower()
        self._counters[key_name] = self._counters.get(key_name, 0) + value

    def clear(self):
        self._counters = {}

    def format(self, padding=''):
        if len(self._counters) == 0:
            return ''

        names = sorted(self._counters.keys())
        max_name_size = max(len(name) for name in names)
        text = '\n'.join('%s%s  %d'% (padding, name.ljust(max_name_size), self._counters[name])
                         for name in names)
        return text
