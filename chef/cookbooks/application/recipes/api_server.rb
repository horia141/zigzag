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
