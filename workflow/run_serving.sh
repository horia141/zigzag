#!/bin/bash
# Run an instance of the serving side components of the system.

# Setup environment variables.
source ./workflow/setup_env.sh

echo 'Running ZigZag server application!'

# Start resource serving public visible task.
lighttpd -D -f ./config/res_serving.lighttpd &
RES_SERVER_PID=`cat ./var/res_serving.pid`

# If the application exist, all these tasks must be killed as well.
trap "kill $RES_SERVER_PID" EXIT INT

# Start REST API public visible task.
python ./interface_server/manage.py runserver $HOST:$API_PORT &>./var/api_serving.log

# Kill all tasks if the last one somehow stops.
echo 'Killing all started jobs'
kill $RES_SERVER_PID
