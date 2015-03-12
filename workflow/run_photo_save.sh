#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the photo save service.
python ./photo_save/__init__.py
