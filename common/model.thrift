// Model objects for the whole system.

namespace py common.model
namespace java com.zigzag.common.model
namespace cocoa common.model

typedef i64 EntityId

// Next id: 4
struct TileData {
  1: required i32 width;
  2: required i32 height;
  3: required string uri_path;
}

// Next id: 3
struct ImagePhotoData {
  1: required TileData full_image;
  2: required list<TileData> tiles;
}

// Next id: 6
struct VideoPhotoData {
  1: required TileData first_frame;
  2: required TileData video;
  3: required i32 frame_count;
  4: required i32 frames_per_sec;
  5: required i32 time_between_frames_ms;
}

// Next id: 7
struct PhotoDescription {
  1: optional string subtitle;
  2: optional string description;
  3: required string source_uri;
  4: required string original_uri_path;
  5: optional map<EntityId, ImagePhotoData> image_data;
  6: optional map<EntityId, VideoPhotoData> video_data;
}

// Next id: 5
struct ArtifactSource {
  1: required EntityId id;
  2: required string name;
  3: required string start_page_uri;
  4: optional set<string> subdomains;
}

// Next id: 4
struct ScreenConfig {
  1: required EntityId id;
  2: required string name;
  3: required i32 width;
}

// Next id: 4
struct Artifact {
  1: required string page_uri;
  2: required string title;
  3: required list<PhotoDescription> photo_descriptions;
}

// Next id: 7
struct Generation {
  1: required EntityId id;
  2: required i32 date_started_ts;
  3: required i32 date_ended_ts;
  4: required map<EntityId, ArtifactSource> artifact_sources;
  5: required map<EntityId, ScreenConfig> screen_configs;
  6: optional list<Artifact> artifacts;
}
