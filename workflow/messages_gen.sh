#!/bin/bash
# Generate messages for the supported targets.

CONFIG_PATH=./assets/messages/messages.yaml
ANDROID_OUTPUT_PATH=./android_client/app/src/main/res/values/strings-messages.xml
IOS_OUTPUT_PATH=./ios_client/ios_client/Base.lproj/Localizable.string

python ./workflow/messages_gen/gen.py \
    --config_path=${CONFIG_PATH} \
    --android_output_path=${ANDROID_OUTPUT_PATH} \
    --ios_output_path=${IOS_OUTPUT_PATH}
