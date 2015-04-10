// Request and response objects for the externally visible API.

include "model.thrift"

namespace py common.api
namespace java com.zigzag.common.api

struct NextGenResponse {
  1: required model.Generation generation;
  2: required map<i64, model.ScreenConfig> screen_configs;
  3: required map<i64, model.ArtifactSource> artifact_sources;
  4: required list<model.Artifact> artifacts;
}
