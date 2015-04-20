import datetime
import logging
import pytz
import random
import time
import urllib2

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport
from thrift.protocol import TJSONProtocol
from thrift.transport import TTransport

import common.defines.constants as defines
import common.model.ttypes as model
import explorer.analyzers as analyzers
import explorer.analyzers.imgur as imgur
import explorer.analyzers.ninegag as ninegag
import explorer.analyzers.reddit as reddit
import photo_save
import rest_api.models as datastore


def main():
    logging.basicConfig(level=logging.INFO, filename=defines.EXPLORER_LOG_PATH)

    right_now_1 = datetime.datetime.now(pytz.utc)

    ser = serializer.Serializer()
    client = transport.Client(defines.PHOTO_SAVE_PORT, ser)
    photo_save_client = photo_save.Service.client(client)

    logging.info('Starting crawl and analysis service on %s', right_now_1.strftime(defines.TIME_FORMAT))

    logging.info('Building analyzers')

    all_analyzers = {
        'Reddit': reddit.Analyzer(defines.ARTIFACT_SOURCES[1]),
        'Imgur': imgur.Analyzer(defines.ARTIFACT_SOURCES[2]),
        '9GAG': ninegag.Analyzer(defines.ARTIFACT_SOURCES[3])
    }

    logging.info('Analyzing sources')

    artifacts = []

    for analyzer_name, analyzer in all_analyzers.iteritems():
        logging.info('Analyzing artifact source "%s"', analyzer_name)

        try:
            artifact_descs = analyzer.analyze()
        except analyzers.Error as e:
            logging.warn('Could not analyze "%s" - %s', analyzer_name, str(e))
            continue

        logging.info('Have %d possibly new artifacts', len(artifact_descs))

        for artifact_desc in artifact_descs:
            if datastore.artifact_exists_by_page_uri(artifact_desc['page_uri']):
                logging.info('Found already existing artifact "%s"', artifact_desc['page_uri'])
                continue

            photo_description = []

            try:
                for image_raw_description in artifact_desc['photo_description']:
                    subtitle = image_raw_description['subtitle']
                    description = image_raw_description['description']
                    source_uri = image_raw_description['uri_path']
                    photo_description.append(photo_save_client.process_one_photo(
                        subtitle, description, source_uri))
            except Exception as e:
                logging.error('Encountered an error in processing artifact "%s"', artifact_desc['title'])
                if comlink.is_remote_exception(e):
                    logging.error(comlink.format_stacktrace_for_remote_exception(e))
                else:
                    logging.exception(e)
                continue

            logging.info('Saving artifact "%s" to database', artifact_desc['title'])
            artifact = model.Artifact(artifact_desc['page_uri'], artifact_desc['title'].encode('utf-8'),
                analyzer.source.id, photo_description)
            artifacts.append(artifact)

            logging.info('Finished processing for "%s"', artifact.title)

        logging.info('Finished processing for "%s"', analyzer_name)

    logging.info('Shuffling the list of artifacts')
    random.shuffle(artifacts)

    right_now_2 = datetime.datetime.now(pytz.utc)

    datetime_started_ts = long(time.mktime(right_now_1.timetuple()))
    datetime_ended_ts = long(time.mktime(right_now_2.timetuple()))
    screen_configs = defines.IMAGE_SCREEN_CONFIG.copy()
    screen_configs.update(defines.VIDEO_SCREEN_CONFIG)

    for idx in range(0, len(artifacts), defines.MAX_ARTIFACTS_PER_GENERATION):
        logging.info('Saving artifacts %d to %d' % (idx, idx+defines.MAX_ARTIFACTS_PER_GENERATION))
        artifacts_chunk = artifacts[idx:idx+defines.MAX_ARTIFACTS_PER_GENERATION]

        generation = model.Generation(-1, datetime_started_ts, datetime_ended_ts,
            defines.ARTIFACT_SOURCES, screen_configs, artifacts_chunk)
        # Datastore operations might need to be wrapped in a transaction, so we should be certain that
        # both the generation and all the artifacts could properly be marked as added. If an error 
        # occurs in the latter stage, we might end up with duplicate artifacts, because not all
        # artifacts in generation might properly be marked.
        # TODO(horia141): use a transaction here or something else to mark a properly closed generation.
        datastore.save_generation(generation)
        for artifact in artifacts:
            datastore.mark_artifact_as_existing(generation, artifact)

    logging.info('Closing generation')


if __name__ == '__main__':
    try:
        main()
    except Exception as e:
        if comlink.is_remote_exception(e):
            logging.error(comlink.format_stacktrace_for_remote_exception(e))
        else:
            logging.exception(e)
        raise
