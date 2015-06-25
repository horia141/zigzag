python_virtualenv node.default['application']['virtual_env'] do
  owner node.default['application']['user']
  group node.default['application']['group']
  action :create
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'django' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'flup' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'pytz' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'pillow' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'beautifulsoup4' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'gunicorn' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'psycopg2' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): depends too much on where and how the egg file is written.
bash 'install_thrift_python' do
  cwd File.join(Chef::Config[:file_cache_path], "thrift-#{node.default['thrift']['version']}", 'lib', 'py')
  code <<-EOF
    (#{node.default['application']['virtual_env']}/bin/python setup.py install)
    (chown -R #{node.default['application']['user']} #{File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', "thrift-#{node.default['thrift']['version']}-py2.7-linux-x86_64.egg")})
    (chgrp -R #{node.default['application']['group']} #{File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', "thrift-#{node.default['thrift']['version']}-py2.7-linux-x86_64.egg")})
  EOF
  not_if { FileTest.exists?(File.join(node.default['application']['virtual_env'], 'local', 'lib', 'python2.7', 'site-packages', "thrift-#{node.default['thrift']['version']}-py2.7-linux-x86_64.egg")) }
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'imgurpython' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'praw' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'numpy' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end

# TODO(horia141): figure out how to write this as the appropriate user and group.
python_pip 'scipy' do
  virtualenv node.default['application']['virtual_env']
  options "--cache-dir #{node.default['application']['pip_cache']}"
end
