package com.zigzag.client_app;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;

public class MediaCarouselActivity extends Activity{

    @Nullable DrawerLayout drawerAndMainContent = null;
    @Nullable LinearLayout drawerContent = null;
    @Nullable ActionBarDrawerToggle drawerToggle = null;
    @Nullable TextView drawerArtifactCarouselView = null;
    @Nullable TextView drawerPreferencesView = null;
    @Nullable TextView drawerTermsAndConditionsView = null;
    @Nullable TextView drawerAboutView = null;
    @Nullable TextView drawerShareAppView = null;

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

        drawerToggle = new ActionBarDrawerToggle(this, drawerAndMainContent,
                R.string.drawer_action_open, R.string.drawer_action_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerAndMainContent.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerArtifactCarouselView = (TextView) findViewById(R.id.drawer_artifact_carousel);
        drawerArtifactCarouselView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDrawerHome();
            }
        });
        
        drawerPreferencesView = (TextView) findViewById(R.id.drawer_preferences);
        drawerPreferencesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDrawerPreferences();
            }
        });
        
        drawerTermsAndConditionsView = (TextView) findViewById(R.id.drawer_terms_and_conditions);
        drawerTermsAndConditionsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { clickDrawerTermsAndConditions(); }
        });

        drawerAboutView = (TextView) findViewById(R.id.drawer_about);
        drawerAboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDrawerAbout();
            }
        });

        drawerShareAppView = (TextView) findViewById(R.id.drawer_share_app);
        drawerShareAppView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDrawerShareApp();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onStop() {
        super.onStop();
        Controller.getInstance(this).stopEverything();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerAndMainContent.isDrawerOpen(drawerContent);
        for (int ii = 0; ii < menu.size(); ii++) {
            menu.getItem(ii).setVisible(!drawerOpen);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clickDrawerHome() {
        // Notice that only this is added to the back-stack so we can return to it. It does not
        // apply to the others.
        MediaCarouselFragment fragment = new ArtifactCarouselFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
        drawerAndMainContent.closeDrawer(drawerContent);
        setTitle(getString(fragment.getTitleResId()));
        drawerArtifactCarouselView.setBackgroundResource(R.color.action_bar_blue);
        drawerArtifactCarouselView.setTextAppearance(this, R.style.MediaCarouselDrawerSelected);
        drawerTermsAndConditionsView.setBackgroundResource(R.color.background_white);
        drawerTermsAndConditionsView.setTextAppearance(this, R.style.MediaCarouselDrawerUnselected);
        drawerAboutView.setBackgroundResource(R.color.background_white);
        drawerAboutView.setTextAppearance(this, R.style.MediaCarouselDrawerUnselected);
        invalidateOptionsMenu();
    }

    private void clickDrawerPreferences() {
    }

    private void clickDrawerTermsAndConditions() {
        MediaCarouselFragment fragment = new TermsAndConditionsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
        drawerAndMainContent.closeDrawer(drawerContent);
        setTitle(getString(fragment.getTitleResId()));
        drawerArtifactCarouselView.setBackgroundResource(R.color.background_white);
        drawerArtifactCarouselView.setTextAppearance(this, R.style.MediaCarouselDrawerUnselected);
        drawerTermsAndConditionsView.setBackgroundResource(R.color.action_bar_blue);
        drawerTermsAndConditionsView.setTextAppearance(this, R.style.MediaCarouselDrawerSelected);
        drawerAboutView.setBackgroundResource(R.color.background_white);
        drawerAboutView.setTextAppearance(this, R.style.MediaCarouselDrawerUnselected);
        invalidateOptionsMenu();
    }

    private void clickDrawerAbout() {
        MediaCarouselFragment fragment = new AboutFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
        drawerAndMainContent.closeDrawer(drawerContent);
        setTitle(getString(fragment.getTitleResId()));
        drawerArtifactCarouselView.setBackgroundResource(R.color.background_white);
        drawerArtifactCarouselView.setTextAppearance(this, R.style.MediaCarouselDrawerUnselected);
        drawerTermsAndConditionsView.setBackgroundResource(R.color.background_white);
        drawerTermsAndConditionsView.setTextAppearance(this, R.style.MediaCarouselDrawerUnselected);
        drawerAboutView.setBackgroundResource(R.color.action_bar_blue);
        drawerAboutView.setTextAppearance(this, R.style.MediaCarouselDrawerSelected);
        invalidateOptionsMenu();
    }

    private void clickDrawerShareApp() {
    }
}
