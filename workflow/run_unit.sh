#!/bin/bash

# Setup environment variables.
source ./workflow/setup_env.sh

# Run tests for common.
echo 'Running common.message_queue tests'
python ./common/message_queue_test.py
