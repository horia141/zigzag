#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run the migration script.
python ./datastore/migrate.py
