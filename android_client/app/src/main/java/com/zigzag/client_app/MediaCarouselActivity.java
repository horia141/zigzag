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
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.common.model.Artifact;

import java.util.ArrayList;
import java.util.List;

public class MediaCarouselActivity extends Activity{

    @Nullable DrawerLayout drawerAndMainContent = null;
    @Nullable LinearLayout drawerContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_carousel);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, new ArtifactCarouselFragment())
                .commit();

        drawerAndMainContent = (DrawerLayout) findViewById(R.id.drawer_and_main_content_layout);
        drawerContent = (LinearLayout) findViewById(R.id.drawer_content);

        TextView drawerHomeView = (TextView) findViewById(R.id.drawer_home);
        drawerHomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDrawerHome();
            }
        });
        
        TextView drawerPreferencesView = (TextView) findViewById(R.id.drawer_preferences);
        drawerPreferencesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDrawerPreferences();
            }
        });
        
        TextView drawerTermsAndConditionsView = (TextView) findViewById(R.id.drawer_terms_and_conditions);
        drawerTermsAndConditionsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDrawerTermsAndConditions();
            }
        });

        TextView drawerAboutView = (TextView) findViewById(R.id.drawer_about);
        drawerAboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDrawerAbout();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Controller.getInstance(this).stopEverything();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.media_carousel_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                // shareArtifact(viewPager.getCurrentItem());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void shareArtifact(int artifactIdx) {
//        Artifact artifact = artifacts.get(artifactIdx);
//        String subject = getString(R.string.share_title, artifact.getTitle(),
//                getString(R.string.app_name));
//        String text = getString(R.string.share_body, artifact.getPage_uri());
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("text/plain");
//        i.putExtra(Intent.EXTRA_SUBJECT, subject);
//        i.putExtra(Intent.EXTRA_TEXT, text);
//        startActivity(Intent.createChooser(i, getString(R.string.activity_media_carousel_action_share_title)));
//    }

    private void clickDrawerHome() {
        // Notice that only this is added to the back-stack so we can return to it. It does not
        // apply to the others.
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, new ArtifactCarouselFragment())
                .commit();
        drawerAndMainContent.closeDrawer(drawerContent);
        setTitle(getString(R.string.artifact_carousel_title));
    }

    private void clickDrawerPreferences() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, new PreferencesFragment())
                .commit();
        drawerAndMainContent.closeDrawer(drawerContent);
        setTitle(getString(R.string.preferences_title));
    }

    private void clickDrawerTermsAndConditions() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, new TermsAndConditionsFragment())
                .commit();
        drawerAndMainContent.closeDrawer(drawerContent);
        setTitle(getString(R.string.terms_and_conditions_title));
    }

    private void clickDrawerAbout() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, new AboutFragment())
                .commit();
        drawerAndMainContent.closeDrawer(drawerContent);
        setTitle(getString(R.string.about_title));
    }

    private void clickDrawerShareApp() {
        
    }
}
