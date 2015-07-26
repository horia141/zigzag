template node.default['application']['frontend']['config'] do
  source 'frontend.erb'
  owner node.default['application']['frontend']['user']
  group node.default['application']['group']
  mode '0440'
  verify "lighttpd -t -f %{file}"
end

template node.default['application']['frontend']['daemon']['script'] do
  source 'frontend_daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['frontend']['name'] do
  init_command node.default['application']['frontend']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start, :restart]
  provider Chef::Provider::Service::Init::Debian
end

firewall_rule node.default['application']['frontend']['name'] do
  port node.default['application']['frontend']['port']
  protocol :tcp
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end
