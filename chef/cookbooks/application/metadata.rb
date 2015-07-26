name             'application'
maintainer       'ZigZag'
maintainer_email 'horia141@gmail.com'
license          'All rights reserved'
description      'Installs/Configures the ZigZag application'
long_description ''
version          '0.1.0'

depends 'apt', '~> 2.7.0'
depends 'firewall', '~> 1.1.1'
depends 'ruby', '~> 0.9.3'
depends 'python', '~> 1.4.6'
depends 'thrift', '~> 1.2.0'
depends 'iptables', '~> 1.0.0'

recipe 'application', 'Sets up the whole application'
recipe 'application::packages', 'Installs core packages used across the app'
recipe 'application::firewall', 'Defines global rules for it'
recipe 'application::users', 'Defines the users for the application'
recipe 'application::work_dirs', 'Defines the directory structure for the application'
recipe 'application::python_env', 'Constructs virtual env and packaged required for application custom services'
recipe 'application::sources', 'Custom services sources'
recipe 'application::database', 'Sets up the database'
recipe 'application::frontend', 'Sets up the frontend system'
recipe 'application::api_server', 'Sets up the API serving system'
recipe 'application::redirect_server', 'Sets up the redirect serving system'
recipe 'application::res_server', 'Sets up the resources serving system'
recipe 'application::explorer', 'Sets up the explorer service and its components'
recipe 'application::log_analyzer', 'Sets up the log analyzer service'
