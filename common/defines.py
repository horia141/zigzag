"""Constants and values useful throught the system."""

TIME_FORMAT = '%B %d, %Y %I:%M:%S %p %Z'

WEBPAGE_MIMETYPES = frozenset(['application/xhtml+xml', 'text/html', 'text/plain'])
IMAGE_MIMETYPES = frozenset(['image/gif', 'image/jpeg', 'image/png'])
IMAGE_MIMETYPES_TO_EXTENSION = dict([('image/gif', 'gif'), ('image/jpeg', 'jpeg'), ('image/png', 'png')])

FETCHER_PORT = 16000
FETCHER_LOG_PATH = 'var/fetcher.log'

EXPLORER_LOG_PATH = 'var/explorer.log'

PHOTO_SAVE_PORT = 16001
PHOTO_SAVE_LOG_PATH = 'var/photo_save.log'
PHOTO_SAVE_IMAGE_STORAGE_PATH = 'var/photos/%s'
