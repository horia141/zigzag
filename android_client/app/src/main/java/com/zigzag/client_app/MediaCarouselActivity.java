package com.zigzag.client_app;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.model.Artifact;

import java.util.ArrayList;
import java.util.List;


public class MediaCarouselActivity extends ActionBarActivity implements Controller.AllArtifactsListener {
    private static final int START_PREFETCH_BEFORE_END = 3;

    private final List<Artifact> artifacts = new ArrayList<>();
    @Nullable private ArtifactsAdapter artifactsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselActivity", "Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_carousel);

        artifactsAdapter = new ArtifactsAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.artifacts_pager);
        viewPager.setAdapter(artifactsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Controller.getInstance(this).fetchArtifacts(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Controller.getInstance(this).deregisterAllArtifactsListener(this);
        Controller.getInstance(this).stopEverything();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onNewArtifacts(List<Artifact> newArtifacts) {
        artifacts.addAll(newArtifacts);
        artifactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorDescription) {
        Log.e("ZigZag/MediaCarouselActivity", String.format("Error %s", errorDescription));
    }

    private class ArtifactsAdapter extends FragmentStatePagerAdapter {
        public ArtifactsAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            if (i + START_PREFETCH_BEFORE_END >= artifacts.size()) {
                Controller.getInstance(MediaCarouselActivity.this).fetchArtifacts(MediaCarouselActivity.this);
            }

            Artifact artifact = artifacts.get(i);
            Fragment fragment = MediaCarouselFragment.newInstance(artifact);
            return fragment;
        }

        @Override
        public int getCount() {
            return artifacts.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Default Title";
        }
    }
}
