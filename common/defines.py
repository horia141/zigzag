"""Constants and values useful throught the system."""

TIME_FORMAT = '%B %d, %Y %I:%M:%S %p %Z'

WEBPAGE_MIMETYPES = frozenset(['application/xhtml+xml', 'text/html', 'text/plain'])
PHOTO_MIMETYPES = frozenset(['image/gif', 'image/jpeg', 'image/png'])
PHOTO_MIMETYPES_TO_EXTENSION = dict([('image/gif', 'gif'), ('image/jpeg', 'jpg'), ('image/png', 'png'),
                                     ('video/mp4', 'mp4')])

FETCHER_PORT = 16000
FETCHER_LOG_PATH = 'var/fetcher.log'

EXPLORER_LOG_PATH = 'var/explorer.log'

PHOTO_SAVE_PORT = 16001
PHOTO_SAVE_LOG_PATH = 'var/photo_save.log'
PHOTO_SAVE_STORAGE_PATH = 'var/photos/%s'

VIDEO_SAVE_SCREEN_CONFIGS = {
    '480': {'width': 480},
    '720p': {'width': 1280},
}

IMAGE_SAVE_SCREEN_CONFIGS = {
    '800': {'width': 800},
    '1200': {'width': 1200},
}

IMAGE_SAVE_JPEG_OPTIONS = {
    'quality': 50,
    'optimize': True,
    'progressive': True
}

VIDEO_SAVE_BITRATE = '512k'

# Will result in 12 fps. A good default.
DEFAULT_TIME_BETWEEN_FRAMES_MS = 75

PHOTO_MAX_WIDTH = 2048
PHOTO_MAX_HEIGHT = 2048

PHOTO_TOO_LARGE = 'too-large'
PHOTO_IMAGE = 'image'
PHOTO_VIDEO = 'video'

