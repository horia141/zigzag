#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the fetcher service.
python ./fetcher/__init__.py &
FETCHER_PID="$!"

# Run the photo save service.
python ./photo_save/__init__.py &
PHOTO_SAVE_PID="$!"

# If the application exist, all these tasks must be killed as well.
trap "kill $FETCHER_PID $PHOTO_SAVE_PID" EXIT INT

# Run the crawling service. This will run forever, while the others are in the
# background.
python ./explorer/server.py

# Kill all tasks if the last one somehow stops.
echo 'Killing all started jobs'
kill $FETCHER_PID
kill $PHOTO_SAVE_PID