import datetime
import pytz
import logging
import urllib2

import comlink
import comlink.serializer.pickle as serializer
import comlink.transport.localipc as transport

import common.defines as defines
import common.flow_annotation as flow_annotation
import explorer.analyzers as analyzers
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
        'Reddit': analyzers.Reddit(),
        'Imgur': analyzers.Imgur(),
    }

    logging.info('Creating new generation')

    generation = models.Generation.add(right_now)

    logging.info('Analyzing sources')

    for artifact_source in models.ArtifactSource.all():
        logging.info('Analyzing artifact source "%s"', artifact_source.name)

        try:
            analyzer = all_analyzers[artifact_source.name]
            logging.info('Found analyzer for artifact source')
        except KeyError as e:
            logging.warn('Could not analyze - analyzer does not exist')
            continue

        try:
            artifact_descs = analyzer.analyze()
        except analyzers.Error as e:
            logging.warn('Could not analyze "%s" - %s', artifact_source.name, str(e))
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

            for image_raw_description in artifact_desc['images_description']:
                subtitle = image_raw_description['subtitle']
                description = image_raw_description['description']
                source_uri = image_raw_description['uri_path']
                images_description.append(photo_save_client.process_one_image(
                    subtitle, description, source_uri))

            logging.info('Saving artifact "%s" to database', artifact_desc['title'])
            artifact = models.Artifact.add(artifact_desc['page_url'], 
                generation, artifact_source, artifact_desc['title'],
                images_description)

            logging.info('Finished processing for "%s"', artifact.title)

        logging.info('Finished processing for "%s"', artifact_source.name)

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
