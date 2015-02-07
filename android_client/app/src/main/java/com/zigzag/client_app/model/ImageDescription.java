package com.zigzag.client_app.model;

public class ImageDescription {
    private final String subtitle;
    private final String description;
    private final String urlPath;

    public ImageDescription(String subtitle, String description, String urlPath) {
        this.subtitle = subtitle;
        this.description = description;
        this.urlPath = urlPath;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
