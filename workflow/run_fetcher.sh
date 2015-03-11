#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the fetcher.
python ./fetcher/__init__.py
