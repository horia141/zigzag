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
	'artifact_title_name': 'reddit.com',
        'start_page_uri': 'http://reddit.com/r/%s',
        'subdomains': ['pics', 'comics', 'fffffffuuuuuuuuuuuu', 'ragecomics'
            'lolcats', 'AdviceAnimals', 'Demotivational', 'memes', 'images',
            'aww', 'cats', 'foxes', 'dogpictures', 'sloths', 'gifs',
            'reactiongifs']
    },
    2: {
        'id': 2,
        'name': 'Imgur',
	'artifact_title_name': 'imgur',
        'start_page_uri': 'http://imgur.com'
    },
    3: {
        'id': 3,
        'name': '9GAG',
	'artifact_title_name': '9gag',
        'start_page_uri': 'http://9gag.com/%s',
        'subdomains': ['hot', 'trending', 'gif']
    }
}

const model.ScreenConfig IMAGE_SCREEN_CONFIG = {
    'name': '800',
    'width': 800
};

const model.ScreenConfig VIDEO_SCREEN_CONFIG = {
    'name': '480',
    'width': 480
};

const i32 IMAGE_SAVE_JPEG_OPTIONS_QUALITY = 50;
const bool IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE = true;
const bool IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE = true;

const string VIDEO_SAVE_BITRATE = '512k';

# Will result in 12 fps. A good default.
const i32 DEFAULT_TIME_BETWEEN_FRAMES_MS = 75;

const i32 PHOTO_MAX_WIDTH = 2048;
const i32 PHOTO_MAX_HEIGHT = 2048;

const i64 BANDWIDTH_ALERT_BYTES_PER_MONTH = 1073741824; // 1GB

// The maximum image size the fetcher service is allowed to download.Larger sizes seem to block
// Comlink for some reason. Luckly, images larger than this comprise less than 1% of the total,
// based on a 2000 image sample. They are mostly GIFs as well.
const i64 MAXIMUM_FETCHED_PHOTO_SIZE_IN_BYTES = 5242880; // 5MB
