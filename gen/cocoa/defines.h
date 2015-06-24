/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

#import <Foundation/Foundation.h>

#import "TProtocol.h"
#import "TApplicationException.h"
#import "TProtocolException.h"
#import "TProtocolUtil.h"
#import "TProcessor.h"
#import "TObjective-C.h"
#import "TBase.h"

#import "model.h"

@interface common.definesdefinesConstants : NSObject {
}
+ (NSString *) TIME_FORMAT;
+ (int32_t) MAX_ARTIFACTS_PER_GENERATION;
+ (NSString *) CACHEABLE_FILES_PATTERN;
+ (NSString *) STANDARD_IMAGE_MIMETYPE;
+ (NSString *) STANDARD_VIDEO_MIMETYPE;
+ (NSMutableSet *) WEBPAGE_MIMETYPES;
+ (NSMutableSet *) PHOTO_MIMETYPES;
+ (NSMutableDictionary *) PHOTO_MIMETYPES_TO_EXTENSION;
+ (NSMutableDictionary *) ARTIFACT_SOURCES;
+ (common.modelScreenConfig *) IMAGE_SCREEN_CONFIG;
+ (common.modelScreenConfig *) VIDEO_SCREEN_CONFIG;
+ (int32_t) IMAGE_SAVE_JPEG_OPTIONS_QUALITY;
+ (BOOL) IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE;
+ (BOOL) IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE;
+ (NSString *) VIDEO_SAVE_BITRATE;
+ (int32_t) DEFAULT_TIME_BETWEEN_FRAMES_MS;
+ (int32_t) PHOTO_MAX_WIDTH;
+ (int32_t) PHOTO_MAX_HEIGHT;
+ (int64_t) BANDWIDTH_ALERT_BYTES_PER_MONTH;
+ (int64_t) MAXIMUM_FETCHED_PHOTO_SIZE_IN_BYTES;
+ (NSString *) IMGUR_CLIENT_ID;
+ (NSString *) IMGUR_CLIENT_SECRET;
+ (NSString *) EXPLORER_USER_AGENT;
+ (double) PHOTO_DEDUP_KEEP_SIZE_FACTOR;
@end
