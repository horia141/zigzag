# Keep the APT cache up-to-date.
include_recipe 'apt::default'

# Setup the proper packages.
package 'daemon'
package 'lighttpd'

# Define groups and users used by different components.
group node.default['application']['group']

user node.default['application']['user'] do
  comment 'Master user for the application'
  group node.default['application']['group']
  shell '/bin/bash'
end

user node.default['application']['api_serving']['user'] do
  comment 'User for the API serving component'
  group node.default['application']['group']
  shell '/bin/bash'
end

user node.default['application']['res_serving']['user'] do
  comment 'User for the resources serving component'
  group node.default['application']['group']
  shell '/bin/bash'
end

# Define directory structure for the runtime data.
directory node.default['application']['work_dir'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

directory node.default['application']['db_backup_dir'] do
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

# Setup serving.

# Setup API serving.

# Setup resources serving.

template node.default['application']['res_serving']['config'] do
  source 'res_serving.lighttpd.erb'
  owner node.default['application']['res_serving']['user']
  group node.default['application']['group']
  mode '0440'
  verify "lighttpd -t -f %{file}"
end

template node.default['application']['res_serving']['daemon_script'] do
  source 'res_serving.daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['res_serving']['name'] do
  init_command node.default['application']['res_serving']['daemon_script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start]
  subscribes :restart, "template[#{node.default['application']['res_serving']['daemon_script']}]", :delayed
  subscribes :restart, "template[#{node.default['application']['res_serving']['config']}]", :delayed
end
