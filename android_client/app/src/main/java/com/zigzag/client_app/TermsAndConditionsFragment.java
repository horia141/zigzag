package com.zigzag.client_app;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zigzag.client_app.R;

public class TermsAndConditionsFragment extends MediaCarouselFragment {

    public TermsAndConditionsFragment() {
        super(R.string.terms_and_conditions_title, R.menu.terms_and_conditions_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
    }
}
