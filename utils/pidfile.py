"""Writing a pidfile."""

import atexit
import os


def write_pidfile(pidfile_path):
    pidfile = open(pidfile_path, 'w')
    pidfile.write(str(os.getpid()))
    pidfile.close()
    atexit.register(_remove_pidfile, pidfile_path)


def _remove_pidfile(pidfile_path):
    try:
        os.remove(pidfile_path)
    except Exception as e:
        pass
