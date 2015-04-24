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
    },
    3: {
        'id': 3,
        'name': '9GAG',
        'start_page_uri': 'http://9gag.com/%s',
        'subdomains': ['hot', 'trending', 'gif']
    }
}

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
