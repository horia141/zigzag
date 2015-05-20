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
