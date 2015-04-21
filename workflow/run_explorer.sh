#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the crawling service. This will run forever, while the others are in the
# background.
python ./explorer/server.py --fetcher_port=16000 --photo_save_port=16001 --log_path=./var/explorer.log --pidfile_path=./var/explorer.pid
