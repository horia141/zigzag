namespace py fetcher

exception IOError {
  1: required string message
}

exception PhotoTooBigError {
  1: required string message
}

struct Response {
  1: required string mime_type
  2: optional string content
}

service Service {
  Response fetch_url(1: string page_url) throws (1: IOError io)
  Response fetch_url_mimetype(1: string page_url) throws (1: IOError io)
  Response fetch_photo(1: string photo_url) throws (1: IOError io, 2: PhotoTooBigError big)
}
