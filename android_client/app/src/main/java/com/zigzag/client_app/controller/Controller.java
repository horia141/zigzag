package com.zigzag.client_app.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Controller {
    public static interface NextArtifactListener {
        void onInitialArtifactData(EntityId id, String title, String sourceName, int numberOfImage);
        void onImageForArtifact(EntityId id, int imageIdx, ImageDescription imageDescription, Bitmap image);
        void onError(String errorDescription);
    }

    private static class ImageCache implements ImageLoader.ImageCache {
        private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(IMAGE_CACHE_SIZE);

        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
        }

        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }
    }

    private static final String REQUESTS_TAG = "ZigZag";
    private static final String API_NEXTGEN_URL_PATTERN = "http://horia141.com:9000/api/v1/nextgen?from=%s";
    private static final int IMAGE_CACHE_SIZE = 20;

    private final Context context;
    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final List<Generation> generations;
    private final List<Artifact> artifacts;
    private int nextArtifact;
    private final ModelDecoder modelDecoder;
    private NextArtifactListener listener;

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

    public void setListener(NextArtifactListener nextArtifactListener) {
        listener = nextArtifactListener;
    }

    public void getNextArtifact(final NextArtifactListener nextArtifactListener) {
        listener = nextArtifactListener;

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
                            nextArtifactListener.onError(e.toString());
                            return;
                        }

                        // Update the "model".
                        generations.add(generation);
                        artifacts.addAll(generation.getArtifacts());

                        if (generation.getArtifacts().size() == 0) {
                            getNextArtifact(nextArtifactListener);
                            return;
                        }

                        // Either {@link stopEverything} has been called, or the listener has
                        // changed. In either case there's no point in continuing.
                        if (listener == null || nextArtifactListener != listener) {
                            return;
                        }

                        doNextArtifact(nextArtifactListener);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Either {@link stopEverything} has been called, or the listener has
                        // changed. In either case there's no point in continuing.
                        if (listener == null || nextArtifactListener != listener) {
                            return;
                        }

                        nextArtifactListener.onError(error.getMessage());
                    }
                });

            request.setTag(REQUESTS_TAG);
            requestQueue.add(request);
        } else {
            doNextArtifact(nextArtifactListener);
        }
    }

    public void stopEverything() {
        requestQueue.cancelAll(REQUESTS_TAG);
        listener = null;
    }

    private void doNextArtifact(final NextArtifactListener nextArtifactListener) {
        // Either {@link stopEverything} has been called, or the listener has changed. In either
        // case there's no point in continuing.
        if (listener == null || nextArtifactListener != listener) {
            return;
        }

        // Get the next artifact to show.
        final Artifact lastArtifact = artifacts.get(nextArtifact);
        nextArtifact = nextArtifact + 1;

        // Change the view to reflect new changes.
        nextArtifactListener.onInitialArtifactData(lastArtifact.getId(), lastArtifact.getTitle(),
                lastArtifact.getArtifactSource().getName(), lastArtifact.getImagesDescription().size());

        // Trigger fetch of artifact images.
        for (int ii = 0; ii < lastArtifact.getImagesDescription().size(); ii++) {
            final int imageIdx = ii;
            final ImageDescription imageDescription = lastArtifact.getImagesDescription().get(ii);
            // TODO(horia141): these should be cancelled if that's the case.
            imageLoader.get(imageDescription.getUrlPath(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    // Either {@link stopEverything} has been called, or the listener has changed. In either
                    // case there's no point in continuing.
                    if (listener == null || nextArtifactListener != listener) {
                        return;
                    }

                    // From the ImageListener's sources: this can only happen when isImmediate is
                    // true, and the image could not be found in the cache.
                    if (response.getBitmap() == null) {
                        return;
                    }

                    nextArtifactListener.onImageForArtifact(lastArtifact.getId(), imageIdx,
                            imageDescription, response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Either {@link stopEverything} has been called, or the listener has changed. In either
                    // case there's no point in continuing.
                    if (listener == null || nextArtifactListener != listener) {
                        return;
                    }

                    nextArtifactListener.onError(error.getMessage());
                }
            });
        }
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
