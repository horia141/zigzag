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
  [tmp0 setStart_page_uri:@"http://reddit.com/r/%s"];
  NSMutableSet *tmp1 = [[[NSMutableSet alloc] initWithCapacity:0] autorelease_stub];

  [tmp0 setSubdomains:tmp1];

  [common.definesARTIFACT_SOURCES setObject:tmp0 forKey:[NSNumber numberWithLongLong: 1]];
  common.modelArtifactSource * tmp2 = [[[common.modelArtifactSource alloc] init] autorelease_stub];
  [tmp2 setId:2];
  [tmp2 setName:@"Imgur"];
  [tmp2 setStart_page_uri:@"http://imgur.com"];

  [common.definesARTIFACT_SOURCES setObject:tmp2 forKey:[NSNumber numberWithLongLong: 2]];
  common.modelArtifactSource * tmp3 = [[[common.modelArtifactSource alloc] init] autorelease_stub];
  [tmp3 setId:3];
  [tmp3 setName:@"9GAG"];
  [tmp3 setStart_page_uri:@"http://9gag.com/%s"];
  NSMutableSet *tmp4 = [[[NSMutableSet alloc] initWithCapacity:3] autorelease_stub];
  [tmp4 addObject:@"hot"];
  [tmp4 addObject:@"trending"];
  [tmp4 addObject:@"gif"];

  [tmp3 setSubdomains:tmp4];

  [common.definesARTIFACT_SOURCES setObject:tmp3 forKey:[NSNumber numberWithLongLong: 3]];

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
@end

