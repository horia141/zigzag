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
  action [:enable, :start]
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

bash 'database_create_database' do
  code <<-EOH
    (sleep 1)
    (#{PSQL_CMD} --command='create database #{node.default['application']['database_name']} owner #{node.default['application']['database']['user']};')
  EOH
  user node.default['application']['database']['user']
  group node.default['application']['group']
  not_if "sleep 1 && #{PSQL_CMD} --command='\\list' | grep #{node.default['application']['database_name']}"
end

bash 'build_and_sync_db' do
  cwd "#{node.default['application']['sources_dir']}/interface_server"
  code <<-EOH
    (sleep 1)
    (touch #{node.default['application']['db_path']})
    (#{node.default['application']['virtual_env']}/bin/python manage.py migrate)
    (chmod g+w #{node.default['application']['db_path']})
  EOH
  environment node.default['application']['python_env']
  user node.default['application']['user']
  group node.default['application']['group']
  subscribes :run, "template[#{node.default['application']['api_server']['app']['config']}]", :immediately
end
