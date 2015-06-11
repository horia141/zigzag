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

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as comlink_transport

import common.defines.constants as defines
import common.model.ttypes as model
import explorer.analyzers as analyzers
import explorer.analyzers.imgur as imgur
import explorer.analyzers.ninegag as ninegag
import explorer.analyzers.reddit as reddit
import fetcher.Service
import photo_save
import rest_api.models as datastore
import utils.pidfile as pidfile


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--sleep_sec', type=int, required=True,
        help='Number of seconds to sleep between generations')
    parser.add_argument('--fetcher_host', type=str, required=True,
        help='Host on which the fetcher server is listening')
    parser.add_argument('--fetcher_port', type=int, required=True,
        help='Port on which the fetcher server is listening')
    parser.add_argument('--photo_save_port', type=int, required=True,
        help='Port on which the photo_save Comlink server is listening')
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

    ser = serializer.Serializer()
    client = comlink_transport.Client(args.photo_save_port, ser)
    photo_save_client = photo_save.Service.client(client)

    transport = TSocket.TSocket(args.fetcher_host, args.fetcher_port)
    transport = TTransport.TBufferedTransport(transport)
    protocol = TBinaryProtocol.TBinaryProtocol(transport)
    fetcher_client = fetcher.Service.Client(protocol)
    transport.open()

    logging.info('Starting crawl and analysis service on %s', right_now_0.strftime(defines.TIME_FORMAT))

    logging.info('Building analyzers')

    all_analyzers = {
        'Reddit': reddit.Analyzer(defines.ARTIFACT_SOURCES[1], fetcher_client),
        'Imgur': imgur.Analyzer(defines.ARTIFACT_SOURCES[2], fetcher_client),
        '9GAG': ninegag.Analyzer(defines.ARTIFACT_SOURCES[3], fetcher_client)
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
    try:
        main()
    except Exception as e:
        if comlink.is_remote_exception(e):
            logging.error(comlink.format_stacktrace_for_remote_exception(e))
        else:
            logging.exception(e)
        raise
