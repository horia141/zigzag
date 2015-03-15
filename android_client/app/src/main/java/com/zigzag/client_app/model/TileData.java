package com.zigzag.client_app.model;

public class TileData {
    private final int width;
    private final int height;
    private final String uriPath;

    public TileData(int width, int height, String uriPath) {
        this.width = width;
        this.height = height;
        this.uriPath = uriPath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUriPath() {
        return uriPath;
    }
}
