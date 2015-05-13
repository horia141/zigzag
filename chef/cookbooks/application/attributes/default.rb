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
default['application']['var_dir'] = File.join(default['application']['work_dir'], 'var')
default['application']['tmp_dir'] = File.join(default['application']['work_dir'], 'tmp')

default['application']['db_path'] = File.join(default['application']['var_dir'], 'db.sqlite3')

default['application']['virtual_env'] = File.join(default['application']['sources_dir'], 'env')
default['application']['egg_cache'] = File.join(default['application']['tmp_dir'], 'egg-cache')
default['application']['pip_cache'] = File.join(default['application']['tmp_dir'], 'pip-cache')

default['application']['api_serving']['name'] = 'zigzag_api_serving'
default['application']['api_serving']['user'] = 'zigzag_api_serving'
default['application']['api_serving']['frontend']['name'] = 'zigzag_api_serving_frontend'
default['application']['api_serving']['frontend']['config'] = File.join(default['application']['config_dir'], 'api_serving.frontend')
default['application']['api_serving']['frontend']['port'] = 9000
default['application']['api_serving']['frontend']['pid_file'] = File.join(default['application']['var_dir'], 'api_serving.frontend.pid')
default['application']['api_serving']['frontend']['error_log'] = File.join(default['application']['var_dir'], 'api_serving.frontend.error.log')
default['application']['api_serving']['frontend']['access_log'] = File.join(default['application']['var_dir'], 'api_serving.frontend.access.log')
default['application']['api_serving']['frontend']['daemon']['script'] = '/etc/init.d/zigzag_api_serving_frontend'
default['application']['api_serving']['frontend']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'api_serving.frontend_daemon.pid')
default['application']['api_serving']['frontend']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'api_serving.frontend_daemon.error.log')
default['application']['api_serving']['frontend']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'api_serving.frontend_daemon.debug.log')
default['application']['api_serving']['app']['name'] = 'zigzag_api_serving_app'
default['application']['api_serving']['app']['config'] = File.join(default['application']['sources_dir'], 'interface_server', 'interface_server', 'settings.py')
default['application']['api_serving']['app']['fastcgi_host'] = '127.0.0.1'
default['application']['api_serving']['app']['fastcgi_port'] = 9002
default['application']['api_serving']['app']['pid_file'] = File.join(default['application']['var_dir'], 'api_serving.app.pid')
default['application']['api_serving']['app']['output_log'] = File.join(default['application']['var_dir'], 'api_serving.app.output.log')
default['application']['api_serving']['app']['error_log'] = File.join(default['application']['var_dir'], 'api_serving.app.error.log')
default['application']['api_serving']['app']['daemon']['script'] = '/etc/init.d/zigzag_api_serving_app'
default['application']['api_serving']['app']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'api_serving.app_daemon.pid')
default['application']['api_serving']['app']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'api_serving.app_daemon.error.log')
default['application']['api_serving']['app']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'api_serving.app_daemon.debug.log')

default['application']['res_serving']['name'] = 'zigzag_res_serving'
default['application']['res_serving']['user'] = 'zigzag_res_serving'
default['application']['res_serving']['port'] = 9001
default['application']['res_serving']['allowed_extension'] = 'jpg|mp4'
default['application']['res_serving']['expire'] = 'access plus 1 months'
default['application']['res_serving']['config'] = File.join(default['application']['config_dir'], 'res_serving')
default['application']['res_serving']['pid_file'] = File.join(default['application']['var_dir'], 'res_serving.pid')
default['application']['res_serving']['error_log'] = File.join(default['application']['var_dir'], 'res_serving.error.log')
default['application']['res_serving']['access_log'] = File.join(default['application']['var_dir'], 'res_serving.access.log')
default['application']['res_serving']['daemon']['script'] = '/etc/init.d/zigzag_res_serving'
default['application']['res_serving']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'res_serving_daemon.pid')
default['application']['res_serving']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'res_serving_daemon.error.log')
default['application']['res_serving']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'res_serving_daemon.debug.log')

default['application']['exploring']['name'] = 'zigzag_exploring'
default['application']['exploring']['user'] = 'zigzag_exploring'
default['application']['exploring']['fetcher']['name'] = 'zigzag_exploring_fetcher'
default['application']['exploring']['fetcher']['port'] = 16000
default['application']['exploring']['fetcher']['pid_file'] = File.join(default['application']['var_dir'], 'exploring.fetcher.pid')
default['application']['exploring']['fetcher']['log'] = File.join(default['application']['var_dir'], 'exploring.fetcher.log')
default['application']['exploring']['fetcher']['daemon']['script'] = '/etc/init.d/zigzag_exploring_fetcher'
default['application']['exploring']['fetcher']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'exploring.fetcher_daemon.pid')
default['application']['exploring']['fetcher']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'exploring.fetcher_daemon.error.log')
default['application']['exploring']['fetcher']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'exploring.fetcher_daemon.debug.log')
default['application']['exploring']['photo_save']['name'] = 'zigzag_exploring_photo_save'
default['application']['exploring']['photo_save']['port'] = 16100
default['application']['exploring']['photo_save']['pid_file'] = File.join(default['application']['var_dir'], 'exploring.photo_save.pid')
default['application']['exploring']['photo_save']['log'] = File.join(default['application']['var_dir'], 'exploring.photo_save.log')
default['application']['exploring']['photo_save']['daemon']['script'] = '/etc/init.d/zigzag_exploring_photo_save'
default['application']['exploring']['photo_save']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'exploring.photo_save_daemon.pid')
default['application']['exploring']['photo_save']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'exploring.photo_save_daemon.error.log')
default['application']['exploring']['photo_save']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'exploring.photo_save_daemon.debug.log')

default['application']['git']['comlink']['repo'] = 'git@github.com:horia141/comlink.git'
default['application']['git']['comlink']['branch'] = 'master'

default['application']['python_env']['PYTHONPATH'] = "#{default['application']['sources_dir']}:#{default['application']['sources_dir']}/interface_server:#{node.default['application']['sources_dir']}/gen/py"
default['application']['python_env']['DJANGO_SETTINGS_MODULE'] = 'interface_server.settings'
default['application']['python_env']['PYTHON_EGG_CACHE'] = default['application']['egg_cache']
