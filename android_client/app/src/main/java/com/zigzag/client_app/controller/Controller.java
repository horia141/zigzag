package com.zigzag.client_app.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.zigzag.common.api.NextGenResponse;
import com.zigzag.common.model.Artifact;
import com.zigzag.common.model.ArtifactSource;
import com.zigzag.common.model.Generation;
import com.zigzag.common.model.PhotoData;
import com.zigzag.common.model.PhotoDescription;
import com.zigzag.common.model.TileData;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Controller {

    public static interface AllArtifactsListener {
        void onNewArtifacts(List<Artifact> newArtifacts);
        void onError(String errorDescription);
    }

    public static interface ArtifactResourcesListener {
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
        private final PhotoDescription photoDescription;
        private final FileSystem fileSystem;

        public VideoRequest(String url, PhotoDescription photoDescription, FileSystem fileSystem,
                            Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.GET, url, errorListener);
            this.listener = listener;
            this.photoDescription = photoDescription;
            this.fileSystem = fileSystem;
        }

        @Override
        protected void deliverResponse(String localPathToVideo) {
            listener.onResponse(localPathToVideo);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String path = Uri.parse(getUrl()).getPath();
            File video = fileSystem.register(photoDescription, path);

            if (!video.exists()) {
                try {
                    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(video));
                    outputStream.write(response.data);
                    outputStream.close();
                } catch(IOException error){
                    Log.e("ZigZag/Controller", "Error processing video", error);
                    return Response.error(new VolleyError("Error saving video", error));
                }
            }

            return Response.success(video.getAbsolutePath(), HttpHeaderParser.parseCacheHeaders(response));
        }
    }

    private static final String NAME = "ZizZag";
    private static final String CACHE_PATH = "volley-cache";
    private static final int CACHE_SIZE_MB = 10 * 1024 * 1024;
    private static final String REQUESTS_TAG = NAME;
    private static final String API_NEXTGEN_URL_PATTERN = "http://horia141.com:9000/api/v1/nextgen?from=%s&output=thrift";
    private static final String API_RES_URL_PATTERN = "http://horia141.com:9001/%s";
    private static final int IMAGE_CACHE_SIZE = 20;

    private final FileSystem fileSystem;
    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final Map<Long, ArtifactSource> artifactSources;
    private final List<Generation> generations;
    private final List<Artifact> artifacts;
    private final ModelDecoder modelDecoder;
    @Nullable private AllArtifactsListener allArtifactsListener;
    private Map<String, ArtifactResourcesListener> resourcesListeners;

    private Controller(Context context) {
        this.fileSystem = new FileSystem(NAME);
        Cache cache = new DiskBasedCache(new File(context.getCacheDir(), CACHE_PATH), CACHE_SIZE_MB);
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

        String apiNextGenUrl;
        if (generations.size() == 0) {
            apiNextGenUrl = String.format(API_NEXTGEN_URL_PATTERN, "latest");
        } else {
            final Generation latestGeneration = generations.get(generations.size()-1);
            apiNextGenUrl = String.format(API_NEXTGEN_URL_PATTERN, latestGeneration.getId());
        }

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
                final String resUrl = translateImagePath(uriPathsToFetch.get(jj));

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

                        Log.i("ZigZag/Here", ((DiskBasedCache) requestQueue.getCache()).getFileForKey(resUrl).getAbsolutePath());

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
                final String resUrl = translateVideoPath(photoData.getVideo_photo_data().getVideo().getUri_path());
                final VideoRequest request = new VideoRequest(resUrl, photoDescription, fileSystem,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String localPathToVideo) {
                                listener.onVideoResourcesForArtifact(artifact, imageIdx, localPathToVideo);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                listener.onError(error.getMessage());
                            }
                        });

                request.setTag(REQUESTS_TAG);
                requestQueue.add(request);
            }
        }
    }

    public void deregisterArtifactResources(Artifact artifact, ArtifactResourcesListener listener) {
        resourcesListeners.remove(artifact.getPage_uri());

        // We are also going to use the fact that at most one listener for an artifact exists, and
        // if this is called, that listener is being destroyed. So we can remove the locally saved
        // videos for the video artifacts.
        for (PhotoDescription photoDescription : artifact.getPhoto_descriptions()) {
            if (!photoDescription.getPhoto_data().isSetVideo_photo_data()) {
                continue;
            }

            fileSystem.release(photoDescription);
        }
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

    public File getCacheFileForPhotoDescription(PhotoDescription photoDescription) {
        String desiredPath = null;
        if (photoDescription.getPhoto_data().isSetToo_big_photo_data()) {
            throw new IllegalArgumentException("Cannot get path for too big images");
        } else if (photoDescription.getPhoto_data().isSetImage_photo_data()) {
            desiredPath = photoDescription.getPhoto_data().getImage_photo_data().getTiles().get(0).getUri_path();
        } else {
            desiredPath = photoDescription.getPhoto_data().getVideo_photo_data().getFirst_frame().getUri_path();
        }
        return ((DiskBasedCache) requestQueue.getCache()).getFileForKey(desiredPath);
    }

    private static String translateImagePath(String urlPath) {
        return String.format(API_RES_URL_PATTERN, urlPath);
    }

    private static String translateVideoPath(String urlPath) {
        return String.format(API_RES_URL_PATTERN, urlPath);
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
