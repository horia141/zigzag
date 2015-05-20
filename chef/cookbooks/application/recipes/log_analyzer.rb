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
