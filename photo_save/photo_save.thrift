include "common/model.thrift"

namespace py photo_save_pb

exception Error {
  1: required string message
}

exception PhotoAlreadyExists {
  1: required string message
}

service Service {
  model.PhotoDescription process_one_photo(1: string source_uri) throws (1: Error e, 2: PhotoAlreadyExists pae)
}
