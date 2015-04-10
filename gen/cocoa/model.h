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


enum GenerationStatus {
  GenerationStatus_OPENED = 1,
  GenerationStatus_CLOSED = 2
};

@interface Generation : NSObject <TBase, NSCoding> {
  int64_t __id;
  int __status;
  int32_t __date_started_ts;
  int32_t __date_ended_ts;

  BOOL __id_isset;
  BOOL __status_isset;
  BOOL __date_started_ts_isset;
  BOOL __date_ended_ts_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=id, setter=setId:) int64_t id;
@property (nonatomic, getter=status, setter=setStatus:) int status;
@property (nonatomic, getter=date_started_ts, setter=setDate_started_ts:) int32_t date_started_ts;
@property (nonatomic, getter=date_ended_ts, setter=setDate_ended_ts:) int32_t date_ended_ts;
#endif

- (id) init;
- (id) initWithId: (int64_t) id status: (int) status date_started_ts: (int32_t) date_started_ts date_ended_ts: (int32_t) date_ended_ts;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (int64_t) id;
- (void) setId: (int64_t) id;
#endif
- (BOOL) idIsSet;

#if !__has_feature(objc_arc)
- (int) status;
- (void) setStatus: (int) status;
#endif
- (BOOL) statusIsSet;

#if !__has_feature(objc_arc)
- (int32_t) date_started_ts;
- (void) setDate_started_ts: (int32_t) date_started_ts;
#endif
- (BOOL) date_started_tsIsSet;

#if !__has_feature(objc_arc)
- (int32_t) date_ended_ts;
- (void) setDate_ended_ts: (int32_t) date_ended_ts;
#endif
- (BOOL) date_ended_tsIsSet;

@end

@interface ArtifactSource : NSObject <TBase, NSCoding> {
  int64_t __id;
  NSString * __name;
  NSString * __start_page_uri;
  NSMutableSet * __subdomains;

  BOOL __id_isset;
  BOOL __name_isset;
  BOOL __start_page_uri_isset;
  BOOL __subdomains_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=id, setter=setId:) int64_t id;
@property (nonatomic, retain, getter=name, setter=setName:) NSString * name;
@property (nonatomic, retain, getter=start_page_uri, setter=setStart_page_uri:) NSString * start_page_uri;
@property (nonatomic, retain, getter=subdomains, setter=setSubdomains:) NSMutableSet * subdomains;
#endif

- (id) init;
- (id) initWithId: (int64_t) id name: (NSString *) name start_page_uri: (NSString *) start_page_uri subdomains: (NSMutableSet *) subdomains;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (int64_t) id;
- (void) setId: (int64_t) id;
#endif
- (BOOL) idIsSet;

#if !__has_feature(objc_arc)
- (NSString *) name;
- (void) setName: (NSString *) name;
#endif
- (BOOL) nameIsSet;

#if !__has_feature(objc_arc)
- (NSString *) start_page_uri;
- (void) setStart_page_uri: (NSString *) start_page_uri;
#endif
- (BOOL) start_page_uriIsSet;

#if !__has_feature(objc_arc)
- (NSMutableSet *) subdomains;
- (void) setSubdomains: (NSMutableSet *) subdomains;
#endif
- (BOOL) subdomainsIsSet;

@end

@interface ScreenConfig : NSObject <TBase, NSCoding> {
  int64_t __id;
  NSString * __name;
  int32_t __width;

  BOOL __id_isset;
  BOOL __name_isset;
  BOOL __width_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=id, setter=setId:) int64_t id;
@property (nonatomic, retain, getter=name, setter=setName:) NSString * name;
@property (nonatomic, getter=width, setter=setWidth:) int32_t width;
#endif

- (id) init;
- (id) initWithId: (int64_t) id name: (NSString *) name width: (int32_t) width;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (int64_t) id;
- (void) setId: (int64_t) id;
#endif
- (BOOL) idIsSet;

#if !__has_feature(objc_arc)
- (NSString *) name;
- (void) setName: (NSString *) name;
#endif
- (BOOL) nameIsSet;

#if !__has_feature(objc_arc)
- (int32_t) width;
- (void) setWidth: (int32_t) width;
#endif
- (BOOL) widthIsSet;

@end

