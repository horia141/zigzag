#!/bin/bash
# Run an instance of the server side components of the system.

# Setup environment variables.
source ./workflow/setup_env.sh

echo 'Running ZigZag server application!'

# Start REST API public visible task.
python ./interface_server/manage.py runserver $HOST:$API_PORT
