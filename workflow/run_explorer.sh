#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the crawling server.
python ./explorer/server.py
