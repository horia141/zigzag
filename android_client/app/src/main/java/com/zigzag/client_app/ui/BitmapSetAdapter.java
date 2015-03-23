package com.zigzag.client_app.ui;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class BitmapSetAdapter<T extends BitmapSetListener> {
    private final List<T> listeners;

    public BitmapSetAdapter() {
        listeners = new ArrayList<>();
    }

    public void addListener(T listener) {
        int idx = listeners.indexOf(listener);

        if (idx != -1) {
            return;
        }

        listeners.add(listener);
    }

    public void removeListener(T listener) {
        listeners.remove(listener);
    }

    public void notifyDataSetChanged() {
        for (T listener : listeners) {
            listener.notifyDataSetChanged();
        }
    }

    @Nullable
    public abstract Bitmap getBitmap(int position);

    public abstract int getCount();
}