@interface TileData : NSObject <TBase, NSCoding> {
  int32_t __width;
  int32_t __height;
  NSString * __uri_path;

  BOOL __width_isset;
  BOOL __height_isset;
  BOOL __uri_path_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=width, setter=setWidth:) int32_t width;
@property (nonatomic, getter=height, setter=setHeight:) int32_t height;
@property (nonatomic, retain, getter=uri_path, setter=setUri_path:) NSString * uri_path;
#endif

- (id) init;
- (id) initWithWidth: (int32_t) width height: (int32_t) height uri_path: (NSString *) uri_path;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (int32_t) width;
- (void) setWidth: (int32_t) width;
#endif
- (BOOL) widthIsSet;

#if !__has_feature(objc_arc)
- (int32_t) height;
- (void) setHeight: (int32_t) height;
#endif
- (BOOL) heightIsSet;

#if !__has_feature(objc_arc)
- (NSString *) uri_path;
- (void) setUri_path: (NSString *) uri_path;
#endif
- (BOOL) uri_pathIsSet;

@end

@interface ImagePhotoData : NSObject <TBase, NSCoding> {
  TileData * __full_image_desc;
  NSMutableArray * __tiles_desc;
  int64_t __screen_config_fk;

  BOOL __full_image_desc_isset;
  BOOL __tiles_desc_isset;
  BOOL __screen_config_fk_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, retain, getter=full_image_desc, setter=setFull_image_desc:) TileData * full_image_desc;
@property (nonatomic, retain, getter=tiles_desc, setter=setTiles_desc:) NSMutableArray * tiles_desc;
@property (nonatomic, getter=screen_config_fk, setter=setScreen_config_fk:) int64_t screen_config_fk;
#endif

- (id) init;
- (id) initWithFull_image_desc: (TileData *) full_image_desc tiles_desc: (NSMutableArray *) tiles_desc screen_config_fk: (int64_t) screen_config_fk;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (TileData *) full_image_desc;
- (void) setFull_image_desc: (TileData *) full_image_desc;
#endif
- (BOOL) full_image_descIsSet;

#if !__has_feature(objc_arc)
- (NSMutableArray *) tiles_desc;
- (void) setTiles_desc: (NSMutableArray *) tiles_desc;
#endif
- (BOOL) tiles_descIsSet;

#if !__has_feature(objc_arc)
- (int64_t) screen_config_fk;
- (void) setScreen_config_fk: (int64_t) screen_config_fk;
#endif
- (BOOL) screen_config_fkIsSet;

@end

@interface VideoPhotoData : NSObject <TBase, NSCoding> {
  TileData * __first_frame_desc;
  TileData * __video_desc;
  int32_t __time_between_frames_ms;
  int32_t __framerate;

  BOOL __first_frame_desc_isset;
  BOOL __video_desc_isset;
  BOOL __time_between_frames_ms_isset;
  BOOL __framerate_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, retain, getter=first_frame_desc, setter=setFirst_frame_desc:) TileData * first_frame_desc;
@property (nonatomic, retain, getter=video_desc, setter=setVideo_desc:) TileData * video_desc;
@property (nonatomic, getter=time_between_frames_ms, setter=setTime_between_frames_ms:) int32_t time_between_frames_ms;
@property (nonatomic, getter=framerate, setter=setFramerate:) int32_t framerate;
#endif

- (id) init;
- (id) initWithFirst_frame_desc: (TileData *) first_frame_desc video_desc: (TileData *) video_desc time_between_frames_ms: (int32_t) time_between_frames_ms framerate: (int32_t) framerate;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (TileData *) first_frame_desc;
- (void) setFirst_frame_desc: (TileData *) first_frame_desc;
#endif
- (BOOL) first_frame_descIsSet;

#if !__has_feature(objc_arc)
- (TileData *) video_desc;
- (void) setVideo_desc: (TileData *) video_desc;
#endif
- (BOOL) video_descIsSet;

#if !__has_feature(objc_arc)
- (int32_t) time_between_frames_ms;
- (void) setTime_between_frames_ms: (int32_t) time_between_frames_ms;
#endif
- (BOOL) time_between_frames_msIsSet;

