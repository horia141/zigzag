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

user node.default['application']['redirect_server']['user'] do
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
