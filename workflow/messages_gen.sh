#!/bin/bash
# Generate messages for the supported targets.

DEFINITIONS=(
  assets/messages/common.yaml=android:ios
  assets/messages/android_client.yaml=android
  assets/messages/ios_client.yaml=ios
)

ANDROID_OUTPUT_PATH=./android_client/app/src/main/res/values/strings-messages.xml
IOS_OUTPUT_PATH=./none

python ./workflow/messages_gen.py \
    --android_output_path=${ANDROID_OUTPUT_PATH} \
    --ios_output_path=${IOS_OUTPUT_PATH} \
    ${DEFINITIONS[*]}