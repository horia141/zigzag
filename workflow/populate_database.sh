#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run populate_database.py
python ./datastore/populate_database.py
