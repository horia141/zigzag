import re
import os
import logging
from datetime import datetime

class ResourceServingLog(object):
	def __init__(self, log_path, resources_path):
		self._log_path = log_path
		self._resources_path = resources_path

	def _load_content(self):
		f = open(self._log_path, 'r')
		return f.read()

	def _requested_resources_for_today(self, content):
		matches = re.findall(r'\d+[.]\d+[.]\d+[.]\d+ - - [[](\d\d/\w+/\d\d\d\d) \d\d:\d\d:\d\d[]] ["]GET /(.+) HTTP/1.1["] 200 -', content)

		requested_resources = []
		today = datetime.today()
		for date, filename in matches:
			parsedDate = datetime.strptime(date, '%d/%b/%Y')
			if parsedDate.day != today.day or parsedDate.month != today.month or parsedDate.year != today.year:
				continue
			else:
				requested_resources.append(filename)
		
		return requested_resources
		
	def size_of_requested_resources_for_today(self):
		content = self._load_content()
		requested_resources = self._requested_resources_for_today(content)
		size = 0

		for resource in requested_resources:
			resource_path = os.path.join(self._resources_path, resource)
			try:
				size += os.stat(resource_path).st_size
			except OSError, e:
				logging.error('Resource %s not found' % resource_path)

		return (datetime.today().strftime('%d/%m/%Y %H:%M'), size)

def main():
	servingLog = ResourceServingLog("../var/res_serving.log", "../var/photos")
	print servingLog.size_of_requested_resources_for_today()


if __name__ == '__main__':
    main()