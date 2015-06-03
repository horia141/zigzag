package com.zigzag.client_app;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.common.model.Artifact;

import java.util.ArrayList;
import java.util.List;

public class ArtifactCarouselFragment extends MediaCarouselFragment
        implements Controller.AllArtifactsListener {

    private static final int START_PREFETCH_BEFORE_END = 3;

    private final List<Artifact> artifacts = new ArrayList<>();
    @Nullable private ViewPager viewPager = null;
    @Nullable private ArtifactsAdapter artifactsAdapter = null;

    public ArtifactCarouselFragment() {
        super(R.string.artifact_carousel_title, R.menu.artifact_carousel_fragment);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareArtifact(viewPager.getCurrentItem());
                return true;
            case R.id.action_open_in_browser:
                openArtifactInBrowser(viewPager.getCurrentItem());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareArtifact(int artifactIdx) {
        Artifact artifact = artifacts.get(artifactIdx);
        String subject = getString(R.string.share_title, artifact.getTitle(),
                getString(R.string.app_name));
        String text = getString(R.string.share_body, artifact.getPage_uri());
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(i, getString(R.string.activity_media_carousel_action_share_title)));
    }

    private void openArtifactInBrowser(int artifactIdx) {
        Artifact artifact = artifacts.get(artifactIdx);
        String url = artifact.getPage_uri();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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
