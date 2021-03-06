# Setup the proper system level packages and source stuff.

include_recipe 'apt'
include_recipe 'build-essential'
include_recipe 'firewall'
include_recipe 'ruby'
include_recipe 'python'
include_recipe 'thrift'
include_recipe 'iptables'

package 'daemon'
package 'lighttpd'
package 'sqlite'
package 'libjpeg-dev'
package 'libjpeg8-dev' # Only on Ubuntu 14.04 >=
package 'imagemagick'
package 'libav-tools'
package 'libpq-dev'
package 'libreadline6'
package 'libreadline6-dev'
package 'python-numpy'
package 'python-scipy'

# Disable the standard lighttpd daemon. We have no use for it.
service 'lighttpd' do
  init_command '/etc/init.d/lighttpd'
  action :disable
  provider Chef::Provider::Service::Init::Debian
end

# Install PostgreSQL from sources.

remote_file "#{Chef::Config[:file_cache_path]}/postgresql-#{node.default['application']['postgresql']['version']}.tar.gz" do
  source node.default['application']['postgresql']['remote_path']
  checksum node.default['application']['postgresql']['checksum']
end

bash 'install_postgresql' do
  cwd Chef::Config[:file_cache_path]
  code <<-EOH
    (tar -zxvf postgresql-#{node.default['application']['postgresql']['version']}.tar.gz)
    (cd postgresql-#{node.default['application']['postgresql']['version']} && ./configure)
    (cd postgresql-#{node.default['application']['postgresql']['version']} && make)
    (cd postgresql-#{node.default['application']['postgresql']['version']} && make install)
  EOH
  not_if { FileTest.exists?(node.default['application']['postgresql']['postgres_path']) }
end
