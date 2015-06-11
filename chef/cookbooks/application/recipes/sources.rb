# TODO(horia141): this is a particular flavor of ugly. It is both low-level and imperative.
# The configuration should be described in a high-level fashion and as a "state", rather than a
# sequence of steps for achieving something. This is neither, and it should be corrected.
execute 'sources' do
  command "cp -r #{node.default['application']['DUMB_project_path']}/datastore #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/datastore && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/datastore && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/datastore && " +

          "cp -r #{node.default['application']['DUMB_project_path']}/explorer #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/explorer && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/explorer && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/explorer && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/fetcher #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/fetcher && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/fetcher && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/fetcher && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/gen #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/gen && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/gen && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/gen && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/interface_server #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/interface_server && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/interface_server && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/interface_server && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/log_analyzer #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/log_analyzer && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/log_analyzer && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/log_analyzer && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/photo_save #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/photo_save && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/photo_save && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/photo_save && " +
  
          "cp -r #{node.default['application']['DUMB_project_path']}/utils #{node.default['application']['sources_dir']} && " +
          "chown -R #{node.default['application']['user']} #{node.default['application']['sources_dir']}/utils && " + 
          "chgrp -R #{node.default['application']['group']} #{node.default['application']['sources_dir']}/utils && " +
          "chmod -R g+w #{node.default['application']['sources_dir']}/utils && " +
  
          "/bin/true"
end
