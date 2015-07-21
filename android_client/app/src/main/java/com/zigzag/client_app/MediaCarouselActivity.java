package com.zigzag.client_app;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class MediaCarouselActivity extends Activity{

    private static class DrawerItemDescriptor {
        final Class<? extends MediaCarouselFragment> fragmentClass;
        final int textViewId;
        final boolean active;

        DrawerItemDescriptor(Class<? extends MediaCarouselFragment> newFragmentClass, int newTextViewId,
                boolean newActive) {
            fragmentClass = newFragmentClass;
            textViewId = newTextViewId;
            active = newActive;
        }
    }

    @Nullable DrawerLayout drawerAndMainContent = null;
    @Nullable LinearLayout drawerContent = null;
    @Nullable ActionBarDrawerToggle drawerToggle = null;

    private static final List<DrawerItemDescriptor> drawerItemDescriptors;
    static {
        drawerItemDescriptors = new ArrayList<>();
        drawerItemDescriptors.add(new DrawerItemDescriptor(
                ArtifactCarouselFragment.class, R.id.drawer_artifact_carousel, true));
        drawerItemDescriptors.add(new DrawerItemDescriptor(
                PreferencesFragment.class, R.id.drawer_preferences, false));
        drawerItemDescriptors.add(new DrawerItemDescriptor(
                TermsAndConditionsFragment.class, R.id.drawer_terms_and_conditions, false));
        drawerItemDescriptors.add(new DrawerItemDescriptor(
                AboutFragment.class, R.id.drawer_about, false));
        drawerItemDescriptors.add(new DrawerItemDescriptor(
                AboutFragment.class, R.id.drawer_share_app, false));
    }

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

        for (final DrawerItemDescriptor descriptor : drawerItemDescriptors) {
            if (!descriptor.active) {
                continue;
            }

            TextView descriptorView = (TextView) findViewById(descriptor.textViewId);
            descriptorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDrawerItemClick(descriptor);
                }
            });
        }
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

    private void onDrawerItemClick(DrawerItemDescriptor clickedDescriptor) {
        for (DrawerItemDescriptor descriptor : drawerItemDescriptors) {
            if (!descriptor.active) {
                continue;
            }

            TextView descriptorView = (TextView) findViewById(descriptor.textViewId);
            descriptorView.setBackgroundResource(R.color.background_white);
            descriptorView.setTextAppearance(this, R.style.MediaCarouselDrawerUnselected);
        }

        try {
            MediaCarouselFragment fragment = clickedDescriptor.fragmentClass.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();
            setTitle(fragment.getTitleResId());
            invalidateOptionsMenu();
            TextView clickedDescriptorView = (TextView) findViewById(clickedDescriptor.textViewId);
            clickedDescriptorView.setBackgroundResource(R.color.action_bar_blue);
            clickedDescriptorView.setTextAppearance(this, R.style.MediaCarouselDrawerSelected);
            drawerAndMainContent.closeDrawer(drawerContent);
        } catch (InstantiationException e) {
            Log.e("ZigZag/MediaCarouselA", "Cannot create fragment");
        } catch (IllegalAccessException e) {
            Log.e("ZigZag/MediaCarouselA", "Cannot create fragment");
        }
    }
}
