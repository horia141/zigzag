default['application']['user'] = 'zigzag'
default['application']['group'] = 'zigzag'
default['application']['work_dir'] = '/zigzag'
default['application']['db_backup_dir'] = File.join(default['application']['work_dir'], 'db_backup')
default['application']['config_dir'] = File.join(default['application']['work_dir'], 'config')
default['application']['photos_dir'] = File.join(default['application']['work_dir'], 'photos')
default['application']['original_photos_dir'] = File.join(default['application']['photos_dir'], 'original')
default['application']['processed_photos_dir'] = File.join(default['application']['photos_dir'], 'processed')

default['application']['api_serving']['user'] = 'zigzag_api_serving'

default['application']['res_serving']['name'] = 'zigzag_res_serving'
default['application']['res_serving']['config'] = File.join(default['application']['config_dir'], 'res_serving.lighttpd')
default['application']['res_serving']['daemon_script'] = '/etc/init.d/zigzag_res_serving'
default['application']['res_serving']['daemon_pid_file'] = File.join(default['application']['work_dir'], 'res_serving.daemon.pid')
default['application']['res_serving']['daemon_error_log'] = File.join(default['application']['work_dir'], 'res_serving.daemon.error.log')
default['application']['res_serving']['daemon_debug_log'] = File.join(default['application']['work_dir'], 'res_serving.daemon.debug.log')
default['application']['res_serving']['user'] = 'zigzag_res_serving'
default['application']['res_serving']['port'] = 9001
default['application']['res_serving']['pid_file'] = File.join(default['application']['work_dir'], 'res_serving.lighttpd.pid')
default['application']['res_serving']['error_log'] = File.join(default['application']['work_dir'], 'res_serving.lighttpd.error.log')
default['application']['res_serving']['access_log'] = File.join(default['application']['work_dir'], 'res_serving.lighttpd.access.log')
default['application']['res_serving']['allowed_extension'] = 'jpg|mp4'
default['application']['res_serving']['expire'] = 'access plus 1 months'
