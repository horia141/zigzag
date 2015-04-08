package com.zigzag.client_app.model;

import java.util.Map;

public class ImageDescription {
    private final String subtitle;
    private final String description;
    private final String sourceUri;
    private final String originalImageUriPath;
    private final Map<ScreenConfig, PhotoData> imageData;

    public ImageDescription(String subtitle, String description, String sourceUri,
            String originalImageUriPath, Map<ScreenConfig, PhotoData> imageData) {
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

    public Map<ScreenConfig, PhotoData> getImageData() {
        return imageData;
    }

    public PhotoData getBestMatchingImageData() {
        ScreenConfig k1 = new ScreenConfig("800", 800);
        ScreenConfig k2 = new ScreenConfig("480", 480);
        PhotoData v1 = imageData.get(k1);

        if (v1 == null) {
            return imageData.get(k2);
        } else {
            return v1;
        }
    }
}
