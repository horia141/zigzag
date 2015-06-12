#!/bin/bash

echo 'Creating tmp files'
tempdir=$(mktemp -dt gif_frames.XXXX)

echo 'Converting gif to frame set'
convert -coalesce "$1" $tempdir/frame%04d.jpg
if [ $? -ne 0 ]
then
  echo 'Cleaning up'
  rm -rf $tempdir
  exit 1
fi

echo 'Converting frame set to mp4'
avconv -r "$2" -i $tempdir/frame%04d.jpg -s "$3"x"$4" -b "$5" -loglevel panic -nostats "$6"
if [ $? -ne 0 ]
then
  echo 'Cleaning up'
  rm -rf $tempdir
  exit 2
fi

echo 'Cleaning up'
rm -rf $tempdir
exit 0
