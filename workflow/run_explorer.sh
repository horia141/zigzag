#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the crawling service. This will run forever, while the others are in the
# background.
python ./explorer/server.py
