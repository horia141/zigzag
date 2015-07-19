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
