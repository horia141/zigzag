#!/bin/bash
# Run an instance of the server side components of the system.

# Setup environment variables.
source ./workflow/setup_env.sh

echo 'Running ZigZag server application!'

# Start resource serving public visible task.
cd var/photos
python -m SimpleHTTPServer $RES_PORT &
RES_SERVER_PID="$!"
cd ../..

# If the application exist, all these tasks must be killed as well.
trap "kill $RES_SERVER_PID" EXIT INT

# Start REST API public visible task.
python ./interface_server/manage.py runserver $HOST:$API_PORT

# Kill all tasks if the last one somehow stops.
echo 'Killing all started jobs'
kill $RES_SERVER_PID
