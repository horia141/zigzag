package com.zigzag.client_app.ui;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class BitmapSetAdapter {
    public static class TileInfo {
        private final Bitmap bitmap;
        private final int width;
        private final int height;

        public TileInfo(Bitmap bitmap, int width, int height) {
            this.bitmap = bitmap;
            this.width = width;
            this.height = height;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    private final List<BitmapSetListener> listeners;

    public BitmapSetAdapter() {
        listeners = new ArrayList<>();
    }

    public void addListener(BitmapSetListener listener) {
        int idx = listeners.indexOf(listener);

        if (idx != -1) {
            return;
        }

        listeners.add(listener);
    }

    public void removeListener(BitmapSetListener listener) {
        listeners.remove(listener);
    }

    public void notifyDataSetChanged() {
        for (BitmapSetListener listener : listeners) {
            listener.notifyDataSetChanged();
        }
    }

    @Nullable
    public abstract TileInfo getTileInfo(int position);

    public abstract int getCount();
}
