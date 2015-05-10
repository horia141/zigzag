#!/bin/bash
# Run an instance of the serving side components of the system.

# Setup environment variables.
source ./workflow/setup_env.sh

echo 'Running ZigZag server application!'

# Start the REST API server.
python ./interface_server/manage.py runfcgi method=threaded host=127.0.0.1 port=9002 workdir=`pwd` outlog=./var/interface_server.out.log errlog=./var/interface_server.error.log pidfile=./var/interface_server.pid
INTERFACE_SERVER_PID=`cat ./var/interface_server.pid`

# Kill all tasks if the last one somehow stops.
echo 'Killing all started jobs'
kill $INTERFACE_SERVER_PID $API_SERVER_PID
