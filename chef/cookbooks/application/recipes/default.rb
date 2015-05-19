# === Setup the proper system level packages and source stuff. ===

include_recipe 'apt'
include_recipe 'build-essential'
include_recipe 'firewall'
include_recipe 'python'
include_recipe 'thrift'

package 'daemon'
package 'lighttpd'
package 'sqlite'
package 'libjpeg-dev'
package 'libjpeg8-dev' # Only on Ubuntu 14.04 >=
package 'imagemagick'
package 'libav-tools'
package 'libpq-dev'
package 'libreadline6'
package 'libreadline6-dev'

# Install PostgreSQL from sources.

remote_file "#{Chef::Config[:file_cache_path]}/postgresql-#{node.default['application']['postgresql']['version']}.tar.gz" do
  source node.default['application']['postgresql']['remote_path']
  checksum node.default['application']['postgresql']['checksum']
end

bash 'install_postgresql' do
  cwd Chef::Config[:file_cache_path]
  code <<-EOH
    (tar -zxvf postgresql-#{node.default['application']['postgresql']['version']}.tar.gz)
    (cd postgresql-#{node.default['application']['postgresql']['version']} && ./configure)
    (cd postgresql-#{node.default['application']['postgresql']['version']} && make)
    (cd postgresql-#{node.default['application']['postgresql']['version']} && make install)
  EOH
  not_if { FileTest.exists?(node.default['application']['postgresql']['postgres_path']) }
end

# === System level firewall configuration. ===

firewall 'ufw' do
  action :nothing
end

firewall_rule 'ssh-in' do
  port 22
  protocol :tcp
  direction :in
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

firewall_rule 'http-out' do
  port 80
  protocol :tcp
  direction :out
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

firewall_rule 'https-out' do
  port 443
  protocol :tcp
  direction :out
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

# === Define groups and users used by different components. ===

group node.default['application']['group'] do
  system true
end

user node.default['application']['user'] do
  comment 'Master user for the application'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

user node.default['application']['database']['user'] do
  comment 'User for the PostgreSQL database component'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

user node.default['application']['api_server']['user'] do
  comment 'User for the API server component'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

user node.default['application']['res_server']['user'] do
  comment 'User for the resources server component'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

user node.default['application']['explorer']['user'] do
  comment 'User for the explorer component'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

user node.default['application']['log_analyzer']['user'] do
  comment 'User for the log_analyzer component'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

# Defining the group is split into two because we want to explicitly specify the members, and we
# cannot do that if they are not yet defined.
# group node.default['application']['group'] do
#   system true
#   members [node.default['application']['user'],
#            node.default['application']['api_server']['user'],
#            node.default['application']['res_server']['user'],
#            node.default['application']['explorer']['user'],
#            node.default['application']['log_analyzer']['user']]
# end

# === Define directory structure for the runtime data. ===

directory node.default['application']['work_dir'] do
  owner node.default['application']['user']

  group node.default['application']['group']
  mode '0770'
  action :create
end

directory node.default['application']['config_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

directory node.default['application']['data_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

# Must be owned by the database user rather than by the master user and must have user-only read
# and write access.
directory node.default['application']['database_dir'] do
  owner node.default['application']['database']['user']
  group node.default['application']['group']
  mode '0700'
  action :create
end

directory node.default['application']['photos_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

directory node.default['application']['original_photos_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

directory node.default['application']['processed_photos_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

directory node.default['application']['sources_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

directory node.default['application']['var_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

directory node.default['application']['tmp_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

# === Setup server side components sources. ===

python_virtualenv node.default['application']['virtual_env'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  action :create
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'django' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'flup' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'pytz' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'pillow' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'beautifulsoup4' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'gunicorn' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'psycopg2' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): depends too much on where and how the egg file is written.
bash 'install_thrift_python' do
  cwd File.join(Chef::Config[:file_cache_path], "thrift-#{node.default['thrift']['version']}", 'lib', 'py')
  code <<-EOF
    (#{node.default['application']['virtual_env']}/bin/python setup.py install)
    (chown -R #{node.default['application']['user']} #{File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', "thrift-#{node.default['thrift']['version']}-py2.7-linux-x86_64.egg")})
    (chgrp -R #{node.default['application']['group']} #{File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', "thrift-#{node.default['thrift']['version']}-py2.7-linux-x86_64.egg")})
  EOF
  not_if { FileTest.exists?(File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', "thrift-#{node.default['thrift']['version']}-py2.7-linux-x86_64.egg")) }
end

# TODO(horia141): this is a particular flavor of ugly. It is both low-level and imperative.
# The configuration should be described in a high-level fashion and as a "state", rather than a
# sequence of steps for achieving something. This is neither, and it should be corrected.
execute 'sources' do
  command "cp -r #{node.default['application']['DUMB_project_path']}/datastore #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/datastore && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/datastore && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/datastore && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/explorer #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/explorer && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/explorer && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/explorer && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/fetcher #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/fetcher && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/fetcher && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/fetcher && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/gen #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/gen && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/gen && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/gen && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/interface_server #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/interface_server && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/interface_server && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/interface_server && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/log_analyzer #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/log_analyzer && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/log_analyzer && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/log_analyzer && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/photo_save #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/photo_save && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/photo_save && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/photo_save && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/utils #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/utils && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/utils && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/utils && " +
  
          "/bin/true"
end

file "#{Chef::Config[:file_cache_path]}/git_ssh_wrapper.sh" do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0755'
  content "#!/bin/sh\nexec /usr/bin/ssh -i /home/horiacoman/.ssh/id_rsa -o StrictHostKeyChecking=no -o PubkeyAuthentication=yes -o PasswordAuthentication=no \"$@\""
end

# TODO(horia141): this should not rely on the built in keys. but rather on protected keys in the data bag.
# TODO(horia141): factor out the repository name.
git "#{Chef::Config[:file_cache_path]}/comlink" do
  repository node.default['application']['git']['comlink']['repo']
  reference node.default['application']['git']['comlink']['branch']
  action :sync
  notifies :run, 'bash[install_comlink]', :delayed
  # user node.default['application']['user']
  # group node.default['application']['group']
  ssh_wrapper "#{Chef::Config[:file_cache_path]}/git_ssh_wrapper.sh"
end

bash 'install_comlink' do
  cwd "#{Chef::Config[:file_cache_path]}/comlink"
  code <<-EOH
    (#{node.default['application']['virtual_env']}/bin/python setup.py install)
    (chown -R #{node.default['application']['user']} #{File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', 'comlink-0.1-py2.7.egg')})
    (chgrp -R #{node.default['application']['group']} #{File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', 'comlink-0.1-py2.7.egg')})
  EOH

  not_if { FileTest.exists?(File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', 'comlink-0.1-py2.7.egg')) }
end

# === Build or update the master database ===

bash 'build_and_sync_db' do
  cwd "#{node.default['application']['sources_dir']}/interface_server"
  code <<-EOH
    (touch #{node.default['application']['db_path']})
    (#{node.default['application']['virtual_env']}/bin/python manage.py migrate)
    (chmod g+w #{node.default['application']['db_path']})
  EOH
  environment node.default['application']['python_env']
  user node.default['application']['user']
  group node.default['application']['group']
  action :nothing
  subscribes :run, "template[#{node.default['application']['api_server']['app']['config']}]", :delayed
end

# === Setup database service. ===

bash 'database_initdb' do
  code <<-EOH
    (#{node.default['application']['postgresql']['initdb_path']} -D #{node.default['application']['database_dir']} -U #{node.default['application']['user']})
  EOH
  user node.default['application']['database']['user']
  group node.default['application']['group']
  creates "#{node.default['application']['database_dir']}/postgresql.auto.conf"
end

# Remove undeeded configuration files.
file "#{node.default['application']['database_dir']}/postgresql.conf" do
  action :delete
end

file "#{node.default['application']['database_dir']}/pg_hba.conf" do
  action :delete
end

file "#{node.default['application']['database_dir']}/pg_ident.conf" do
  action :delete
end

template node.default['application']['database']['config'] do
  source 'database.erb'
  owner node.default['application']['database']['user']
  group node.default['application']['group']
  mode '0400'
end

template node.default['application']['database']['hba_config'] do
  source 'database_hba.erb'
  owner node.default['application']['database']['user']
  group node.default['application']['group']
  mode '0400'
end

template node.default['application']['database']['ident_config'] do
  source 'database_ident.erb'
  owner node.default['application']['database']['user']
  group node.default['application']['group']
  mode '0400'
end

template node.default['application']['database']['daemon']['script'] do
  source 'database_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['database']['name'] do
  init_command node.default['application']['database']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end

PSQL_CMD = "#{node.default['application']['postgresql']['psql_path']} --host=#{node.default['application']['database']['host']} --port=#{node.default['application']['database']['port']} --username=#{node.default['application']['user']} -d postgres"

# TODO(horia141): Hackish way of going about this. Also, the test if for the user existing, not for
# it having the desired roles.
bash "database_create_role_#{node.default['application']['database']['user']}" do
  code <<-EOH
    (sleep 1)
    (#{PSQL_CMD} --command='create user #{node.default['application']['database']['user']} login createdb createrole;')
  EOH
  user node.default['application']['database']['user']
  group node.default['application']['group']
  not_if "sleep 1 && #{PSQL_CMD} --command='\\du' | grep #{node.default['application']['database']['user']}"
end

# TODO(horia141): Hackish way of going about this. Also, the test if for the user existing, not for
# it having the desired roles.
bash "database_create_role_#{node.default['application']['api_server']['user']}" do
  code <<-EOH
    (sleep 1)
    (#{PSQL_CMD} --command='create user #{node.default['application']['api_server']['user']} login;')
  EOH
  user node.default['application']['database']['user']
  group node.default['application']['group']
  not_if "sleep 1 && #{PSQL_CMD} --command='\\du' | grep #{node.default['application']['api_server']['user']}"
end

# TODO(horia141): Hackish way of going about this. Also, the test if for the user existing, not for
# it having the desired roles.
bash "database_create_role_#{node.default['application']['res_server']['user']}" do
  code <<-EOH
    (sleep 1)
    (#{PSQL_CMD} --command='create user #{node.default['application']['res_server']['user']} login;')
  EOH
  user node.default['application']['database']['user']
  group node.default['application']['group']
  not_if "sleep 1 && #{PSQL_CMD} --command='\\du' | grep #{node.default['application']['res_server']['user']}"
end

# TODO(horia141): Hackish way of going about this. Also, the test if for the user existing, not for
# it having the desired roles.
bash "database_create_role_#{node.default['application']['explorer']['user']}" do
  code <<-EOH
    (sleep 1)
    (#{PSQL_CMD} --command='create user #{node.default['application']['explorer']['user']} login;')
  EOH
  user node.default['application']['database']['user']
  group node.default['application']['group']
  not_if "sleep 1 && #{PSQL_CMD} --command='\\du' | grep #{node.default['application']['explorer']['user']}"
end

# TODO(horia141): Hackish way of going about this. Also, the test if for the user existing, not for
# it having the desired roles.
bash "database_create_role_#{node.default['application']['log_analyzer']['user']}" do
  code <<-EOH
    (sleep 1)
    (#{PSQL_CMD} --command='create user #{node.default['application']['log_analyzer']['user']} login;')
  EOH
  user node.default['application']['database']['user']
  group node.default['application']['group']
  not_if "sleep 1 && #{PSQL_CMD} --command='\\du' | grep #{node.default['application']['log_analyzer']['user']}"
end

# === Setup serving. ===

# Setup API server.

template node.default['application']['api_server']['frontend']['config'] do
  source 'api_server.frontend.erb'
  owner node.default['application']['api_server']['user']
  group node.default['application']['group']
  mode '0440'
  verify "lighttpd -t -f %{file}"
end

template node.default['application']['api_server']['frontend']['daemon']['script'] do
  source 'api_server.frontend_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['api_server']['frontend']['name'] do
  init_command node.default['application']['api_server']['frontend']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end

firewall_rule node.default['application']['api_server']['frontend']['name'] do
  port node.default['application']['api_server']['frontend']['port']
  protocol :tcp
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

template node.default['application']['api_server']['app']['config'] do
  source 'api_server.app.erb'
  owner node.default['application']['user'] # Owned by application.user, like all sources
  group node.default['application']['group']
  mode '0440'
end

template node.default['application']['api_server']['app']['daemon']['script'] do
  source 'api_server.app_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['api_server']['app']['name'] do
  init_command node.default['application']['api_server']['app']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
  subscribes :restart, "bash[build_and_sync_db]", :delayed
end

# === Setup resources server. ===

template node.default['application']['res_server']['config'] do
  source 'res_server.erb'
  owner node.default['application']['res_server']['user']
  group node.default['application']['group']
  mode '0440'
  verify "lighttpd -t -f %{file}"
end

template node.default['application']['res_server']['daemon']['script'] do
  source 'res_server_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['res_server']['name'] do
  init_command node.default['application']['res_server']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end

firewall_rule node.default['application']['res_server']['name'] do
  port node.default['application']['res_server']['port']
  protocol :tcp
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

# === Setup explorer. ===

# Setup fetcher service.

template node.default['application']['explorer']['fetcher']['daemon']['script'] do
  source 'explorer.fetcher_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['explorer']['fetcher']['name'] do
  init_command node.default['application']['explorer']['fetcher']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end

# Setup photo_save service.

template node.default['application']['explorer']['photo_save']['daemon']['script'] do
  source 'explorer.photo_save_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['explorer']['photo_save']['name'] do
  init_command node.default['application']['explorer']['photo_save']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end

# Setup explorer service.

template node.default['application']['explorer']['explorer']['daemon']['script'] do
  source 'explorer.explorer_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['explorer']['explorer']['name'] do
  init_command node.default['application']['explorer']['explorer']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end

# === Setup log analyzer. ===

template node.default['application']['log_analyzer']['daemon']['script'] do
  source 'log_analyzer_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['log_analyzer']['name'] do
  init_command node.default['application']['log_analyzer']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end
