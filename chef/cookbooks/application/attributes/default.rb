default['application']['DUMB_project_path'] = '/home/picjardev/zigzag'

default['application']['user'] = 'zigzag'
default['application']['group'] = 'zigzag'

default['application']['work_dir'] = '/zigzag'
default['application']['config_dir'] = File.join(default['application']['work_dir'], 'config')
default['application']['data_dir'] = File.join(default['application']['work_dir'], 'data')
default['application']['database_dir'] = File.join(default['application']['data_dir'], 'database')
default['application']['photos_dir'] = File.join(default['application']['data_dir'], 'photos')
default['application']['original_photos_dir'] = File.join(default['application']['photos_dir'], 'original')
default['application']['processed_photos_dir'] = File.join(default['application']['photos_dir'], 'processed')
default['application']['sources_dir'] = File.join(default['application']['work_dir'], 'sources')
default['application']['var_dir'] = File.join(default['application']['work_dir'], 'var')
default['application']['tmp_dir'] = File.join(default['application']['work_dir'], 'tmp')

default['application']['database_name'] = 'zigzag_main'

default['application']['database']['name'] = 'zigzag_database'
default['application']['database']['user'] = 'zigzag_database'
default['application']['database']['config'] = File.join(default['application']['config_dir'], 'database')
default['application']['database']['hba_config'] = File.join(default['application']['config_dir'], 'database_hba')
default['application']['database']['ident_config'] = File.join(default['application']['config_dir'], 'database_ident')
default['application']['database']['host'] = '127.0.0.1'
default['application']['database']['port'] = 8000
default['application']['database']['pid_file'] = File.join(default['application']['var_dir'], 'database.pid')
default['application']['database']['output_log'] = 'database.output.%Y%m%d.log'
default['application']['database']['error_log'] = File.join(default['application']['var_dir'], 'database.error.log')
default['application']['database']['daemon']['script'] = '/etc/init.d/zigzag_database'
default['application']['database']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'database_daemon.pid')
default['application']['database']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'database_daemon.error.log')
default['application']['database']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'database_daemon.debug.log')

default['application']['api_server']['name'] = 'zigzag_api_server'
default['application']['api_server']['user'] = 'zigzag_api_server'
default['application']['api_server']['frontend']['name'] = 'zigzag_api_server_frontend'
default['application']['api_server']['frontend']['config'] = File.join(default['application']['config_dir'], 'api_server.frontend')
default['application']['api_server']['frontend']['port'] = 9000
default['application']['api_server']['frontend']['pid_file'] = File.join(default['application']['var_dir'], 'api_server.frontend.pid')
default['application']['api_server']['frontend']['error_log'] = File.join(default['application']['var_dir'], 'api_server.frontend.error.log')
default['application']['api_server']['frontend']['access_log'] = File.join(default['application']['var_dir'], 'api_server.frontend.access.log')
default['application']['api_server']['frontend']['daemon']['script'] = '/etc/init.d/zigzag_api_server_frontend'
default['application']['api_server']['frontend']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'api_server.frontend_daemon.pid')
default['application']['api_server']['frontend']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'api_server.frontend_daemon.error.log')
default['application']['api_server']['frontend']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'api_server.frontend_daemon.debug.log')
default['application']['api_server']['app']['name'] = 'zigzag_api_server_app'
default['application']['api_server']['app']['wsgi_module'] = 'interface_server.wsgi'
default['application']['api_server']['app']['django_config'] = File.join(default['application']['sources_dir'], 'interface_server', 'interface_server', 'settings.py')
default['application']['api_server']['app']['gunicorn_work_dir'] = File.join(default['application']['sources_dir'], 'interface_server')
default['application']['api_server']['app']['gunicorn_config_ln'] = File.join(default['application']['sources_dir'], 'interface_server', 'gunicorn.py')
default['application']['api_server']['app']['gunicorn_config'] = File.join(default['application']['config_dir'], 'api_server.app_gunicorn.py')
default['application']['api_server']['app']['fastcgi_host'] = '127.0.0.1'
default['application']['api_server']['app']['fastcgi_port'] = 9002
default['application']['api_server']['app']['pid_file'] = File.join(default['application']['var_dir'], 'api_server.app.pid')
default['application']['api_server']['app']['access_log'] = File.join(default['application']['var_dir'], 'api_server.app.access.log')
default['application']['api_server']['app']['error_log'] = File.join(default['application']['var_dir'], 'api_server.app.error.log')
default['application']['api_server']['app']['daemon']['script'] = '/etc/init.d/zigzag_api_server_app'
default['application']['api_server']['app']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'api_server.app_daemon.pid')
default['application']['api_server']['app']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'api_server.app_daemon.error.log')
default['application']['api_server']['app']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'api_server.app_daemon.debug.log')

default['application']['res_server']['name'] = 'zigzag_res_server'
default['application']['res_server']['user'] = 'zigzag_res_server'
default['application']['res_server']['port'] = 9001
default['application']['res_server']['allowed_extension'] = 'jpg|mp4'
default['application']['res_server']['expire'] = 'access plus 1 months'
default['application']['res_server']['config'] = File.join(default['application']['config_dir'], 'res_server')
default['application']['res_server']['pid_file'] = File.join(default['application']['var_dir'], 'res_server.pid')
default['application']['res_server']['error_log'] = File.join(default['application']['var_dir'], 'res_server.error.log')
default['application']['res_server']['access_log'] = File.join(default['application']['var_dir'], 'res_server.access.log')
default['application']['res_server']['daemon']['script'] = '/etc/init.d/zigzag_res_server'
default['application']['res_server']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'res_server_daemon.pid')
default['application']['res_server']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'res_server_daemon.error.log')
default['application']['res_server']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'res_server_daemon.debug.log')

