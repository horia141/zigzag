#!/bin/bash
# Generate messages for the supported targets.

CONFIG_PATH=./assets/graphics/graphics.yaml
ANDROID_OUTPUT_DIR_PATH=./android_client/app/src/main/res
IOS_OUTPUT_DIR_PATH=./ios_client/ios_client/Images.xcassets

python ./workflow/graphics_gen/gen.py \
    --config_path=${CONFIG_PATH} \
    --android_output_dir_path=${ANDROID_OUTPUT_DIR_PATH} \
    --ios_output_dir_path=${IOS_OUTPUT_DIR_PATH}