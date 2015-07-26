firewall 'ufw' do
  action :nothing
end

firewall_rule 'ssh-in' do
  port 22
  protocol :tcp
  direction :in
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

firewall_rule 'http-in' do
  port 80
  protocol :tcp
  direction :in
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

iptables_rule 'iptables_redirect80' do
  action :enable
end

firewall_rule 'http-out' do
  port 80
  protocol :tcp
  direction :out
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

firewall_rule 'https-in' do
  port 443
  protocol :tcp
  direction :in
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end

firewall_rule 'https-out' do
  port 443
  protocol :tcp
  direction :out
  action :allow
  notifies :enable, 'firewall[ufw]', :delayed
end
