package com.zigzag.client_app.model;

public class ScreenConfig {
    private String key;
    private int width;

    public ScreenConfig(String key, int width) {
        this.key = key;
        this.width = width;
    }

    public String getKey() {
        return key;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ScreenConfig)) {
            return false;
        }

        ScreenConfig that = (ScreenConfig) o;

        if (width != that.width) {
            return false;
        }

        if (!key.equals(that.key)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + width;
        return result;
    }
}
