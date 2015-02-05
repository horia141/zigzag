"""Populate the database with artifact sources and their information."""

import datetime
import logging
import pytz

import common.flow_annotation as flow_annotation
import rest_api.models as models


def _define_artifact_source(start_page_url, name, right_now):
    """Add an artifact source, only if it does not exist.

    Don't complain if it does not exist, though.

    Params:
      start_page_url: the URL of the start page of the artifact source. This must be unique across
         the system.
      name: the name of the artifact source.
      right_now: the time the artifact source was added.
    """

    try:
        artifact_source = models.ArtifactSource.add(start_page_url, name, right_now)
        logging.info('Added %s', name)
    except models.Error as e:
        logging.info('Did not add %s since it already existed', name)

@flow_annotation.populate_database
def main():
    """Application main function."""

    logging.basicConfig(level=logging.INFO)

    right_now = datetime.datetime.now(pytz.utc)

    _define_artifact_source('http://reddit.com', 'Reddit', right_now)
    _define_artifact_source('http://imgur.com', 'Imgur', right_now)


if __name__ == '__main__':
    main()
