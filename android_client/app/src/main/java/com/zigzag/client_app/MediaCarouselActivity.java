package com.zigzag.client_app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.common.model.Artifact;

import java.util.ArrayList;
import java.util.List;


public class MediaCarouselActivity extends Activity implements Controller.AllArtifactsListener {
    private static final int START_PREFETCH_BEFORE_END = 3;

    private final List<Artifact> artifacts = new ArrayList<>();
    @Nullable private ViewPager viewPager = null;
    @Nullable private ArtifactsAdapter artifactsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselActivity", "Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_carousel);

        artifactsAdapter = new ArtifactsAdapter(getFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.artifacts_pager);
        viewPager.setAdapter(artifactsAdapter);

        getActionBar().setTitle(getString(R.string.activity_media_carousel_action_title));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.media_carousel_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
//            case R.id.action_save:
//                saveArtifact(viewPager.getCurrentItem());
//                return true;
            case R.id.action_share:
                shareArtifact(viewPager.getCurrentItem());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveArtifact(int artifactId) {
    }

    private void shareArtifact(int artifactId) {
        if (artifactId > artifacts.size()) {
            Log.e("ZigZag/MediaCarouselActivity", String.format("Artifact id %d too big (%d)", artifactId, artifacts.size()));
        }
        Artifact artifact = artifacts.get(artifactId);
        String subject = String.format("%s via %s", artifact.getTitle(), getString(R.string.app_name));
        String text = String.format("%s via %s %s", artifact.getTitle(), getString(R.string.app_name), artifact.getPage_uri());
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(i, getString(R.string.activity_media_carousel_action_share_title)));
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
