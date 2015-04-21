import argparse
import datetime
import logging
import os
import re

import utils.pidfile as pidfile


class ServingLog(object):
    def __init__(self, log_path):
        self._log_path = log_path
            
    def size_of_requested_resources_for_today(self):
        today = datetime.date.today()
        log_file = open(self._log_path, 'r')

        lines_seen = 0
        lines_processed = 0
        total_bytes_sent = 0

        for line in log_file:
            lines_seen += 1
            match = re.match(r'.*[[](.+) [+]\d+[]] ["]GET /.+ HTTP/\d[.]\d["] 200 (\d+)', line)

            if match is None:
                continue

            try:
                line_date = datetime.datetime.strptime(match.group(1), '%d/%b/%Y:%H:%M:%S')
                bytes_sent = int(match.group(2), 10)
            except ValueError as e:
                logging.error('Could not parse data from line "%s"', line[0:40])
                continue

            if line_date.date() != today:
                logging.info('Here')
                continue

            total_bytes_sent += bytes_sent
            lines_processed += 1

        log_file.close()

        return total_bytes_sent


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--api_serving_log_path', type=str, required=True,
        help='Path to the API serving log')
    parser.add_argument('--res_serving_log_path', type=str, required=True,
        help='Path to the res serving log')
    parser.add_argument('--log_path', type=str, required=True,
        help='Path to the log file')
    parser.add_argument('--pidfile_path', type=str, required=True,
        help='Path for the pidfile')
    args = parser.parse_args()

    pidfile.write_pidfile(args.pidfile_path)

    logging.basicConfig(level=logging.INFO, filename=args.log_path)

    api_serving_log = ServingLog(args.api_serving_log_path)
    print api_serving_log.size_of_requested_resources_for_today()
    res_serving_log = ServingLog(args.res_serving_log_path)
    print res_serving_log.size_of_requested_resources_for_today()


if __name__ == '__main__':
    main()
