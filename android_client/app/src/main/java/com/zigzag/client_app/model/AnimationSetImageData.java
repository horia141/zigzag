package com.zigzag.client_app.model;

import java.util.List;

public class AnimationSetImageData extends ImageData {
    private final double timeBetweenFrames;
    private final List<TileData> framesDesc;

    public AnimationSetImageData(double timeBetweenFrames, List<TileData> framesDesc) {
        this.timeBetweenFrames = timeBetweenFrames;
        this.framesDesc = framesDesc;
    }

    public double getTimeBetweenFrames() {
        return timeBetweenFrames;
    }

    public List<TileData> getFramesDesc() {
        return framesDesc;
    }
}