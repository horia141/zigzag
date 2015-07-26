template node.default['application']['redirect_server']['frontend']['config'] do
  source 'redirect_server.frontend.erb'
  owner node.default['application']['redirect_server']['user']
  group node.default['application']['group']
  mode '0440'
  verify "lighttpd -t -f %{file}"
end

template node.default['application']['redirect_server']['frontend']['daemon']['script'] do
  source 'redirect_server.frontend_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['redirect_server']['frontend']['name'] do
  init_command node.default['application']['redirect_server']['frontend']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end

template node.default['application']['redirect_server']['app']['gunicorn_config'] do
  source 'redirect_server.app_gunicorn.erb'
  owner node.default['application']['redirect_server']['user']
  group node.default['application']['group']
  mode '0440'
end

link node.default['application']['redirect_server']['app']['gunicorn_config_ln'] do
  owner node.default['application']['user'] # Owned by application.user, like all sources
  group node.default['application']['group']
  mode '0440'
  to node.default['application']['redirect_server']['app']['gunicorn_config']
end

template node.default['application']['redirect_server']['app']['django_config'] do
  source 'redirect_server.app_django.erb'
  owner node.default['application']['user'] # Owned by application.user, like all sources
  group node.default['application']['group']
  mode '0440'
end

template node.default['application']['redirect_server']['app']['daemon']['script'] do
  source 'redirect_server.app_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['redirect_server']['app']['name'] do
  init_command node.default['application']['redirect_server']['app']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
  subscribes :restart, "bash[build_and_sync_db]", :delayed
end
