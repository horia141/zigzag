package com.zigzag.client_app;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zigzag.client_app.R;

public class PreferencesFragment extends MediaCarouselFragment {

    public PreferencesFragment() {
        super(R.string.hamburger_preferences_title, R.menu.preferences_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preferences, container, false);
    }
}
