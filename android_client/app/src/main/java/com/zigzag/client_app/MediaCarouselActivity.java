package com.zigzag.client_app;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MediaCarouselActivity extends ActionBarActivity {
    ArtifactsAdapter artifactsAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselActivity", "Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_carousel);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new MediaCarouselFragment())
//                    .commit();
//        }

        artifactsAdapter = new ArtifactsAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.artifacts_pager);
        viewPager.setAdapter(artifactsAdapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private static class ArtifactsAdapter extends FragmentStatePagerAdapter {
        public ArtifactsAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new MediaCarouselFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Default Title";
        }
    }
}
