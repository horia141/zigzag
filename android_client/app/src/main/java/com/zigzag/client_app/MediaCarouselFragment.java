package com.zigzag.client_app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public abstract class MediaCarouselFragment extends Fragment {

    private final int titleResId;
    private final int menuResId;

    public MediaCarouselFragment(int newTitleResId, int newMenuResId) {
        titleResId = newTitleResId;
        menuResId = newMenuResId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(menuResId, menu);
    }

    public final int getTitleResId() {
        return titleResId;
    }

    public final int getMenuResId() {
        return menuResId;
    }
}
