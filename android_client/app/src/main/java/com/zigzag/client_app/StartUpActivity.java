package com.zigzag.client_app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.common.model.Artifact;

import java.util.List;

public class StartUpActivity extends Activity
        implements Controller.AllArtifactsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
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
    }

    @Override
    public void onNewArtifacts(List<Artifact> newArtifacts) {
        Intent intent = new Intent(this, MediaCarouselActivity.class);
        startActivity(intent);
    }

    @Override
    public void onError(String errorDescription) {
        Log.e("ZigZag/MediaCarouselA", String.format("Error %s", errorDescription));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
