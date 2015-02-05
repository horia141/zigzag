import datetime
import pytz
import logging
import urllib2

import common.defines as defines
import common.flow_annotation as flow_annotation
import explorer.analyzers as analyzers
import rest_api.models as models


def main():
    logging.basicConfig(level=logging.INFO, filename='var/explorer.log')

    right_now = datetime.datetime.now(pytz.utc)

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

            logging.info('Saving artifact "%s" to database', artifact_desc['title'])
            artifact = models.Artifact.add(artifact_desc['page_url'], 
                generation, artifact_source, artifact_desc['title'],
                artifact_desc['images_description'])

            logging.info('Finished processing for "%s"', artifact.title)

        logging.info('Finished processing for "%s"', artifact_source.name)

    right_now_2 = datetime.datetime.now(pytz.utc)

    logging.info('Closing generation')
    generation.close(right_now_2)
    generation.save()


if __name__ == '__main__':
    main()
