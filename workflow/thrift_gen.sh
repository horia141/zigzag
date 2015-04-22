#!/bin/bash
# Generate all Thrift definitions for the supported languages.

LANGUAGES=(py java cocoa)
DEFINITIONS=(
  common/model.thrift
  common/defines.thrift
  common/api.thrift
  log_analyzer/log_analyzer.thrift
)

mkdir -p gen

for lang in ${LANGUAGES[*]}
do
  mkdir -p gen/$lang

  for defn in ${DEFINITIONS[*]}
  do
    if [ "$lang" = "py" ]
    then
      thrift -out gen/$lang --gen py:new_style $defn
    else
      thrift -out gen/$lang --gen $lang $defn
    fi
  done
done
