#!/bin/bash
# Generate messages for the supported targets.

ANDROID_OUTPUT_DIR_PATH=./android_client/app/src/main/res
IOS_OUTPUT_DIR_PATH=./ios_client/ios_client/Images.xcassets

python ./workflow/graphics_gen.py \
    --android_output_dir_path=${ANDROID_OUTPUT_DIR_PATH} \
    --ios_output_dir_path=${ANDROID_OUTPUT_DIR_PATH}