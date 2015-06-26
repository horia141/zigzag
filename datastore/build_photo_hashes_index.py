import argparse
import logging
import os.path

from PIL import Image

import common.model.ttypes as model
import photo_dedup
import rest_api.models as datastore
import utils.pidfile as pidfile


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--photos_dir', type=str, required=True,
        help='Directory to store the processed/derived photos')
    parser.add_argument('--pidfile_path', type=str, required=True,
        help='Path for the pidfile')
    parser.add_argument('--log_path', type=str, required=True,
        help='Path to the log file')
    args = parser.parse_args()

    pidfile.write_pidfile(args.pidfile_path)

    logging.basicConfig(level=logging.INFO, filename=args.log_path)

    for generation_store in datastore.GenerationStore.objects.all():
        logging.info('Processing generation "%d"', generation_store.id)

        logging.info('Decoding generation')
        generation = datastore.parse(model.Generation, generation_store.generation_ser)

        for artifact in generation.artifacts:
            logging.info('Processing artifact "%s"' % artifact.page_uri)

            photo_idx = 0
            photos_size = len(artifact.photo_descriptions)
            for photo_description in artifact.photo_descriptions:
                logging.info('Processing photo "%d/%d' % (photo_idx, photos_size))

                if photo_description.photo_data.too_big_photo_data is not None:
                    logging.info('Found TooBigPhotoData. Skipping')
                elif photo_description.photo_data.image_photo_data is not None:
                    logging.info('Found image')
                    try:
                        uri_path = photo_description.photo_data.image_photo_data.tiles[0].uri_path
                        logging.info('Reading image form storage')
                        first_tile_image = Image.open(os.path.join(args.photos_dir, uri_path))
                        logging.info('Computing dedup hash')
                        dedup_hash = photo_dedup.dedup_hash(first_tile_image)
                        logging.info('Updating hash state')
                        photo_hash = datastore.photo_exists_by_dedup_hash(dedup_hash)
                        if photo_hash is None:
                            logging.info('New image')
                            datastore.mark_photo_as_existing(dedup_hash, uri_path)
                        else:
                            logging.info('Old image')
                            datastore.increment_photo_dup_count(photo_hash)
                    except IOError as e:
                        logging.error(str(e))
                elif photo_description.photo_data.image_photo_data is not None:
                    logging.info('Found video. Skipping')

                photo_idx += 1


if __name__ == '__main__':
    main()
