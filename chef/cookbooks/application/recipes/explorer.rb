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
