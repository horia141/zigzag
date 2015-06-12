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

#import "defines.h"

static NSString * common.definesTIME_FORMAT = @"%B %d, %Y %I:%M:%S %p %Z";
static int32_t common.definesMAX_ARTIFACTS_PER_GENERATION = 32;
static NSString * common.definesCACHEABLE_FILES_PATTERN = @"^.*(jpg|mp4)";
static NSString * common.definesSTANDARD_IMAGE_MIMETYPE = @"image/jpeg";
static NSString * common.definesSTANDARD_VIDEO_MIMETYPE = @"video/mp4";
static NSMutableSet * common.definesWEBPAGE_MIMETYPES;
static NSMutableSet * common.definesPHOTO_MIMETYPES;
static NSMutableDictionary * common.definesPHOTO_MIMETYPES_TO_EXTENSION;
static NSMutableDictionary * common.definesARTIFACT_SOURCES;
static common.modelScreenConfig * common.definesIMAGE_SCREEN_CONFIG;
static common.modelScreenConfig * common.definesVIDEO_SCREEN_CONFIG;
static int32_t common.definesIMAGE_SAVE_JPEG_OPTIONS_QUALITY = 50;
static BOOL common.definesIMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE = YES;
static BOOL common.definesIMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE = YES;
static NSString * common.definesVIDEO_SAVE_BITRATE = @"512k";
static int32_t common.definesDEFAULT_TIME_BETWEEN_FRAMES_MS = 75;
static int32_t common.definesPHOTO_MAX_WIDTH = 2048;
static int32_t common.definesPHOTO_MAX_HEIGHT = 2048;
static int64_t common.definesBANDWIDTH_ALERT_BYTES_PER_MONTH = 1073741824;
static int64_t common.definesMAXIMUM_FETCHED_PHOTO_SIZE_IN_BYTES = 5242880;
static NSString * common.definesIMGUR_CLIENT_ID = @"0df7ad16acdd582";
static NSString * common.definesIMGUR_CLIENT_SECRET = @"67d3131c430b25d393e55f2eaa63252249b734df";

