#!/usr/bin/env python
"""Script with scaffold code for migrating between different database versions.

Modify and apply when the database changes and while a single such script can still perform.
"""

import datetime
import pytz
import sqlite3


NEW_DATABASE_NAME = 'var/db.sqlite3'
OLD_DATABASE_NAME = 'var/db_backup/db.sqlite3'


def main():
    """Application main function."""

    new_db_connection = sqlite3.connect(NEW_DATABASE_NAME)
    old_db_connection = sqlite3.connect(OLD_DATABASE_NAME)

    new_db_cursor = new_db_connection.cursor()
    old_db_cursor = old_db_connection.cursor()

    # Migrate Generations
    for (id, time_added, time_closed) in \
        old_db_cursor.execute('select * from rest_api_generation'):
        new_db_cursor.execute(
            'insert into rest_api_generation values (:id, :status, :time_added, :time_closed)',
            {'id': id, 'status': 2, 'time_added': time_added, 'time_closed': time_closed})

    # Migrate ArtifactSources
    for (id, start_page_url, name, time_added) in \
        old_db_cursor.execute('select * from rest_api_artifactsource'):
        new_db_cursor.execute(
            'insert into rest_api_artifactsource values (:id, :start_page_url, :name, :time_added)',
            {'id': id, 'start_page_url': start_page_url, 'name': name, 'time_added': time_added})

    # Migrate Artifacts
    for (id, page_url, generation_id, artifact_source_id, title, image_url_paths) in \
        old_db_cursor.execute('select * from rest_api_artifact'):
        new_db_cursor.execute(
            'insert into rest_api_artifact values (:id, :page_url, :generation_id, ' +
            ':artifact_source_id, :title, :image_url_paths)',
            {'id': id, 'page_url': page_url, 'generation_id': generation_id, 'artifact_source_id': 
             artifact_source_id, 'title': title, 'image_url_paths': image_url_paths})

    new_db_connection.commit()

    old_db_connection.close()
    new_db_connection.close()


if __name__ == '__main__':
    main()
