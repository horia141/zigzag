#!/bin/bash
# Run an instance of the resource serving system.

# Setup the environment variables.
source ./workflow/setup_env.sh

# Start resource serving public visible task.
cd var/photos
python -m SimpleHTTPServer $RES_PORT

# Return to initial position.
cd ../..
