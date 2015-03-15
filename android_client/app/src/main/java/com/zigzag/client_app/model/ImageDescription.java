package com.zigzag.client_app.model;

import java.util.Map;

public class ImageDescription {
    private final String subtitle;
    private final String description;
    private final String sourceUri;
    private final String originalImageUriPath;
    private final Map<ScreenConfig, ImageData> imageData;

    public ImageDescription(String subtitle, String description, String sourceUri,
            String originalImageUriPath, Map<ScreenConfig, ImageData> imageData) {
        this.subtitle = subtitle;
        this.description = description;
        this.sourceUri = sourceUri;
        this.originalImageUriPath = originalImageUriPath;
        this.imageData = imageData;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceUri() {
        return sourceUri;
    }

    public String getOriginalImageUriPath() {
        return originalImageUriPath;
    }

    public Map<ScreenConfig, ImageData> getImageData() {
        return imageData;
    }
}
