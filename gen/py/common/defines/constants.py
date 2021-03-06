#
# Autogenerated by Thrift Compiler (0.9.2)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py:new_style
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
from ttypes import *

TIME_FORMAT = "%B %d, %Y %I:%M:%S %p %Z"
MAX_ARTIFACTS_PER_GENERATION = 32
CACHEABLE_FILES_PATTERN = "^.*(jpg|mp4)"
STANDARD_IMAGE_MIMETYPE = "image/jpeg"
STANDARD_VIDEO_MIMETYPE = "video/mp4"
WEBPAGE_MIMETYPES = set([
  "application/xhtml+xml",
  "text/html",
  "text/plain",
])
PHOTO_MIMETYPES = set([
  "image/gif",
  "image/jpeg",
  "image/png",
])
PHOTO_MIMETYPES_TO_EXTENSION = {
  "image/gif" : "gif",
  "image/jpeg" : "jpg",
  "image/png" : "png",
  "video/mp4" : "mp4",
}
ARTIFACT_SOURCES = {
  1 : common.model.ttypes.ArtifactSource(**{
    "id" : 1,
    "name" : "Reddit",
    "artifact_title_name" : "reddit.com",
    "start_page_uri" : "http://reddit.com/r/%s",
    "subdomains" : set([
      "pics",
      "comics",
      "fffffffuuuuuuuuuuuu",
      "ragecomics",
      "lolcats",
      "AdviceAnimals",
      "Demotivational",
      "memes",
      "images",
      "aww",
      "cats",
      "foxes",
      "dogpictures",
      "sloths",
      "gifs",
      "reactiongifs",
    ]),
  }),
  2 : common.model.ttypes.ArtifactSource(**{
    "id" : 2,
    "name" : "Imgur",
    "artifact_title_name" : "imgur",
    "start_page_uri" : "http://imgur.com",
    "subdomains" : set([
      "hot:viral",
      "hot:top",
      "top:viral",
      "top:top",
    ]),
  }),
  3 : common.model.ttypes.ArtifactSource(**{
    "id" : 3,
    "name" : "9GAG",
    "artifact_title_name" : "9gag",
    "start_page_uri" : "http://9gag.com/%s",
    "subdomains" : set([
      "hot",
      "trending",
      "gif",
    ]),
  }),
}
IMAGE_SCREEN_CONFIG = common.model.ttypes.ScreenConfig(**{
  "name" : "800",
  "width" : 800,
})
VIDEO_SCREEN_CONFIG = common.model.ttypes.ScreenConfig(**{
  "name" : "480",
  "width" : 480,
})
IMAGE_SAVE_JPEG_OPTIONS_QUALITY = 50
IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE = True
IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE = True
VIDEO_SAVE_BITRATE = "512k"
DEFAULT_TIME_BETWEEN_FRAMES_MS = 75
PHOTO_MAX_WIDTH = 2048
PHOTO_MAX_HEIGHT = 2048
BANDWIDTH_ALERT_BYTES_PER_MONTH = 1073741824
MAXIMUM_FETCHED_PHOTO_SIZE_IN_BYTES = 5242880
RES_SERVING_CACHE_CONTROL = "max-age=2592000"
IMGUR_CLIENT_ID = "0df7ad16acdd582"
IMGUR_CLIENT_SECRET = "67d3131c430b25d393e55f2eaa63252249b734df"
EXPLORER_USER_AGENT = "ZigZag Explorer v1"
PHOTO_DEDUP_KEEP_SIZE_FACTOR = 0.25
PHOTO_DEDUP_KEY_FRAMES = [
  0,
  4,
  9,
  14,
  19,
]
PHOTO_DEDUP_SMALL_WIDTH = 128
PHOTO_DEDUP_EDGE_PIXELS = 16
URLS_MAIN = "http://picjar.io"
URLS_API_NEXTGEN_PATTERN = "http://picjar.io/api/v1/nextgen?from=%s&output=thrift"
URLS_API_NEXTGEN_JSON_PATTERN = "http://picjar.io/api/v1/nextgen?from=%s&output=json"
URLS_API_RES_URL_PATTERN = "http://picjar.io/res/%s"
URLS_SHARE_APP_TARGET = "http://picjar.io/getpicjar"
URLS_GOOGLE_PLAY_STORE = "https://play.google.com/store"
URLS_APPSTORE = "https://itunes.apple.com"
