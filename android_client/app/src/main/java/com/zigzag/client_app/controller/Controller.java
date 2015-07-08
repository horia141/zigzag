package com.zigzag.client_app.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.zigzag.client_app.R;
import com.zigzag.common.api.NextGenResponse;
import com.zigzag.common.defines.definesConstants;
import com.zigzag.common.model.Artifact;
import com.zigzag.common.model.ArtifactSource;
import com.zigzag.common.model.Generation;
import com.zigzag.common.model.PhotoData;
import com.zigzag.common.model.PhotoDescription;
import com.zigzag.common.model.TileData;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class Controller {

    public interface AllArtifactsListener {
        void onNewArtifacts(List<Artifact> newArtifacts);
        void onError(String errorDescription);
    }

    public interface ArtifactResourcesListener {
        void onResourcesForArtifact(Artifact artifact, int photoDescriptionIdx, int tileOrFrameIdx, Bitmap image);
        void onVideoResourcesForArtifact(Artifact artifact, int photoDescriptionIdx, String localPathToVideo);
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

    private static class ThriftRequest extends Request<ByteBuffer> {

        private final Response.Listener<ByteBuffer> listener;

        public ThriftRequest(String url, Response.Listener<ByteBuffer> listener,
                             Response.ErrorListener errorListener) {
            super(Method.GET, url, errorListener);
            this.listener = listener;
        }

        @Override
        protected void deliverResponse(ByteBuffer serializedThriftData) {
            listener.onResponse(serializedThriftData);
        }

        @Override
        protected Response<ByteBuffer> parseNetworkResponse(NetworkResponse response) {
            ByteBuffer serializedThriftData = ByteBuffer.wrap(response.data);
            return Response.success(serializedThriftData, HttpHeaderParser.parseCacheHeaders(response));
        }
    }

    private static class VideoRequest extends Request<String> {

        private final Response.Listener<String> listener;

        public VideoRequest(String url, Response.Listener<String> listener,
                Response.ErrorListener errorListener) {
            super(Method.GET, url, errorListener);
            this.listener = listener;
        }

        @Override
        protected void deliverResponse(String localPathToVideo) {
            listener.onResponse(localPathToVideo);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            return Response.success("", HttpHeaderParser.parseCacheHeaders(response));
        }
    }

    private static final String NAME = "ZizZag";
    private static final String CACHE_PATH = "volley-cache";
    private static final int CACHE_SIZE = 100;
    private static final Pattern CACHEABLE_FILES_PATTERN = Pattern.compile(definesConstants.CACHEABLE_FILES_PATTERN);
    private static final String REQUESTS_TAG = NAME;
    private static final String API_NEXTGEN_URL_PATTERN = "http://horia141.com:9000/api/v1/nextgen?from=%s&output=thrift";
    private static final int API_NEXTGEN_RETRIES = 10;
    private static final String API_RES_URL_PATTERN = "http://horia141.com:9001/%s";
    private static final int API_RES_RETRIES = 10;
    private static final int IMAGE_CACHE_SIZE = 20;
    private static final String FILEPROVIDER_AUTHORITY = "com.zigzag.client_app.fileprovider";

    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final Map<Long, ArtifactSource> artifactSources;
    private final List<Generation> generations;
    private final List<Artifact> artifacts;
    private final ModelDecoder modelDecoder;
    @Nullable private AllArtifactsListener allArtifactsListener;
    private Map<String, ArtifactResourcesListener> resourcesListeners;

    private Controller(Context context) {
        Cache cache = new PhotoCache(new File(context.getCacheDir(), CACHE_PATH), CACHE_SIZE, CACHEABLE_FILES_PATTERN);
        Network network = new BasicNetwork(new HurlStack());
        this.requestQueue = new RequestQueue(cache, network);
        this.requestQueue.start();
        this.imageLoader = new ImageLoader(this.requestQueue, new ImageCache());
        this.artifactSources = new HashMap<>();
        this.generations = new ArrayList<>();
        this.artifacts = new ArrayList<>();
        this.modelDecoder = new ModelDecoder();
        this.allArtifactsListener = null;
        this.resourcesListeners = new HashMap<>();
    }

    public List<Artifact> getArtifacts() {
        return Collections.unmodifiableList(artifacts);
    }

    public void fetchArtifacts(final AllArtifactsListener listener) {
        allArtifactsListener = listener;

        String apiNextGenUrl = translateNextGenPath(generations);

        ThriftRequest request = new ThriftRequest(apiNextGenUrl,
            new Response.Listener<ByteBuffer>() {
                @Override
                public void onResponse(ByteBuffer serializedThriftData) {
                    // Note: Parsing the generation and updating the model happens regardless of
                    // whether the listener has changed (see below). No point in wasting the
                    // API call.

                    NextGenResponse nextGenResponse;

                    try {
                        // Parse the JSON response and obtain a new generation.
                        nextGenResponse = modelDecoder.decodeNextGenResponse(serializedThriftData);
                    } catch (ModelDecoder.Error e) {
                        listener.onError(e.toString());
                        return;
                    }

                    Generation generation = nextGenResponse.getGeneration();

                    // Update the "model".
                    artifactSources.putAll(generation.getArtifact_sources());
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
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                API_NEXTGEN_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public void deregisterAllArtifactsListener(AllArtifactsListener listener) {
        allArtifactsListener = null;
    }

    public void fetchArtifactResources(final Artifact artifact, final ArtifactResourcesListener listener) {
        resourcesListeners.put(artifact.getPage_uri(), listener);

        // Assert artifact actually exists.
        // Trigger fetch of artifact images.
        for (int ii = 0; ii < artifact.getPhoto_descriptionsSize(); ii++) {
            final int imageIdx = ii;
            final PhotoDescription photoDescription = artifact.getPhoto_descriptions().get(ii);
            final PhotoData photoData = photoDescription.getPhoto_data();
            List<String> uriPathsToFetch = getUriPathsToFetch(photoData);

            for (int jj = 0; jj < uriPathsToFetch.size(); jj++) {
                final int tileOrFrameIdx = jj;
                final String resUrl = translatePhotoPath(uriPathsToFetch.get(jj));

                imageLoader.get(resUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        // Either {@link stopEverything} has been called, or the listener has changed. In either
                        // case there's no point in continuing.
                        if (resourcesListeners.get(artifact.getPage_uri()) == null || resourcesListeners.get(artifact.getPage_uri()) != listener) {
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
                        if (resourcesListeners.get(artifact.getPage_uri()) == null || resourcesListeners.get(artifact.getPage_uri()) != listener) {
                            return;
                        }

                        listener.onError(error.getMessage());
                    }
                });
            }

            if (photoData.isSetVideo_photo_data()) {
                final String resUrl = translatePhotoPath(photoData.getVideo_photo_data().getVideo().getUri_path());
                final VideoRequest request = new VideoRequest(resUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String localPathToVideo) {
                                // Either {@link stopEverything} has been called, or the listener has changed. In either
                                // case there's no point in continuing.
                                if (resourcesListeners.get(artifact.getPage_uri()) == null || resourcesListeners.get(artifact.getPage_uri()) != listener) {
                                    return;
                                }

                                File cacheFile = ((PhotoCache) requestQueue.getCache()).contentFileForKey(resUrl);
                                listener.onVideoResourcesForArtifact(artifact, imageIdx, cacheFile.getAbsolutePath());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Either {@link stopEverything} has been called, or the listener has changed. In either
                                // case there's no point in continuing.
                                if (resourcesListeners.get(artifact.getPage_uri()) == null || resourcesListeners.get(artifact.getPage_uri()) != listener) {
                                    return;
                                }

                                listener.onError(error.getMessage());
                            }
                        });

                request.setTag(REQUESTS_TAG);
                request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        API_RES_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
            }
        }
    }

    public void deregisterArtifactResources(Artifact artifact, ArtifactResourcesListener listener) {
        resourcesListeners.remove(artifact.getPage_uri());
    }

    public void stopEverything() {
        requestQueue.cancelAll(REQUESTS_TAG);
        allArtifactsListener = null;
    }

    @Nullable
    public Artifact getArtifactByPageUri(String pageUri) {
        for (Artifact artifact : artifacts) {
            if (artifact.getPage_uri().equals(pageUri)) {
                return artifact;
            }
        }

        return null;
    }

    public ArtifactSource getSourceForArtifact(Artifact artifact) {
        ArtifactSource artifactSource = artifactSources.get(artifact.getArtifact_source_pk());

        if (artifactSource == null) {
            throw new RuntimeException("Cannot find artifact source! This should not happen");
        }

        return artifactSource;
    }

    public Date getDateForArtifact(Artifact artifact) {
        for (Generation gen : generations) {
            for (Artifact otherArtifact : gen.getArtifacts()) {
                if (otherArtifact.equals(artifact)) {
                    // TODO(horia141): nicer conversion to ms.
                    return new Date((long)gen.getDatetime_ended_ts() * 1000);
                }
            }
        }

        throw new RuntimeException("Cannot find artifact! This should not happen");
    }

    @Nullable
    public Intent getSharingIntentForArtifact(Context context, Artifact artifact) {
        PhotoDescription firstPhotoDescription = artifact.getPhoto_descriptions().get(0);
        String uriPath;
        if (firstPhotoDescription.getPhoto_data().isSetToo_big_photo_data()) {
            throw new IllegalArgumentException("Cannot get path for too big images");
        } else if (firstPhotoDescription.getPhoto_data().isSetImage_photo_data()) {
            uriPath = firstPhotoDescription.getPhoto_data().getImage_photo_data().getTiles().get(0).getUri_path();
        } else {
            uriPath = firstPhotoDescription.getPhoto_data().getVideo_photo_data().getFirst_frame().getUri_path();
        }
        String uri = translatePhotoPath(uriPath);

        File cacheFile = ((PhotoCache) requestQueue.getCache()).contentFileForKey(uri);
        if (!cacheFile.exists()) {
            return null;
        }

        String subject = context.getString(R.string.share_title, artifact.getTitle(),
                context.getString(R.string.app_name));
        String text = context.getString(R.string.share_body, artifact.getTitle(),
                context.getString(R.string.app_name), artifact.getPage_uri());

        Uri contentUri = FileProvider.getUriForFile(context, FILEPROVIDER_AUTHORITY, cacheFile);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        intent.setType(definesConstants.STANDARD_IMAGE_MIMETYPE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        return intent;
    }

    @Nullable
    public Intent getOpenBrowserIntentForArtifact(Context context, Artifact artifact) {
        String url = artifact.getPage_uri();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        return intent;
    }

    @Nullable
    public Intent getOpenBrowserIntentForPhotoDescription(Context context,
            PhotoDescription photoDescription) {
        String url = photoDescription.getSource_uri();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        return intent;
    }

    private static String translateNextGenPath(List<Generation> generations) {
        if (generations.size() == 0) {
            return String.format(API_NEXTGEN_URL_PATTERN, "latest");
        } else {
            final Generation latestGeneration = generations.get(generations.size()-1);
            return String.format(API_NEXTGEN_URL_PATTERN, latestGeneration.getId());
        }
    }

    private static String translatePhotoPath(String photoUrlPath) {
        return String.format(API_RES_URL_PATTERN, photoUrlPath);
    }

    private static List<String> getUriPathsToFetch(PhotoData photoData) {
        if (photoData.isSetToo_big_photo_data()) {
            return new ArrayList<>();
        } else if (photoData.isSetImage_photo_data()) {
            List<String> uriPathsToFetch = new ArrayList<>(photoData.getImage_photo_data().getTilesSize());
            for (TileData tileData : photoData.getImage_photo_data().getTiles()) {
                uriPathsToFetch.add(tileData.getUri_path());
            }
            return uriPathsToFetch;
        } else if (photoData.isSetVideo_photo_data()) {
            List<String> uriPathsToFetch = new ArrayList<>(1);
            uriPathsToFetch.add(photoData.getVideo_photo_data().getFirst_frame().getUri_path());
            return uriPathsToFetch;
        } else {
            throw new RuntimeException("Invalid photoData object");
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