@implementation common.definesdefinesConstants
+ (void) initialize {
  common.definesWEBPAGE_MIMETYPES = [[NSMutableSet alloc] initWithCapacity:3];
  [common.definesWEBPAGE_MIMETYPES addObject:@"application/xhtml+xml"];
  [common.definesWEBPAGE_MIMETYPES addObject:@"text/html"];
  [common.definesWEBPAGE_MIMETYPES addObject:@"text/plain"];

;
  common.definesPHOTO_MIMETYPES = [[NSMutableSet alloc] initWithCapacity:3];
  [common.definesPHOTO_MIMETYPES addObject:@"image/gif"];
  [common.definesPHOTO_MIMETYPES addObject:@"image/jpeg"];
  [common.definesPHOTO_MIMETYPES addObject:@"image/png"];

;
  common.definesPHOTO_MIMETYPES_TO_EXTENSION = [[NSMutableDictionary alloc] initWithCapacity:4]; 
  [common.definesPHOTO_MIMETYPES_TO_EXTENSION setObject:@"gif" forKey:@"image/gif"];
  [common.definesPHOTO_MIMETYPES_TO_EXTENSION setObject:@"jpg" forKey:@"image/jpeg"];
  [common.definesPHOTO_MIMETYPES_TO_EXTENSION setObject:@"png" forKey:@"image/png"];
  [common.definesPHOTO_MIMETYPES_TO_EXTENSION setObject:@"mp4" forKey:@"video/mp4"];

;
  common.definesARTIFACT_SOURCES = [[NSMutableDictionary alloc] initWithCapacity:3]; 
  common.modelArtifactSource * tmp0 = [[[common.modelArtifactSource alloc] init] autorelease_stub];
  [tmp0 setId:1];
  [tmp0 setName:@"Reddit"];
  [tmp0 setArtifact_title_name:@"reddit.com"];
  [tmp0 setStart_page_uri:@"http://reddit.com/r/%s"];
  NSMutableSet *tmp1 = [[[NSMutableSet alloc] initWithCapacity:1] autorelease_stub];
  [tmp1 addObject:@"pics"];

  [tmp0 setSubdomains:tmp1];

  [common.definesARTIFACT_SOURCES setObject:tmp0 forKey:[NSNumber numberWithLongLong: 1]];
  common.modelArtifactSource * tmp2 = [[[common.modelArtifactSource alloc] init] autorelease_stub];
  [tmp2 setId:2];
  [tmp2 setName:@"Imgur"];
  [tmp2 setArtifact_title_name:@"imgur"];
  [tmp2 setStart_page_uri:@"http://imgur.com"];
  NSMutableSet *tmp3 = [[[NSMutableSet alloc] initWithCapacity:4] autorelease_stub];
  [tmp3 addObject:@"hot:viral"];
  [tmp3 addObject:@"hot:top"];
  [tmp3 addObject:@"top:viral"];
  [tmp3 addObject:@"top:top"];

  [tmp2 setSubdomains:tmp3];

  [common.definesARTIFACT_SOURCES setObject:tmp2 forKey:[NSNumber numberWithLongLong: 2]];
  common.modelArtifactSource * tmp4 = [[[common.modelArtifactSource alloc] init] autorelease_stub];
  [tmp4 setId:3];
  [tmp4 setName:@"9GAG"];
  [tmp4 setArtifact_title_name:@"9gag"];
  [tmp4 setStart_page_uri:@"http://9gag.com/%s"];
  NSMutableSet *tmp5 = [[[NSMutableSet alloc] initWithCapacity:3] autorelease_stub];
  [tmp5 addObject:@"hot"];
  [tmp5 addObject:@"trending"];
  [tmp5 addObject:@"gif"];

  [tmp4 setSubdomains:tmp5];

  [common.definesARTIFACT_SOURCES setObject:tmp4 forKey:[NSNumber numberWithLongLong: 3]];

;
  common.definesIMAGE_SCREEN_CONFIG = [[common.modelScreenConfig alloc] init];
  [common.definesIMAGE_SCREEN_CONFIG setName:@"800"];
  [common.definesIMAGE_SCREEN_CONFIG setWidth:800];

;
  common.definesVIDEO_SCREEN_CONFIG = [[common.modelScreenConfig alloc] init];
  [common.definesVIDEO_SCREEN_CONFIG setName:@"480"];
  [common.definesVIDEO_SCREEN_CONFIG setWidth:480];

;
}
+ (NSString *) TIME_FORMAT{
  return common.definesTIME_FORMAT;
}
+ (int32_t) MAX_ARTIFACTS_PER_GENERATION{
  return common.definesMAX_ARTIFACTS_PER_GENERATION;
}
+ (NSString *) CACHEABLE_FILES_PATTERN{
  return common.definesCACHEABLE_FILES_PATTERN;
}
+ (NSString *) STANDARD_IMAGE_MIMETYPE{
  return common.definesSTANDARD_IMAGE_MIMETYPE;
}
+ (NSString *) STANDARD_VIDEO_MIMETYPE{
  return common.definesSTANDARD_VIDEO_MIMETYPE;
}
+ (NSMutableSet *) WEBPAGE_MIMETYPES{
  return common.definesWEBPAGE_MIMETYPES;
}
+ (NSMutableSet *) PHOTO_MIMETYPES{
  return common.definesPHOTO_MIMETYPES;
}
+ (NSMutableDictionary *) PHOTO_MIMETYPES_TO_EXTENSION{
  return common.definesPHOTO_MIMETYPES_TO_EXTENSION;
}
+ (NSMutableDictionary *) ARTIFACT_SOURCES{
  return common.definesARTIFACT_SOURCES;
}
+ (common.modelScreenConfig *) IMAGE_SCREEN_CONFIG{
  return common.definesIMAGE_SCREEN_CONFIG;
}
+ (common.modelScreenConfig *) VIDEO_SCREEN_CONFIG{
  return common.definesVIDEO_SCREEN_CONFIG;
}
+ (int32_t) IMAGE_SAVE_JPEG_OPTIONS_QUALITY{
  return common.definesIMAGE_SAVE_JPEG_OPTIONS_QUALITY;
}
+ (BOOL) IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE{
  return common.definesIMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE;
}
+ (BOOL) IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE{
  return common.definesIMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE;
}
+ (NSString *) VIDEO_SAVE_BITRATE{
  return common.definesVIDEO_SAVE_BITRATE;
}
+ (int32_t) DEFAULT_TIME_BETWEEN_FRAMES_MS{
  return common.definesDEFAULT_TIME_BETWEEN_FRAMES_MS;
}
+ (int32_t) PHOTO_MAX_WIDTH{
  return common.definesPHOTO_MAX_WIDTH;
}
+ (int32_t) PHOTO_MAX_HEIGHT{
  return common.definesPHOTO_MAX_HEIGHT;
}
+ (int64_t) BANDWIDTH_ALERT_BYTES_PER_MONTH{
  return common.definesBANDWIDTH_ALERT_BYTES_PER_MONTH;
}
+ (int64_t) MAXIMUM_FETCHED_PHOTO_SIZE_IN_BYTES{
  return common.definesMAXIMUM_FETCHED_PHOTO_SIZE_IN_BYTES;
}
+ (NSString *) IMGUR_CLIENT_ID{
  return common.definesIMGUR_CLIENT_ID;
}
+ (NSString *) IMGUR_CLIENT_SECRET{
  return common.definesIMGUR_CLIENT_SECRET;
}
@end

