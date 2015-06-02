package com.zigzag.client_app;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.common.model.Artifact;

import java.util.ArrayList;
import java.util.List;

public class ArtifactCarouselFragment extends Fragment
        implements Controller.AllArtifactsListener {

    private static final int START_PREFETCH_BEFORE_END = 3;

    private final List<Artifact> artifacts = new ArrayList<>();
    @Nullable private ViewPager viewPager = null;
    @Nullable private ArtifactsAdapter artifactsAdapter = null;

    public ArtifactCarouselFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artifact_carousel, container, false);

        artifactsAdapter = new ArtifactsAdapter(getFragmentManager());
        viewPager = (ViewPager) rootView.findViewById(R.id.artifacts_pager);
        viewPager.setAdapter(artifactsAdapter);

        // We know we have some artifacts, because we usually end up here because of a
        // StartUpActivity invoking us. That happens when there is some data.
        artifacts.addAll(Controller.getInstance(getActivity()).getArtifacts());
        artifactsAdapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Controller.getInstance(getActivity()).fetchArtifacts(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Controller.getInstance(getActivity()).deregisterAllArtifactsListener(this);
    }


    @Override
    public void onNewArtifacts(List<Artifact> newArtifacts) {
        artifacts.addAll(newArtifacts);
        artifactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorDescription) {
        Log.e("ZigZag/MediaCarouselA", String.format("Error %s", errorDescription));
    }

    private class ArtifactsAdapter extends FragmentStatePagerAdapter {
        public ArtifactsAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int artifactIdx) {
            if (artifactIdx + START_PREFETCH_BEFORE_END >= artifacts.size()) {
                Controller.getInstance(ArtifactCarouselFragment.this.getActivity())
                        .fetchArtifacts(ArtifactCarouselFragment.this);
            }

            Artifact artifact = artifacts.get(artifactIdx);
            return ArtifactFragment.newInstance(artifact);
        }

        @Override
        public int getCount() {
            return artifacts.size();
        }

        @Override
        public CharSequence getPageTitle(int artifactIdx) {
            Artifact artifact = artifacts.get(artifactIdx);
            return artifact.getTitle();
        }
    }
}
