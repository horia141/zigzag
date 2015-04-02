package com.zigzag.client_app.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
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
import com.zigzag.client_app.model.PhotoData;
import com.zigzag.client_app.model.ImageDescription;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Controller {

    public static interface AllArtifactsListener {
        void onNewArtifacts(List<Artifact> newArtifacts);
        void onError(String errorDescription);
    }

    public static interface ArtifactResourcesListener {
        void onResourcesForArtifact(Artifact artifact, int imageIdx, int tileOrFrameIdx, Bitmap image);
        void onError(String errorDescription);
    }

    private static class ImageCache implements ImageLoader.ImageCache {
        private final LruCache<String, Bitmap> cache = new LruCache<>(IMAGE_CACHE_SIZE);

        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
            Log.i("ZigZag/Cache", String.format("Got image %dx%d", bitmap.getWidth(), bitmap.getHeight()));
        }

        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }
    }

    private static final String REQUESTS_TAG = "ZigZag";
    private static final String API_NEXTGEN_URL_PATTERN = "http://horia141.com:9000/api/v1/nextgen?from=%s";
    private static final String API_RES_URL_PATTERN = "http://horia141.com:9001/%s";
    private static final int IMAGE_CACHE_SIZE = 20;

    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final List<Generation> generations;
    private final List<Artifact> artifacts;
    private final ModelDecoder modelDecoder;
    @Nullable private AllArtifactsListener allArtifactsListener;
    private Map<EntityId, ArtifactResourcesListener> resourcesListeners;

    private Controller(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.imageLoader = new ImageLoader(this.requestQueue, new ImageCache());
        this.generations = new ArrayList<>();
        this.artifacts = new ArrayList<>();
        this.modelDecoder = new ModelDecoder();
        this.allArtifactsListener = null;
        this.resourcesListeners = new HashMap<>();
    }

    public void fetchArtifacts(final AllArtifactsListener listener) {
        allArtifactsListener = listener;

        String apiNextGenUrl;
        if (generations.size() == 0) {
            apiNextGenUrl = String.format(API_NEXTGEN_URL_PATTERN, "latest");
        } else {
            final Generation latestGeneration = generations.get(generations.size()-1);
            apiNextGenUrl = String.format(API_NEXTGEN_URL_PATTERN, latestGeneration.getId().getId());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiNextGenUrl, null,
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
                            listener.onError(e.toString());
                            return;
                        }

                        // Update the "model".
                        generations.add(generation);
                        artifacts.addAll(generation.getArtifacts());

                        if (generation.getArtifacts().size() == 0) {
                            fetchArtifacts(listener);
                            return;
                        }

                        // Either {@link stopEverything} has been called, or the listener has
                        // changed. In either case there's no point in continuing.
                        if (allArtifactsListener == null || allArtifactsListener != listener) {
                            return;
                        }

                        listener.onNewArtifacts(generation.getArtifacts());
                    }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Either {@link stopEverything} has been called, or the listener has
                    // changed. In either case there's no point in continuing.
                    if (allArtifactsListener == null || allArtifactsListener != listener) {
                        return;
                    }

                    listener.onError(error.getMessage());
                }
            });

        request.setTag(REQUESTS_TAG);
        requestQueue.add(request);
    }

    public void deregisterAllArtifactsListener(AllArtifactsListener listener) {
        allArtifactsListener = null;
    }

    public void fetchArtifactResources(final Artifact artifact, final ArtifactResourcesListener listener) {
        resourcesListeners.put(artifact.getId(), listener);

        // Assert artifact actually exists.
        // Trigger fetch of artifact images.
        for (int ii = 0; ii < artifact.getImagesDescription().size(); ii++) {
            final int imageIdx = ii;
            final ImageDescription imageDescription = artifact.getImagesDescription().get(ii);
            final PhotoData photoData = imageDescription.getBestMatchingImageData();
            List<String> uriPathsToFetch = photoData.getUriPathsToFetch();

            for (int jj = 0; jj < uriPathsToFetch.size(); jj++) {
                final int tileOrFrameIdx = jj;
                String resUrl = getResUrl(uriPathsToFetch.get(jj));

                imageLoader.get(resUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        // Either {@link stopEverything} has been called, or the listener has changed. In either
                        // case there's no point in continuing.
                        if (resourcesListeners.get(artifact.getId()) == null || resourcesListeners.get(artifact.getId()) != listener) {
                            return;
                        }

                        // From the ImageListener's sources: this can only happen when isImmediate is
                        // true, and the image could not be found in the cache.
                        if (response.getBitmap() == null) {
                            return;
                        }

                        listener.onResourcesForArtifact(artifact, imageIdx, tileOrFrameIdx, response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Either {@link stopEverything} has been called, or the listener has changed. In either
                        // case there's no point in continuing.
                        if (resourcesListeners.get(artifact.getId()) == null || resourcesListeners.get(artifact.getId()) != listener) {
                            return;
                        }

                        listener.onError(error.getMessage());
                    }
                });
            }
        }
    }

    public void deregisterArtifactResources(Artifact artifact, ArtifactResourcesListener listener) {
        resourcesListeners.remove(artifact.getId());
    }

    public void stopEverything() {
        requestQueue.cancelAll(REQUESTS_TAG);
        allArtifactsListener = null;
    }

    @Nullable
    public Artifact getArtifactById(EntityId id) {
        for (Artifact artifact : artifacts) {
            if (artifact.getId().equals(id)) {
                return artifact;
            }
        }

        return null;
    }

    private static String getResUrl(String urlPath) {
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
