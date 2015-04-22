// Model objects for the log analyzer.

namespace py log_analyzer.protos
namespace java com.zigzag.log_analyzer.protos
namespace cocoa log_analyzer.protos

// Next id: 4
struct SystemConsumption {
  1: required i64 total_bytes;
  2: required i64 api_serving_bytes;
  3: required i64 res_serving_bytes;
}

// Next id: 5
struct AnalysisResult {
  1: required i64 id;
  2: required i64 datetime_ran_ts;
  3: required SystemConsumption month;
  4: required SystemConsumption day;
}
