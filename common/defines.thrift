// Constants shared across the system
include "model.thrift"

namespace py common.defines
namespace java com.zigzag.common.defines
namespace cocoa common.defines

const string TIME_FORMAT = '%B %d, %Y %I:%M:%S %p %Z';

const i32 MAX_ARTIFACTS_PER_GENERATION = 32;

const set<string> WEBPAGE_MIMETYPES = ['application/xhtml+xml', 'text/html', 'text/plain'];
const set<string> PHOTO_MIMETYPES = ['image/gif', 'image/jpeg', 'image/png'];
const map<string, string> PHOTO_MIMETYPES_TO_EXTENSION = {'image/gif': 'gif', 'image/jpeg': 'jpg', 'image/png': 'png', 'video/mp4': 'mp4'};

const map<i64, model.ArtifactSource> ARTIFACT_SOURCES = {
    1: {
        'id': 1,
	'name': 'Reddit',
	'start_page_uri': 'http://reddit.com/r/%s',
        'subdomains': ['pics', 'comics', 'fffffffuuuuuuuuuuuu', 'ragecomics'
            'lolcats', 'AdviceAnimals', 'Demotivational', 'memes', 'images',
            'aww', 'cats', 'foxes', 'dogpictures', 'sloths', 'gifs',
            'reactiongifs']
    },
    2: {
        'id': 2,
        'name': 'Imgur',
        'start_page_uri': 'http://imgur.com'
    }
}

const i32 API_SERVING_PORT = 9000;
const string API_SERVING_LOG_PATH = 'var/api_serving.log';

const i32 RES_SERVING_PORT = 9001;
const string RES_SERVING_LOG_PATH = 'var/res_serving.log';

const i32 FETCHER_PORT = 16000;
const string FETCHER_LOG_PATH = 'var/fetcher.log';

const string EXPLORER_LOG_PATH = 'var/explorer.log';

const i32 PHOTO_SAVE_PORT = 16001;
const string PHOTO_SAVE_LOG_PATH = 'var/photo_save.log';
const string PHOTO_SAVE_STORAGE_PATH = 'var/photos/%s';

const map<i64, model.ScreenConfig> VIDEO_SCREEN_CONFIG = {
    1: {'id': 1, 'name': '480', 'width': 480},
    2: {'id': 2, 'name': '720p', 'width': 1280}
};

const map<i64, model.ScreenConfig> IMAGE_SCREEN_CONFIG = {
    3: {'id': 3, 'name': '800', 'width': 800},
    4: {'id': 4, 'name': '1200', 'width': 1200}
};

const i32 IMAGE_SAVE_JPEG_OPTIONS_QUALITY = 50;
const bool IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE = true;
const bool IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE = true;

const string VIDEO_SAVE_BITRATE = '512k';

# Will result in 12 fps. A good default.
const i32 DEFAULT_TIME_BETWEEN_FRAMES_MS = 75;

const i32 PHOTO_MAX_WIDTH = 2048;
const i32 PHOTO_MAX_HEIGHT = 2048;
