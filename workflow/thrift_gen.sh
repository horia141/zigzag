#!/bin/bash
# Generate all Thrift definitions for the supported languages.

DEFINITIONS=(
  common/model.thrift=py:java:cocoa
  common/defines.thrift=py:java:cocoa
  common/api.thrift=py:java:cocoa
  fetcher/fetcher.thrift=py
  log_analyzer/log_analyzer.thrift=py
  photo_save/photo_save.thrift=py
)

mkdir -p gen

for defn in ${DEFINITIONS[*]}
do
  sp1=(${defn//=/ })
  thrift_file=${sp1[0]}
  langs=(${sp1[1]//:/ })
  for lang in ${langs[*]}
  do
    mkdir -p gen/$lang
    if [ "$lang" = "py" ]
    then
      thrift -I `pwd` -out gen/$lang --gen py:new_style $thrift_file
    else
      thrift -I `pwd` -out gen/$lang --gen $lang $thrift_file
    fi
  done
done
