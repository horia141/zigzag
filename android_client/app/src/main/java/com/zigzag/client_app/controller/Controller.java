package com.zigzag.client_app.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zigzag.client_app.model.Artifact;
import com.zigzag.client_app.model.EntityId;
import com.zigzag.client_app.model.Generation;
import com.zigzag.client_app.model.ImageDescription;
import com.zigzag.client_app.model.ImageSetImageData;
import com.zigzag.client_app.model.ScreenConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Controller {

    public static interface ArtifactListener {
        void onInitialArtifactData(EntityId id, String title, String pageUrl, String sourceName, int numberOfImage);
        void onImageForArtifact(EntityId id, int imageIdx, ImageDescription imageDescription, Bitmap image);
        void onError(String errorDescription);
    }

    private static class ImageCache implements ImageLoader.ImageCache {
        private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(IMAGE_CACHE_SIZE);

        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
            Log.i("ZigZag/Cache", String.format("Got image %dx%d", bitmap.getWidth(), bitmap.getHeight()));
        }

        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }
    }

    private static final String REQUESTS_TAG = "ZigZag";
    private static final String API_NEXTGEN_URL_PATTERN = "http://192.168.1.35:9000/api/v1/nextgen?from=%s";
    private static final String API_RES_URL_PATTERN = "http://192.168.1.35:9001/%s";
    private static final int IMAGE_CACHE_SIZE = 20;
    public static final int STANDARD_IMAGE_WIDTH = 960;
    public static final int ROUNDED_CORNER_SIZE = 12;

    private final Context context;
    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final List<Generation> generations;
    private final List<Artifact> artifacts;
    private int nextArtifact;
    private final ModelDecoder modelDecoder;
    private ArtifactListener listener;

    private Controller(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        this.imageLoader = new ImageLoader(this.requestQueue, new ImageCache());
        this.generations = new ArrayList<Generation>();
        this.artifacts = new ArrayList<Artifact>();
        this.nextArtifact = 0;
        this.modelDecoder = new ModelDecoder();
        this.listener = null;
    }

    public void setListener(ArtifactListener artifactListener) {
        listener = artifactListener;
    }

    public void getNextArtifact(final ArtifactListener artifactListener) {
        listener = artifactListener;

        if (generations.size() == 0 || nextArtifact == artifacts.size()) {
            String apiNextgenUrl;
            if (generations.size() == 0) {
                apiNextgenUrl = String.format(API_NEXTGEN_URL_PATTERN, "latest");
            } else {
                final Generation latestGeneration = generations.get(generations.size()-1);
                apiNextgenUrl = String.format(API_NEXTGEN_URL_PATTERN, latestGeneration.getId().getId());
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiNextgenUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Note: Parsing the generation and updating the model happens regardless of
                        // whether the listener has changed (see below). No point in wasting the
                        // API call.

                        Generation generation;

                        try {
                            // Parse the JSON response and obtain a new generation.
                            generation = modelDecoder.decodeGeneration(response);
                        } catch (ModelDecoder.Error e) {
                            artifactListener.onError(e.toString());
                            return;
                        }

                        // Update the "model".
                        generations.add(generation);
                        artifacts.addAll(generation.getArtifacts());

                        if (generation.getArtifacts().size() == 0) {
                            getNextArtifact(artifactListener);
                            return;
                        }

                        // Either {@link stopEverything} has been called, or the listener has
                        // changed. In either case there's no point in continuing.
                        if (listener == null || artifactListener != listener) {
                            return;
                        }

                        doNextArtifact(artifactListener);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Either {@link stopEverything} has been called, or the listener has
                        // changed. In either case there's no point in continuing.
                        if (listener == null || artifactListener != listener) {
                            return;
                        }

                        artifactListener.onError(error.getMessage());
                    }
                });

            request.setTag(REQUESTS_TAG);
            requestQueue.add(request);
        } else {
            doNextArtifact(artifactListener);
        }
    }

    private void doNextArtifact(ArtifactListener artifactListener) {
        // Either {@link stopEverything} has been called, or the listener has changed. In either
        // case there's no point in continuing.
        if (listener == null || artifactListener != listener) {
            return;
        }

        // Get the next artifact to show.
        final Artifact lastArtifact = artifacts.get(nextArtifact);
        nextArtifact = nextArtifact + 1;
        handleArtifact(artifactListener, lastArtifact);
    }

    public void getPrevArtifact(final ArtifactListener artifactListener) {
        listener = artifactListener;

        if (nextArtifact == 0) {
            return;
        }

        nextArtifact = nextArtifact - 1;
        final Artifact lastArtifact = artifacts.get(nextArtifact);
        handleArtifact(artifactListener, lastArtifact);
    }

    public void stopEverything() {
        requestQueue.cancelAll(REQUESTS_TAG);
        listener = null;
    }

    private void handleArtifact(final ArtifactListener artifactListener, final Artifact lastArtifact) {
        // Change the view to reflect new changes.
        artifactListener.onInitialArtifactData(lastArtifact.getId(), lastArtifact.getTitle(),
                lastArtifact.getPageUrl(), lastArtifact.getArtifactSource().getName(),
                lastArtifact.getImagesDescription().size());

        // Trigger fetch of artifact images.
        for (int ii = 0; ii < lastArtifact.getImagesDescription().size(); ii++) {
            final int imageIdx = ii;
            final ImageDescription imageDescription = lastArtifact.getImagesDescription().get(ii);
            // TODO(horia141): these should be cancelled if that's the case.
            String resUrl = getResUrl(((ImageSetImageData)imageDescription.getImageData().get(new ScreenConfig("800", 800))).getFullImageDesc().getUriPath());
            imageLoader.get(resUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    // Either {@link stopEverything} has been called, or the listener has changed. In either
                    // case there's no point in continuing.
                    if (listener == null || artifactListener != listener) {
                        return;
                    }

                    // From the ImageListener's sources: this can only happen when isImmediate is
                    // true, and the image could not be found in the cache.
                    if (response.getBitmap() == null) {
                        return;
                    }

                    artifactListener.onImageForArtifact(lastArtifact.getId(), imageIdx,
                            imageDescription, response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Either {@link stopEverything} has been called, or the listener has changed. In either
                    // case there's no point in continuing.
                    if (listener == null || artifactListener != listener) {
                        return;
                    }

                    artifactListener.onError(error.getMessage());
                }
            });
        }
    }

    public static String getResUrl(String urlPath) {
        return String.format(API_RES_URL_PATTERN, urlPath);
    }

    // Singleton interface.

    private static Controller instance = null;

    public static synchronized Controller getInstance(Context context) {
        if (instance == null) {
            instance = new Controller(context);
        }

        return instance;
    }
}
