import datetime
import pytz
import logging
import urllib2

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport

import common.defines.constants as defines
import explorer.analyzers as analyzers
import explorer.analyzers.reddit as reddit
import explorer.analyzers.imgur as imgur
import photo_save
import rest_api.models as models


def main():
    logging.basicConfig(level=logging.INFO, filename=defines.EXPLORER_LOG_PATH)

    right_now = datetime.datetime.now(pytz.utc)

    ser = serializer.Serializer()
    client = transport.Client(defines.PHOTO_SAVE_PORT, ser)
    photo_save_client = photo_save.Service.client(client)

    logging.info('Starting crawl and analysis service on %s', right_now.strftime(defines.TIME_FORMAT))

    logging.info('Building analyzers')

    all_analyzers = {
        'Reddit': reddit.Analyzer(defines.ARTIFACT_SOURCES['Reddit']),
        'Imgur': imgur.Analyzer(defines.ARTIFACT_SOURCES['Imgur']),
    }

    logging.info('Creating new generation')

    generation = models.Generation.add(right_now)

    logging.info('Analyzing sources')

    for analyzer_name, analyzer in all_analyzers.iteritems():
        logging.info('Analyzing artifact source "%s"', analyzer_name)

        try:
            artifact_descs = analyzer.analyze()
        except analyzers.Error as e:
            logging.warn('Could not analyze "%s" - %s', analyzer_name, str(e))
            continue

        logging.info('Have %d possibly new artifacts', len(artifact_descs))

        for artifact_desc in artifact_descs:
            try:
                artifact = models.Artifact.get_by_page_url(artifact_desc['page_url'])
                logging.info('Found already existing artifact "%s"', artifact.title)
                continue
            except models.Error as e:
                pass

            images_description = []

            try:
                for image_raw_description in artifact_desc['images_description']:
                    subtitle = image_raw_description['subtitle']
                    description = image_raw_description['description']
                    source_uri = image_raw_description['uri_path']
                    images_description.append(photo_save_client.process_one_photo(
                        subtitle, description, source_uri))
            except Exception as e:
                logging.error('Encountered an error in processing artifact "%s"', artifact_desc['title'])
                if comlink.is_remote_exception(e):
                    logging.error(comlink.format_stacktrace_for_remote_exception(e))
                else:
                    logging.exception(e)
                continue

            logging.info('Saving artifact "%s" to database', artifact_desc['title'])
            artifact = models.Artifact.add(artifact_desc['page_url'], 
                generation, models.ArtifactSource.objects.get(id=analyzer.source.id), artifact_desc['title'],
                images_description)

            logging.info('Finished processing for "%s"', artifact.title)

        logging.info('Finished processing for "%s"', analyzer_name)

    right_now_2 = datetime.datetime.now(pytz.utc)

    logging.info('Closing generation')
    generation.close(right_now_2)
    generation.save()


if __name__ == '__main__':
    try:
        main()
    except Exception as e:
        if comlink.is_remote_exception(e):
            logging.error(comlink.format_stacktrace_for_remote_exception(e))
        else:
            logging.exception(e)
        raise
