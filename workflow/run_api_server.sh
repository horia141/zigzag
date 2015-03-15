#!/bin/bash
# Run an instance of the API server.

# Setup environment variables.
source ./workflow/setup_env.sh

# Start REST API public visible task.
python ./interface_server/manage.py runserver $HOST:$API_PORT
