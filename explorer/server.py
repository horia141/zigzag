import argparse
import datetime
import logging
import pytz
import random
import time
import urllib2

from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket
from thrift.transport import TTransport

import common.defines.constants as defines
import common.model.ttypes as model
import explorer.analyzers as analyzers
import explorer.analyzers.imgur as imgur
import explorer.analyzers.ninegag as ninegag
import explorer.analyzers.reddit as reddit
import fetcher_pb.Service
import fetcher_pb.ttypes as fetcher_types
import photo_save_pb.Service
import photo_save_pb.ttypes as photo_save_types
import rest_api.models as datastore
import utils.pidfile as pidfile
import utils.rpc as rpc


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--sleep_sec', type=int, required=True,
        help='Number of seconds to sleep between generations')
    parser.add_argument('--fetcher_host', type=str, required=True,
        help='Host on which the fetcher server is listening')
    parser.add_argument('--fetcher_port', type=int, required=True,
        help='Port on which the fetcher server is listening')
    parser.add_argument('--photo_save_host', type=str, required=True,
        help='Host on which the photo_save server is listening')
    parser.add_argument('--photo_save_port', type=int, required=True,
        help='Port on which the photo_save server is listening')
    parser.add_argument('--log_path', type=str, required=True,
        help='Path to the log file')
    parser.add_argument('--pidfile_path', type=str, required=True,
        help='Path for the pidfile')
    args = parser.parse_args()

    pidfile.write_pidfile(args.pidfile_path)

    logging.basicConfig(level=logging.INFO, filename=args.log_path)

    if args.sleep_sec <= 0:
        logging.error('The number of seconds to sleep (sleep_sec) must be positive')
        return

    right_now_0 = datetime.datetime.now(pytz.utc)

    logging.info('Starting crawl and analysis service on %s', right_now_0.strftime(defines.TIME_FORMAT))

    logging.info('Building analyzers')

    all_analyzers = {
        'Reddit': reddit.Analyzer(defines.ARTIFACT_SOURCES[1], args.fetcher_host, args.fetcher_port),
        'Imgur': imgur.Analyzer(defines.ARTIFACT_SOURCES[2], args.fetcher_host, args.fetcher_port),
        '9GAG': ninegag.Analyzer(defines.ARTIFACT_SOURCES[3], args.fetcher_host, args.fetcher_port)
    }

    iter_nr = 1

    while True:
        logging.info('Iteration %d' % iter_nr)

        right_now_1 = datetime.datetime.now(pytz.utc)
    
        logging.info('Analyzing sources')
    
        artifacts = []
    
        for analyzer_name, analyzer in all_analyzers.iteritems():
            logging.info('Analyzing artifact source "%s"', analyzer_name)
    
            try:
                artifact_descs = analyzer.analyze()
            except (analyzers.Error, fetcher_types.IOError) as e:
                logging.error('Could not analyze "%s" - %s', analyzer_name, str(e))
                continue
    
            logging.info('Have %d possibly new artifacts', len(artifact_descs))
    
            for artifact_desc in artifact_descs:
                if datastore.artifact_exists_by_page_uri(artifact_desc['page_uri']):
                    logging.info('Found already existing artifact "%s"', artifact_desc['page_uri'])
                    continue
    
                logging.info('Fetching photos for artifact')
                photo_descriptions = []
    
                try:
                    for image_raw_description in artifact_desc['photo_description']:
                        logging.info('Fetching photo "%s"' % image_raw_description['uri_path'])
                        source_uri = image_raw_description['uri_path']
                        
                        try:
                            with rpc.to(photo_save_pb.Service, args.photo_save_host, args.photo_save_port) as photo_save_client:
                                photo_data = photo_save_client.process_one_photo(source_uri)
                        except photo_save_types.PhotoAlreadyExists as e:
                            logging.error('Photo "%s" already exists in the photo database' % image_raw_description['uri_path'])
                            continue

                        photo_description = model.PhotoDescription()
                        if not image_raw_description['subtitle'].isspace():
                            photo_description.subtitle = image_raw_description['subtitle']
                        if not image_raw_description['description'].isspace():
                            photo_description.description = image_raw_description['description']
                        photo_description.source_uri = source_uri
                        photo_description.photo_data = photo_data
                        photo_descriptions.append(photo_description)
                except (photo_save_types.Error, Exception) as e:
                    logging.error('Encountered an error in processing artifact "%s"', artifact_desc['title'])
                    logging.error(str(e))
                    continue

                if len(photo_descriptions) <= defines.PHOTO_DEDUP_KEEP_SIZE_FACTOR * len(artifact_desc['photo_description']):
                    logging.error('Not enough new photos in artifact to keep it')
                    continue

                logging.info('Saving artifact "%s" to database', artifact_desc['title'])
                artifact = model.Artifact(artifact_desc['page_uri'], artifact_desc['title'].encode('utf-8'),
                                          analyzer.source.id, photo_descriptions)
                artifacts.append(artifact)
    
                logging.info('Finished processing for "%s"', artifact.title)
    
            logging.info('Finished processing for "%s"', analyzer_name)
    
        logging.info('Shuffling the list of artifacts')
        random.shuffle(artifacts)
    
        right_now_2 = datetime.datetime.now(pytz.utc)
    
        datetime_started_ts = long(time.mktime(right_now_1.timetuple()))
        datetime_ended_ts = long(time.mktime(right_now_2.timetuple()))
    
        for idx in range(0, len(artifacts), defines.MAX_ARTIFACTS_PER_GENERATION):
            logging.info('Saving artifacts %d to %d' % (idx, idx+defines.MAX_ARTIFACTS_PER_GENERATION))
            artifacts_chunk = artifacts[idx:idx+defines.MAX_ARTIFACTS_PER_GENERATION]
    
            generation = model.Generation(-1, datetime_started_ts, datetime_ended_ts,
                defines.ARTIFACT_SOURCES, defines.IMAGE_SCREEN_CONFIG,
                defines.VIDEO_SCREEN_CONFIG, artifacts_chunk)
            # Datastore operations might need to be wrapped in a transaction, so we should be certain that
            # both the generation and all the artifacts could properly be marked as added. If an error 
            # occurs in the latter stage, we might end up with duplicate artifacts, because not all
            # artifacts in generation might properly be marked.
            # TODO(horia141): use a transaction here or something else to mark a properly closed generation.
            datastore.save_generation(generation)
            for artifact in artifacts:
                datastore.mark_artifact_as_existing(generation, artifact)
    
        logging.info('Closing generation')

        right_now_3 = datetime.datetime.now(pytz.utc)
        duration = right_now_3 - right_now_1
        sleep_time_sec = max(10, args.sleep_sec - duration.total_seconds())

        logging.info('Took %d seconds. Going to sleep for %d seconds' % (duration.total_seconds(), sleep_time_sec))

        time.sleep(sleep_time_sec)

        logging.info('Waking up')
        iter_nr = iter_nr + 1


if __name__ == '__main__':
    main()
