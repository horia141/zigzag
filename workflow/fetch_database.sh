#!/bin/bash
# Fetch the production database from the deployment server.

export HOST=horia141.com
export USER=horia141

scp $USER@$HOST://home/$USER/zigzag/var/db.sqlite3 .
