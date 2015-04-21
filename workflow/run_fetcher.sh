#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the fetcher.
python ./fetcher/__init__.py --port=16000 --log_path=./var/fetcher.log --pidfile_path=./var/fetcher.pid
