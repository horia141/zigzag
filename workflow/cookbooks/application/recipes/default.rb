# Keep the APT cache up-to-date.
include_recipe 'apt::default'

# Define groups and users used by different components.
group 'zigzag'

user 'zigzag-api-serving' do
  comment 'User for the API serving component'
  group 'zigzag'
  shell '/bin/bash'
end

user 'zigzag-res-serving' do
  comment 'User for the resources serving component'
  group 'zigzag'
  shell '/bin/bash'
end

# Define directory structure for the runtime data.
directory File.join(node.default['application']['work_dir'], 'var') do
  owner 'horia'
  group 'zigzag'
  mode '0755'
  action :create
end

directory File.join(node.default['application']['work_dir'], 'var/db_backup') do
  owner 'horia'
  group 'zigzag'
  mode '0755'
  action :create
end

directory File.join(node.default['application']['work_dir'], 'var/photos') do
  owner 'horia'
  group 'zigzag'
  mode '0755'
  action :create
end

directory File.join(node.default['application']['work_dir'], 'var/photos/original') do
  owner 'horia'
  group 'zigzag'
  mode '0755'
  action :create
end

directory File.join(node.default['application']['work_dir'], 'var/photos/processed') do
  owner 'horia'
  group 'zigzag'
  mode '0755'
  action :create
end

# Setup serving.

# Setup API serving.

# Setup resources serving.
