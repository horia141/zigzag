# Keep the APT cache up-to-date.
include_recipe 'apt::default'

# Setup the proper packages and source stuff.

package 'daemon'
package 'lighttpd'
include_recipe 'build-essential'
include_recipe 'python'
include_recipe 'thrift'

# Define groups and users used by different components.

group node.default['application']['group'] do
  system true
  members [node.default['application']['user'],
           node.default['application']['api_serving']['user'],
           node.default['application']['res_serving']['user']]
end

user node.default['application']['user'] do
  comment 'Master user for the application'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

user node.default['application']['api_serving']['user'] do
  comment 'User for the API serving component'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

user node.default['application']['res_serving']['user'] do
  comment 'User for the resources serving component'
  group node.default['application']['group']
  shell '/usr/sbin/nologin'
  home node.default['application']['work_dir']
  system true
end

# General firewall configuration.

firewall 'ufw' do
  action :nothing
end

firewall_rule 'ssh' do
  port 22
  protocol :tcp
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
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

directory node.default['application']['api_serving']['work_dir'] do
  owner node.default['application']['api_serving']['user']
  group node.default['application']['group']
  mode '0770'
  action :create
end

python_virtualenv node.default['application']['api_serving']['virtual_env'] do
  owner node.default['application']['api_serving']['user']
  group node.default['application']['group']
  action :create
end

python_pip 'django' do
  virtualenv node.default['application']['api_serving']['virtual_env']
end

python_pip 'flup' do
  virtualenv node.default['application']['api_serving']['virtual_env']
end

python_pip 'pytz' do
  virtualenv node.default['application']['api_serving']['virtual_env']
end

bash 'install_thrift_python' do
  cwd File.join(Chef::Config[:file_cache_path], "thrift-#{node.default['thrift']['version']}", 'lib', 'py')
  code <<-EOF
    (#{node.default['application']['api_serving']['virtual_env']}/bin/python setup.py install)
  EOF
  not_if { FileTest.exists?(File.join(node.default['application']['api_serving']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', "thrift-#{node.default['thrift']['version']}-py2.7-linux-x86_64.egg")) }
end

# TODO(horia141): this is a particular flavor of ugly. It is both low-level and imperative.
# The configuration should be described in a high-level fashion and as a "state", rather than a
# sequence of steps for achieving something. This is neither, and it should be corrected.
execute 'api_serving_sources' do
  command "cp -r #{node.default['application']['DUMB_project_path']}/datastore #{node.default['application']['api_serving']['work_dir']} && " +
          "chown -R #{node.default['application']['api_serving']['user']} #{node.default['application']['api_serving']['work_dir']}/datastore && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['api_serving']['work_dir']}/datastore && " +
          "chmod -R g+w #{node.default['application']['api_serving']['work_dir']}/datastore && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/explorer #{node.default['application']['api_serving']['work_dir']} && " +
          "chown -R #{node.default['application']['api_serving']['user']} #{node.default['application']['api_serving']['work_dir']}/explorer && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['api_serving']['work_dir']}/explorer && " +
          "chmod -R g+w #{node.default['application']['api_serving']['work_dir']}/explorer && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/fetcher #{node.default['application']['api_serving']['work_dir']} && " +
          "chown -R #{node.default['application']['api_serving']['user']} #{node.default['application']['api_serving']['work_dir']}/fetcher && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['api_serving']['work_dir']}/fetcher && " +
          "chmod -R g+w #{node.default['application']['api_serving']['work_dir']}/fetcher && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/gen #{node.default['application']['api_serving']['work_dir']} && " +
          "chown -R #{node.default['application']['api_serving']['user']} #{node.default['application']['api_serving']['work_dir']}/gen && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['api_serving']['work_dir']}/gen && " +
          "chmod -R g+w #{node.default['application']['api_serving']['work_dir']}/gen && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/interface_server #{node.default['application']['api_serving']['work_dir']} && " +
          "chown -R #{node.default['application']['api_serving']['user']} #{node.default['application']['api_serving']['work_dir']}/interface_server && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['api_serving']['work_dir']}/interface_server && " +
          "chmod -R g+w #{node.default['application']['api_serving']['work_dir']}/interface_server && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/log_analyzer #{node.default['application']['api_serving']['work_dir']} && " +
          "chown -R #{node.default['application']['api_serving']['user']} #{node.default['application']['api_serving']['work_dir']}/log_analyzer && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['api_serving']['work_dir']}/log_analyzer && " +
          "chmod -R g+w #{node.default['application']['api_serving']['work_dir']}/log_analyzer && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/photo_save #{node.default['application']['api_serving']['work_dir']} && " +
          "chown -R #{node.default['application']['api_serving']['user']} #{node.default['application']['api_serving']['work_dir']}/photo_save && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['api_serving']['work_dir']}/photo_save && " +
          "chmod -R g+w #{node.default['application']['api_serving']['work_dir']}/photo_save && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/utils #{node.default['application']['api_serving']['work_dir']} && " +
          "chown -R #{node.default['application']['api_serving']['user']} #{node.default['application']['api_serving']['work_dir']}/utils && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['api_serving']['work_dir']}/utils && " +
          "chmod -R g+w #{node.default['application']['api_serving']['work_dir']}/utils && " +

          "/bin/true"
end

template node.default['application']['api_serving']['config'] do
  source 'api_serving.lighttpd.erb'
  owner node.default['application']['api_serving']['user']
  group node.default['application']['group']
  mode '0440'
  verify "lighttpd -t -f %{file}"
end

template node.default['application']['api_serving']['daemon']['script'] do
  source 'api_serving.daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['api_serving']['name'] do
  init_command node.default['application']['api_serving']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start]
  subscribes :restart, "template[#{node.default['application']['api_serving']['daemon']['script']}]", :delayed
  subscribes :restart, "template[#{node.default['application']['api_serving']['config']}]", :delayed
end

firewall_rule node.default['application']['api_serving']['name'] do
  port node.default['application']['api_serving']['port']
  protocol :tcp
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

template node.default['application']['api_serving']['app']['daemon']['script'] do
  source 'api_serving_app.daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['api_serving']['app']['name'] do
  init_command node.default['application']['api_serving']['app']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start]
  subscribes :restart, "template[#{node.default['application']['api_serving']['app']['daemon']['script']}]", :delayed
end

# Setup resources serving.

template node.default['application']['res_serving']['config'] do
  source 'res_serving.lighttpd.erb'
  owner node.default['application']['res_serving']['user']
  group node.default['application']['group']
  mode '0440'
  verify "lighttpd -t -f %{file}"
end

template node.default['application']['res_serving']['daemon']['script'] do
  source 'res_serving.daemon.erb'
  owner 'root'
  group 'root'
  mode '0755'
end

service node.default['application']['res_serving']['name'] do
  init_command node.default['application']['res_serving']['daemon']['script']
  supports :start => true, :stop => true, :restart => true, :status => true
  action [:enable, :start]
  subscribes :restart, "template[#{node.default['application']['res_serving']['daemon']['script']}]", :delayed
  subscribes :restart, "template[#{node.default['application']['res_serving']['config']}]", :delayed
end

firewall_rule node.default['application']['res_serving']['name'] do
  port node.default['application']['res_serving']['port']
  protocol :tcp
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end