#if !__has_feature(objc_arc)
- (int32_t) framerate;
- (void) setFramerate: (int32_t) framerate;
#endif
- (BOOL) framerateIsSet;

@end

@interface PhotoDescription : NSObject <TBase, NSCoding> {
  NSString * __subtitle;
  NSString * __description;
  NSString * __source_uri;
  NSString * __original_uri_path;
  ImagePhotoData * __image_data;
  VideoPhotoData * __video_data;

  BOOL __subtitle_isset;
  BOOL __description_isset;
  BOOL __source_uri_isset;
  BOOL __original_uri_path_isset;
  BOOL __image_data_isset;
  BOOL __video_data_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, retain, getter=subtitle, setter=setSubtitle:) NSString * subtitle;
@property (nonatomic, retain, getter=description, setter=setDescription:) NSString * description;
@property (nonatomic, retain, getter=source_uri, setter=setSource_uri:) NSString * source_uri;
@property (nonatomic, retain, getter=original_uri_path, setter=setOriginal_uri_path:) NSString * original_uri_path;
@property (nonatomic, retain, getter=image_data, setter=setImage_data:) ImagePhotoData * image_data;
@property (nonatomic, retain, getter=video_data, setter=setVideo_data:) VideoPhotoData * video_data;
#endif

- (id) init;
- (id) initWithSubtitle: (NSString *) subtitle description: (NSString *) description source_uri: (NSString *) source_uri original_uri_path: (NSString *) original_uri_path image_data: (ImagePhotoData *) image_data video_data: (VideoPhotoData *) video_data;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (NSString *) subtitle;
- (void) setSubtitle: (NSString *) subtitle;
#endif
- (BOOL) subtitleIsSet;

#if !__has_feature(objc_arc)
- (NSString *) description;
- (void) setDescription: (NSString *) description;
#endif
- (BOOL) descriptionIsSet;

#if !__has_feature(objc_arc)
- (NSString *) source_uri;
- (void) setSource_uri: (NSString *) source_uri;
#endif
- (BOOL) source_uriIsSet;

#if !__has_feature(objc_arc)
- (NSString *) original_uri_path;
- (void) setOriginal_uri_path: (NSString *) original_uri_path;
#endif
- (BOOL) original_uri_pathIsSet;

#if !__has_feature(objc_arc)
- (ImagePhotoData *) image_data;
- (void) setImage_data: (ImagePhotoData *) image_data;
#endif
- (BOOL) image_dataIsSet;

#if !__has_feature(objc_arc)
- (VideoPhotoData *) video_data;
- (void) setVideo_data: (VideoPhotoData *) video_data;
#endif
- (BOOL) video_dataIsSet;

@end

@interface Artifact : NSObject <TBase, NSCoding> {
  int64_t __id;
  NSString * __page_uri;
  NSString * __title;
  NSMutableArray * __photo_descriptions;

  BOOL __id_isset;
  BOOL __page_uri_isset;
  BOOL __title_isset;
  BOOL __photo_descriptions_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=id, setter=setId:) int64_t id;
@property (nonatomic, retain, getter=page_uri, setter=setPage_uri:) NSString * page_uri;
@property (nonatomic, retain, getter=title, setter=setTitle:) NSString * title;
@property (nonatomic, retain, getter=photo_descriptions, setter=setPhoto_descriptions:) NSMutableArray * photo_descriptions;
#endif

- (id) init;
- (id) initWithId: (int64_t) id page_uri: (NSString *) page_uri title: (NSString *) title photo_descriptions: (NSMutableArray *) photo_descriptions;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (int64_t) id;
- (void) setId: (int64_t) id;
#endif
- (BOOL) idIsSet;

#if !__has_feature(objc_arc)
- (NSString *) page_uri;
- (void) setPage_uri: (NSString *) page_uri;
#endif
- (BOOL) page_uriIsSet;

#if !__has_feature(objc_arc)
- (NSString *) title;
- (void) setTitle: (NSString *) title;
#endif
- (BOOL) titleIsSet;

#if !__has_feature(objc_arc)
- (NSMutableArray *) photo_descriptions;
- (void) setPhoto_descriptions: (NSMutableArray *) photo_descriptions;
#endif
- (BOOL) photo_descriptionsIsSet;

@end

@interface modelConstants : NSObject {
}
@end
