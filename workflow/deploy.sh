#!/bin/bash
# Deploy all files necessary to run the server side components of the system to an actual
# production machine. 

echo 'Deploying ZigZag server application!'

export HOST=horia141.com
export USER=horia141

# Deploying the local version of the system to production currently consists of the following
# conceptual stages:
#   1. Make a backup of the already existing production database.
#   2. Copy local sources to the production machine.
#   3. Configure production machine.

# Make a backup of the old database. Because it gets overwritten.
# Super safe designs ftw.
# ssh $USER@$HOST <<'EOF'
#  cd zigzag
#  mkdir -p var/db_backup
#  if [ -f var/db_backup/last_index ]; then
#    last_index=`cat var/db_backup/last_index`
#    next_index=$((last_index + 1))
#  else
#    next_index=0
#  fi
#  echo $next_index > var/db_backup/last_index
#  cp var/db.sqlite3 var/db_backup/db.sqlite3.`date '+%Y-%m-%d'`.$next_index
# EOF

# Cleanup pyc files. These cause issues when they're copied to the server.
tree -fi | grep pyc | xargs rm

# Mass copy other task elements.
scp -r common datastore explorer fetcher interface_server photo_save config workflow $USER@$HOST://home/$USER/zigzag

# Change setup_env.sh file to work with the remote host.
ssh $USER@$HOST <<EOF
  sed -i 's/localhost/horia141.com/g' zigzag/workflow/setup_env.sh
EOF

# Create a place to store photos.
ssh $USER@$HOST <<EOF
  mkdir -p zigzag/var/photos
EOF

# Configure cron on the production machine.
ssh $USER@$HOST <<'EOF'
  sed -i 's|/home/horia/Dropbox/Work/ZigZag|/home/horia141/zigzag|g' zigzag/config/crontab
  sed -i 's|/home/horia/Dropbox/Work/ZigZag|/home/horia141/zigzag|g' zigzag/workflow/run_exploring_in_cron.sh
  crontab zigzag/config/crontab
EOF
