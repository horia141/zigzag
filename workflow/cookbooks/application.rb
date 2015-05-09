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
directory 'var' do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

directory 'var/db_backup' do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

directory 'var/photos' do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

directory 'var/photos/original' do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

directory 'var/photos/processed' do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

# Setup serving.

# Setup API serving.

# Setup resources serving.
