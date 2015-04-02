#!/bin/bash

tempdir=$(mktemp -dt gif_frames.XXXX)
convert -coalesce "$1" $tempdir/frame%04d.jpg
avconv -r "$2" -i $tempdir/frame%04d.jpg -s "$3"x"$4" -b "$5" -loglevel panic -nostats "$6"
rm -rf $tempdir
