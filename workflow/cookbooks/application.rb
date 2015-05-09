directory "var" do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

directory "var/db_backup" do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

directory "var/photos" do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

directory "var/photos/original" do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end

directory "var/photos/processed" do
  owner 'horia'
  group 'horia'
  mode '0755'
  action :create
end
