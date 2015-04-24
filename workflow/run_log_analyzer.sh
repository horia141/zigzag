#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the log analysis system.
python ./log_analyzer/server.py --api_serving_log_path=./var/api_serving.access.log --res_serving_log_path=./var/res_serving.access.log --log_path=./var/log_analyzer.log --pidfile_path=./var/log_analyzer.pid
