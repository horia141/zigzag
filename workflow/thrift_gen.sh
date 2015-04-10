#!/bin/bash
# Generate all Thrift definitions for the supported languages.

LANGUAGES=(py java cocoa)
DEFINITIONS=(
  common/model.thrift
  common/defines.thrift
  common/api.thrift
)

mkdir -p gen

for lang in ${LANGUAGES[*]}
do
  mkdir -p gen/$lang

  for defn in ${DEFINITIONS[*]}
  do
    thrift -out gen/$lang --gen $lang $defn
  done
done
