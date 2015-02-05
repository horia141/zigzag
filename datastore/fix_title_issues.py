import HTMLParser
import logging

import rest_api.models as models


def main():
    logging.basicConfig(level=logging.INFO, filename='var/fix_title_issues.log')

    parser = HTMLParser.HTMLParser()

    for artifact in models.Artifact.all():
        logging.info('Analyzing artifact source %d - %s', artifact.id, artifact.title)

        new_title = parser.unescape(artifact.title)
        artifact.title = new_title
        artifact.save()

        logging.info('Saved with new title - %s', new_title)

if __name__ == '__main__':
    main()