default['application']['explorer']['name'] = 'zigzag_explorer'
default['application']['explorer']['user'] = 'zigzag_explorer'
default['application']['explorer']['fetcher']['name'] = 'zigzag_explorer_fetcher'
default['application']['explorer']['fetcher']['host'] = '127.0.0.1'
default['application']['explorer']['fetcher']['port'] = 16000
default['application']['explorer']['fetcher']['pid_file'] = File.join(default['application']['var_dir'], 'explorer.fetcher.pid')
default['application']['explorer']['fetcher']['log'] = File.join(default['application']['var_dir'], 'explorer.fetcher.log')
default['application']['explorer']['fetcher']['daemon']['script'] = '/etc/init.d/zigzag_explorer_fetcher'
default['application']['explorer']['fetcher']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'explorer.fetcher_daemon.pid')
default['application']['explorer']['fetcher']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'explorer.fetcher_daemon.error.log')
default['application']['explorer']['fetcher']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'explorer.fetcher_daemon.debug.log')
default['application']['explorer']['photo_save']['name'] = 'zigzag_explorer_photo_save'
default['application']['explorer']['photo_save']['host'] = '127.0.0.1'
default['application']['explorer']['photo_save']['port'] = 16100
default['application']['explorer']['photo_save']['pid_file'] = File.join(default['application']['var_dir'], 'explorer.photo_save.pid')
default['application']['explorer']['photo_save']['log'] = File.join(default['application']['var_dir'], 'explorer.photo_save.log')
default['application']['explorer']['photo_save']['daemon']['script'] = '/etc/init.d/zigzag_explorer_photo_save'
default['application']['explorer']['photo_save']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'explorer.photo_save_daemon.pid')
default['application']['explorer']['photo_save']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'explorer.photo_save_daemon.error.log')
default['application']['explorer']['photo_save']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'explorer.photo_save_daemon.debug.log')
default['application']['explorer']['explorer']['name'] = 'zigzag_explorer_explorer'
default['application']['explorer']['explorer']['sleep_sec'] = 30 * 60
default['application']['explorer']['explorer']['pid_file'] = File.join(default['application']['var_dir'], 'explorer.explorer.pid')
default['application']['explorer']['explorer']['log'] = File.join(default['application']['var_dir'], 'explorer.explorer.log')
default['application']['explorer']['explorer']['daemon']['script'] = '/etc/init.d/zigzag_explorer_explorer'
default['application']['explorer']['explorer']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'explorer.explorer_daemon.pid')
default['application']['explorer']['explorer']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'explorer.explorer_daemon.error.log')
default['application']['explorer']['explorer']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'explorer.explorer_daemon.debug.log')

default['application']['log_analyzer']['name'] = 'zigzag_log_analyzer'
default['application']['log_analyzer']['user'] = 'zigzag_log_analyzer'
default['application']['log_analyzer']['sleep_sec'] = 60 * 60
default['application']['log_analyzer']['pid_file'] = File.join(default['application']['var_dir'], 'log_analyzer.pid')
default['application']['log_analyzer']['log'] = File.join(default['application']['var_dir'], 'log_analyzer.log')
default['application']['log_analyzer']['daemon']['script'] = '/etc/init.d/zigzag_log_analyzer'
default['application']['log_analyzer']['daemon']['pid_file'] = File.join(default['application']['var_dir'], 'log_analyzer_daemon.pid')
default['application']['log_analyzer']['daemon']['error_log'] = File.join(default['application']['var_dir'], 'log_analyzer_daemon.error.log')
default['application']['log_analyzer']['daemon']['debug_log'] = File.join(default['application']['var_dir'], 'log_analyzer_daemon.debug.log')

default['application']['virtual_env'] = File.join(default['application']['sources_dir'], 'env')
default['application']['egg_cache'] = File.join(default['application']['tmp_dir'], 'egg-cache')
default['application']['pip_cache'] = File.join(default['application']['tmp_dir'], 'pip-cache')

default['thrift']['version'] = '0.9.2'
default['thrift']['mirror'] = 'http://apache.mirror.anlx.net'
default['thrift']['checksum'] = 'cef50d3934c41db5fa7724440cc6f10a732e7a77fe979b98c23ce45725349570'

default['application']['postgresql']['version'] = '9.4.1'
default['application']['postgresql']['remote_path'] = "https://ftp.postgresql.org/pub/source/v9.4.1/postgresql-#{default['application']['postgresql']['version']}.tar.gz"
default['application']['postgresql']['checksum'] = '43da2d54ff8333a2ee595efc3d0df3255bb46d4431841d2b88f9cfcd6366e234'
default['application']['postgresql']['initdb_path'] = '/usr/local/pgsql/bin/initdb'
default['application']['postgresql']['postgres_path'] = '/usr/local/pgsql/bin/postgres'
default['application']['postgresql']['psql_path'] = '/usr/local/pgsql/bin/psql'

default['application']['python_env']['PYTHONPATH'] = "#{default['application']['sources_dir']}:#{default['application']['sources_dir']}/interface_server:#{node.default['application']['sources_dir']}/gen/py"
default['application']['python_env']['PYTHONPATH_GUNICORN'] = "#{default['application']['sources_dir']}:#{node.default['application']['sources_dir']}/gen/py"
default['application']['python_env']['DJANGO_SETTINGS_MODULE'] = 'interface_server.settings'
default['application']['python_env']['PYTHON_EGG_CACHE'] = default['application']['egg_cache']

default['application']['runtime']['debug'] = 'True'

