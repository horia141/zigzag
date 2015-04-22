import argparse
import datetime
import logging
import os
import re
import time

import rest_api.models as models
import log_analyzer.protos.ttypes as log_analyzer_protos
import utils.pidfile as pidfile


class ServingLog(object):
    def __init__(self, log_path):
        self._log_path = log_path
            
    def size_of_requested_resources_for_today(self):
        today = datetime.date.today()
        log_file = open(self._log_path, 'r')

        total_bytes_sent_for_month = 0
        total_bytes_sent_for_day = 0

        lines_seen = 0
        lines_processed_for_month = 0
        lines_processed_for_day = 0

        for line in log_file:
            lines_seen += 1
            match = re.match(r'.*[[](.+) [+]\d+[]] ["]GET /.+ HTTP/\d[.]\d["] 200 (\d+)', line)

            if match is None:
                continue

            try:
                line_date = datetime.datetime.strptime(match.group(1), '%d/%b/%Y:%H:%M:%S').date()
                bytes_sent = int(match.group(2), 10)
            except ValueError as e:
                logging.error('Could not parse data from line "%s"', line[0:40])
                continue

            if line_date.year == today.year and line_date.month == today.month:
                total_bytes_sent_for_month += bytes_sent
                lines_processed_for_month += 1

            if line_date == today:
                total_bytes_sent_for_day += bytes_sent
                lines_processed_for_day += 1

        log_file.close()

        logging.info('Processed total/month/day: %d/%d/%d lines', lines_seen,
            lines_processed_for_month, lines_processed_for_day)

        return (total_bytes_sent_for_month, total_bytes_sent_for_day)


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
    api_serving_total_bytes_for_month, api_serving_total_bytes_for_day = \
        api_serving_log.size_of_requested_resources_for_today()
    res_serving_log = ServingLog(args.res_serving_log_path)
    res_serving_total_bytes_for_month, res_serving_total_bytes_for_day = \
        res_serving_log.size_of_requested_resources_for_today()

    datetime_ran_ts = long(time.mktime(datetime.datetime.now().timetuple()))
    month_consumption = log_analyzer_protos.SystemConsumption(
        api_serving_total_bytes_for_month + res_serving_total_bytes_for_month,
        api_serving_total_bytes_for_month, res_serving_total_bytes_for_month)
    day_consumption = log_analyzer_protos.SystemConsumption(
        api_serving_total_bytes_for_day + res_serving_total_bytes_for_day,
        api_serving_total_bytes_for_day, res_serving_total_bytes_for_day)
    analysis_result = log_analyzer_protos.AnalysisResult(-1, datetime_ran_ts,
        month_consumption, day_consumption)

    models.save_analysis_result(analysis_result)


if __name__ == '__main__':
    main()
