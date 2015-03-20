package com.zigzag.client_app.model;

import java.util.ArrayList;
import java.util.List;

public class AnimationSetImageData extends ImageData {
    private final long timeBetweenFrames;
    private final List<TileData> framesDesc;

    public AnimationSetImageData(long timeBetweenFrames, List<TileData> framesDesc) {
        this.timeBetweenFrames = timeBetweenFrames;
        this.framesDesc = framesDesc;
    }

    public long getTimeBetweenFrames() {
        return timeBetweenFrames;
    }

    public List<TileData> getFramesDesc() {
        return framesDesc;
    }

    public List<String> getUriPathsToFetch() {
        List<String> uriPaths = new ArrayList<>(framesDesc.size());

        for (TileData frameDesc : framesDesc) {
            uriPaths.add(frameDesc.getUriPath());
        }

        return uriPaths;
    }

    public int getTotalHeight() {
        return framesDesc.get(0).getHeight();
    }
}
