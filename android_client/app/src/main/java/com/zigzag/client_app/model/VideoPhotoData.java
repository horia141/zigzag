package com.zigzag.client_app.model;

import java.util.ArrayList;
import java.util.List;

public class VideoPhotoData extends PhotoData {
    private final TileData firstFrameDesc;
    private final TileData videoDesc;
    private final int timeBetweenFramesMs;
    private final int framerate;

    public VideoPhotoData(TileData firstFrameDesc, TileData videoDesc, int timeBetweenFramesMs, int framerate) {
        this.firstFrameDesc = firstFrameDesc;
        this.videoDesc = videoDesc;
        this.timeBetweenFramesMs = timeBetweenFramesMs;
        this.framerate = framerate;
    }

    public List<String> getUriPathsToFetch() {
        List<String> uriPathsToFetch = new ArrayList<>();
        uriPathsToFetch.add(firstFrameDesc.getUriPath());
        return uriPathsToFetch;
    }

    public TileData getFirstFrameDesc() {
        return firstFrameDesc;
    }

    public TileData getVideoDesc() {
        return videoDesc;
    }

    public int getTimeBetweenFramesMs() {
        return timeBetweenFramesMs;
    }

    public int getFramerate() {
        return framerate;
    }
}
