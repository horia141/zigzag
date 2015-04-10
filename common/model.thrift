// Model objects for the whole system.

namespace py common.model
namespace java com.zigzag.common.model

enum GenerationStatus {
    IN_PROGRESS = 1,
    CLOSED = 2
}

// Next id: 4
struct Generation {
  1: required GenerationStatus status;
  2: required i32 date_started_ts;
  3: optional i32 date_ended_ts;
}

// Next id: 4
struct ArtifactSource {
  1: required string name;
  2: required string start_page_uri;
  3: optional set<string> subdomains;
}

// Next id: 3
struct ScreenConfig {
  1: required string name;
  2: required i32 width;
}

// Next id: 4
struct TileData {
  1: required i32 width;
  2: required i32 height;
  3: required string uri_path;
}
// Next id: 4
struct ImagePhotoData {
  1: required TileData full_image_desc;
  2: required list<TileData> tiles_desc;
  3: required i64 screen_config_fk;
}

// Next id: 5
struct VideoPhotoData {
  1: required TileData first_frame_desc;
  2: required TileData video_desc;
  3: required i32 time_between_frames_ms;
  4: required i32 framerate;
}

// Next id: 7
struct PhotoDescription {
  1: optional string subtitle;
  2: optional string description;
  3: required string source_uri;
  4: required string original_uri_path;
  5: optional ImagePhotoData image_data;
  6: optional VideoPhotoData video_data;
}

// Next id: 4
struct Artifact {
  1: required string page_uri;
  2: required string title;
  3: required list<PhotoDescription> photo_descriptions;
}

















