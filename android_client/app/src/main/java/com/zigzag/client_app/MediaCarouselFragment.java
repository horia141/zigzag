package com.zigzag.client_app;

import android.app.Fragment;

public abstract class MediaCarouselFragment extends Fragment {

    private int titleResId;

    public MediaCarouselFragment(int newTitleResId) {
        titleResId = newTitleResId;
    }

    public final int getTitleResId() {
        return titleResId;
    }
}
