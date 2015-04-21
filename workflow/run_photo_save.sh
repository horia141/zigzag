#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the photo save service.
python ./photo_save/__init__.py --port=16001 --fetcher_port=16000 --photos_dir=./var/photos --log_path=./var/photo_save.log --pidfile_path=./var/photo_save.pid
