default['thrift']['version'] = '0.9.2'
default['thrift']['mirror'] = 'http://apache.mirror.anlx.net'
default['thrift']['checksum'] = 'cef50d3934c41db5fa7724440cc6f10a732e7a77fe979b98c23ce45725349570'

default['application']['DUMB_project_path'] = '/home/horiacoman/Work/zigzag'

default['application']['user'] = 'zigzag'
default['application']['group'] = 'zigzag'

default['application']['work_dir'] = '/zigzag'
default['application']['config_dir'] = File.join(default['application']['work_dir'], 'config')
default['application']['data_dir'] = File.join(default['application']['work_dir'], 'data')
default['application']['db_backup_dir'] = File.join(default['application']['data_dir'], 'db_backup')
default['application']['photos_dir'] = File.join(default['application']['data_dir'], 'photos')
default['application']['original_photos_dir'] = File.join(default['application']['photos_dir'], 'original')
default['application']['processed_photos_dir'] = File.join(default['application']['photos_dir'], 'processed')
default['application']['sources_dir'] = File.join(default['application']['work_dir'], 'sources')

default['application']['virtual_env'] = File.join(default['application']['sources_dir'], 'env')

default['application']['api_serving']['name'] = 'zigzag_api_serving'
default['application']['api_serving']['user'] = 'zigzag_api_serving'
default['application']['api_serving']['work_dir'] = File.join(default['application']['work_dir'], 'api_serving')
default['application']['api_serving']['config'] = File.join(default['application']['config_dir'], 'api_serving.lighttpd')
default['application']['api_serving']['port'] = 9000
default['application']['api_serving']['daemon']['script'] = '/etc/init.d/zigzag_api_serving'
default['application']['api_serving']['daemon']['pid_file'] = File.join(default['application']['api_serving']['work_dir'], 'daemon.pid')
default['application']['api_serving']['daemon']['error_log'] = File.join(default['application']['api_serving']['work_dir'], 'daemon.error.log')
default['application']['api_serving']['daemon']['debug_log'] = File.join(default['application']['api_serving']['work_dir'], 'daemon.debug.log')
default['application']['api_serving']['lighttpd']['pid_file'] = File.join(default['application']['api_serving']['work_dir'], 'lighttpd.pid')
default['application']['api_serving']['lighttpd']['error_log'] = File.join(default['application']['api_serving']['work_dir'], 'lighttpd.error.log')
default['application']['api_serving']['lighttpd']['access_log'] = File.join(default['application']['api_serving']['work_dir'], 'lighttpd.access.log')
default['application']['api_serving']['django']['fastcgi_host'] = '127.0.0.1'
default['application']['api_serving']['django']['fastcgi_port'] = 9002
default['application']['api_serving']['app']['name'] = 'zigzag_api_serving_app'
default['application']['api_serving']['app']['daemon']['script'] = '/etc/init.d/zigzag_api_serving_app'
default['application']['api_serving']['app']['daemon']['pid_file'] = File.join(default['application']['api_serving']['work_dir'], 'app.daemon.pid')
default['application']['api_serving']['app']['daemon']['error_log'] = File.join(default['application']['api_serving']['work_dir'], 'app.daemon.error.log')
default['application']['api_serving']['app']['daemon']['debug_log'] = File.join(default['application']['api_serving']['work_dir'], 'app.daemon.debug.log')
default['application']['api_serving']['app']['pid_file'] = File.join(default['application']['api_serving']['work_dir'], 'app.pid')
default['application']['api_serving']['app']['output_log'] = File.join(default['application']['api_serving']['work_dir'], 'app.output.log')
default['application']['api_serving']['app']['error_log'] = File.join(default['application']['api_serving']['work_dir'], 'app.error.log')

default['application']['res_serving']['name'] = 'zigzag_res_serving'
default['application']['res_serving']['config'] = File.join(default['application']['config_dir'], 'res_serving.lighttpd')
default['application']['res_serving']['daemon']['script'] = '/etc/init.d/zigzag_res_serving'
default['application']['res_serving']['daemon']['pid_file'] = File.join(default['application']['work_dir'], 'res_serving.daemon.pid')
default['application']['res_serving']['daemon']['error_log'] = File.join(default['application']['work_dir'], 'res_serving.daemon.error.log')
default['application']['res_serving']['daemon']['debug_log'] = File.join(default['application']['work_dir'], 'res_serving.daemon.debug.log')
default['application']['res_serving']['user'] = 'zigzag_res_serving'
default['application']['res_serving']['port'] = 9001
default['application']['res_serving']['lighttpd']['pid_file'] = File.join(default['application']['work_dir'], 'res_serving.lighttpd.pid')
default['application']['res_serving']['lighttpd']['error_log'] = File.join(default['application']['work_dir'], 'res_serving.lighttpd.error.log')
default['application']['res_serving']['lighttpd']['access_log'] = File.join(default['application']['work_dir'], 'res_serving.lighttpd.access.log')
default['application']['res_serving']['allowed_extension'] = 'jpg|mp4'
default['application']['res_serving']['expire'] = 'access plus 1 months'

default['application']['exploring']['user'] = 'zigzag_exploring'
default['application']['exploring']['work_dir'] = File.join(default['application']['work_dir'], 'exploring')
