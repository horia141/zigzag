// Request and response objects for the externally visible API.

include "model.thrift"

namespace py common.api
namespace java com.zigzag.common.api
namespace cocoa common.api

struct NextGenResponse {
  1: required model.Generation generation;
  2: required bool bandwidth_alert;
}
