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
static NSMutableSet * common.definesWEBPAGE_MIMETYPES;
static NSMutableSet * common.definesPHOTO_MIMETYPES;
static NSMutableDictionary * common.definesPHOTO_MIMETYPES_TO_EXTENSION;
static NSMutableDictionary * common.definesARTIFACT_SOURCES;
static int32_t common.definesAPI_SERVING_PORT = 9000;
static NSString * common.definesAPI_SERVING_LOG_PATH = @"var/api_serving.log";
static int32_t common.definesRES_SERVING_PORT = 9001;
static NSString * common.definesRES_SERVING_LOG_PATH = @"var/res_serving.log";
static int32_t common.definesFETCHER_PORT = 16000;
static NSString * common.definesFETCHER_LOG_PATH = @"var/fetcher.log";
static NSString * common.definesEXPLORER_LOG_PATH = @"var/explorer.log";
static int32_t common.definesPHOTO_SAVE_PORT = 16001;
static NSString * common.definesPHOTO_SAVE_LOG_PATH = @"var/photo_save.log";
static NSString * common.definesPHOTO_SAVE_STORAGE_PATH = @"var/photos/%s";
static NSMutableDictionary * common.definesVIDEO_SCREEN_CONFIG;
static NSMutableDictionary * common.definesIMAGE_SCREEN_CONFIG;
static int32_t common.definesIMAGE_SAVE_JPEG_OPTIONS_QUALITY = 50;
static BOOL common.definesIMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE = YES;
static BOOL common.definesIMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE = YES;
static NSString * common.definesVIDEO_SAVE_BITRATE = @"512k";
static int32_t common.definesDEFAULT_TIME_BETWEEN_FRAMES_MS = 75;
static int32_t common.definesPHOTO_MAX_WIDTH = 2048;
static int32_t common.definesPHOTO_MAX_HEIGHT = 2048;

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
  common.definesARTIFACT_SOURCES = [[NSMutableDictionary alloc] initWithCapacity:2]; 
  common.modelArtifactSource * tmp0 = [[[common.modelArtifactSource alloc] init] autorelease_stub];
  [tmp0 setId:1];
  [tmp0 setName:@"Reddit"];
  [tmp0 setStart_page_uri:@"http://reddit.com/r/%s"];
  NSMutableSet *tmp1 = [[[NSMutableSet alloc] initWithCapacity:1] autorelease_stub];
  [tmp1 addObject:@"pics"];

  [tmp0 setSubdomains:tmp1];

  [common.definesARTIFACT_SOURCES setObject:tmp0 forKey:@"Reddit"];
  common.modelArtifactSource * tmp2 = [[[common.modelArtifactSource alloc] init] autorelease_stub];
  [tmp2 setId:2];
  [tmp2 setName:@"Imgur"];
  [tmp2 setStart_page_uri:@"http://imgur.com"];

  [common.definesARTIFACT_SOURCES setObject:tmp2 forKey:@"Imgur"];

;
  common.definesVIDEO_SCREEN_CONFIG = [[NSMutableDictionary alloc] initWithCapacity:2]; 
  common.modelScreenConfig * tmp3 = [[[common.modelScreenConfig alloc] init] autorelease_stub];
  [tmp3 setId:1];
  [tmp3 setName:@"480"];
  [tmp3 setWidth:480];

  [common.definesVIDEO_SCREEN_CONFIG setObject:tmp3 forKey:@"480"];
  common.modelScreenConfig * tmp4 = [[[common.modelScreenConfig alloc] init] autorelease_stub];
  [tmp4 setId:2];
  [tmp4 setName:@"720p"];
  [tmp4 setWidth:1280];

  [common.definesVIDEO_SCREEN_CONFIG setObject:tmp4 forKey:@"720p"];

;
  common.definesIMAGE_SCREEN_CONFIG = [[NSMutableDictionary alloc] initWithCapacity:2]; 
  common.modelScreenConfig * tmp5 = [[[common.modelScreenConfig alloc] init] autorelease_stub];
  [tmp5 setId:1];
  [tmp5 setName:@"800"];
  [tmp5 setWidth:800];

  [common.definesIMAGE_SCREEN_CONFIG setObject:tmp5 forKey:@"800"];
  common.modelScreenConfig * tmp6 = [[[common.modelScreenConfig alloc] init] autorelease_stub];
  [tmp6 setId:2];
  [tmp6 setName:@"1200"];
  [tmp6 setWidth:1200];

  [common.definesIMAGE_SCREEN_CONFIG setObject:tmp6 forKey:@"1200"];

;
}
+ (NSString *) TIME_FORMAT{
  return common.definesTIME_FORMAT;
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
+ (int32_t) API_SERVING_PORT{
  return common.definesAPI_SERVING_PORT;
}
+ (NSString *) API_SERVING_LOG_PATH{
  return common.definesAPI_SERVING_LOG_PATH;
}
+ (int32_t) RES_SERVING_PORT{
  return common.definesRES_SERVING_PORT;
}
+ (NSString *) RES_SERVING_LOG_PATH{
  return common.definesRES_SERVING_LOG_PATH;
}
+ (int32_t) FETCHER_PORT{
  return common.definesFETCHER_PORT;
}
+ (NSString *) FETCHER_LOG_PATH{
  return common.definesFETCHER_LOG_PATH;
}
+ (NSString *) EXPLORER_LOG_PATH{
  return common.definesEXPLORER_LOG_PATH;
}
+ (int32_t) PHOTO_SAVE_PORT{
  return common.definesPHOTO_SAVE_PORT;
}
+ (NSString *) PHOTO_SAVE_LOG_PATH{
  return common.definesPHOTO_SAVE_LOG_PATH;
}
+ (NSString *) PHOTO_SAVE_STORAGE_PATH{
  return common.definesPHOTO_SAVE_STORAGE_PATH;
}
+ (NSMutableDictionary *) VIDEO_SCREEN_CONFIG{
  return common.definesVIDEO_SCREEN_CONFIG;
}
+ (NSMutableDictionary *) IMAGE_SCREEN_CONFIG{
  return common.definesIMAGE_SCREEN_CONFIG;
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
@end

