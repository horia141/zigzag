package com.zigzag.client_app.controller;

import com.zigzag.client_app.model.Artifact;
import com.zigzag.client_app.model.ArtifactSource;
import com.zigzag.client_app.model.EntityId;
import com.zigzag.client_app.model.Generation;
import com.zigzag.client_app.model.ImagePhotoData;
import com.zigzag.client_app.model.PhotoData;
import com.zigzag.client_app.model.ImageDescription;
import com.zigzag.client_app.model.ScreenConfig;
import com.zigzag.client_app.model.TileData;
import com.zigzag.client_app.model.TooBigPhotoData;
import com.zigzag.client_app.model.VideoPhotoData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ModelDecoder {
    public static class Error extends Exception {
        private static final long serialVersionUID = 1L;

        public Error(String message) {
            super(message);
        }
    }

    private final DateFormat dateFormatter = DateFormat.getDateTimeInstance(
            DateFormat.LONG, DateFormat.LONG, Locale.US);


    public ModelDecoder() {
        super();
    }

    public Generation decodeGeneration(JSONObject generationJson) throws Error {
        try {
            String idString = generationJson.getString("id");
            EntityId id = new EntityId(idString);

            String timeAddedAsString = generationJson.getString("time_added");
            Date timeAdded = dateFormatter.parse(timeAddedAsString);

            String timeClosedAsString = generationJson.getString("time_closed");
            Date timeClosed = dateFormatter.parse(timeClosedAsString);

            JSONObject screenConfigsJson = generationJson.getJSONObject("screen_configs");
            Map<String, ScreenConfig> screenConfigs = new HashMap<>();

            for (Iterator<String> ii = screenConfigsJson.keys(); ii.hasNext();) {
                String screenConfigKey = ii.next();
                ScreenConfig screenConfig = decodeScreenConfig(screenConfigKey, screenConfigsJson.getJSONObject(screenConfigKey));
                screenConfigs.put(screenConfigKey, screenConfig);
            }

            JSONObject artifactSourcesJson = generationJson.getJSONObject("artifact_sources");
            Map<EntityId, ArtifactSource> artifactSources = new HashMap<>();

            for (Iterator<String> ii = artifactSourcesJson.keys(); ii.hasNext();) {
                String artifactSourceId = ii.next();
                ArtifactSource artifactSource = decodeArtifactSource(
                        artifactSourcesJson.getJSONObject(artifactSourceId));
                artifactSources.put(new EntityId(artifactSourceId), artifactSource);
            }

            JSONArray artifactsJson = generationJson.getJSONArray("artifacts");
            List<Artifact> artifacts = new ArrayList<>();

            for (int ii = 0; ii < artifactsJson.length(); ii++) {
                artifacts.add(decodeArtifact(artifactsJson.getJSONObject(ii), artifactSources, screenConfigs));
            }

            Generation generation = new Generation(id, timeAdded, timeClosed, artifacts);

            return generation;
        } catch (JSONException e) {
            throw new Error(e.toString());
        } catch (ParseException e) {
            throw new Error(e.toString());
        }
    }

    private ScreenConfig decodeScreenConfig(String screenConfigKey, JSONObject screenConfigJson) throws Error {
        try {
            int width = screenConfigJson.getInt("width");

            ScreenConfig screenConfig = new ScreenConfig(screenConfigKey, width);

            return screenConfig;
        } catch (JSONException e) {
            throw new Error(e.toString());
        }
    }

    private ArtifactSource decodeArtifactSource(JSONObject artifactSourceJson) throws Error {
        try {
            String idString = artifactSourceJson.getString("id");
            EntityId id = new EntityId(idString);

            String startPageUrl = artifactSourceJson.getString("start_page_url");

            String name = artifactSourceJson.getString("name");

            String timeAddedAsString = artifactSourceJson.getString("time_added");
            Date timeAdded = dateFormatter.parse(timeAddedAsString);

            ArtifactSource artifactSource = new ArtifactSource(id, startPageUrl, name, timeAdded);

            return artifactSource;
        } catch (JSONException e) {
            throw new Error(e.toString());
        } catch (ParseException e) {
            throw new Error(e.toString());
        }
    }

    private Artifact decodeArtifact(JSONObject artifactJson, Map<EntityId, ArtifactSource> artifactSources, Map<String, ScreenConfig> screenConfigs) throws Error {
        try {
            String idString = artifactJson.getString("id");
            EntityId id = new EntityId(idString);

            String pageUrl = artifactJson.getString("page_url");

            String artifactSourceIdString = artifactJson.getString("artifact_source_id");
            EntityId artifactSourceId = new EntityId(artifactSourceIdString);

            ArtifactSource artifactSource = artifactSources.get(artifactSourceId);

            if (artifactSource == null ) {
                throw new Error(String.format("Invalid JSON response - cannot find source with " +
                        "given id %s", artifactSourceId.getId()));
            }

            String title = artifactJson.getString("title");

            JSONArray imagesDescriptionJson = artifactJson.getJSONArray("images_description");
            List<ImageDescription> imagesDescription = new ArrayList<ImageDescription>();

            for (int ii = 0; ii < imagesDescriptionJson.length(); ii++) {
                imagesDescription.add(decodeImageDescription(imagesDescriptionJson.getJSONObject(ii), screenConfigs));
            }

            Artifact artifact = new Artifact(id, pageUrl, artifactSource, title, imagesDescription);

            return artifact;
        } catch (JSONException e) {
            throw new Error(e.toString());
        }
    }

    private ImageDescription decodeImageDescription(JSONObject imageDescriptionJson, Map<String, ScreenConfig> screenConfigs) throws Error {
        try {
            String subtitle = imageDescriptionJson.getString("subtitle");

            String description = imageDescriptionJson.getString("description");

            String sourceUri = imageDescriptionJson.getString("source_uri");

            String originalImageUriPath = imageDescriptionJson.getString("original_photo_uri_path");

            JSONObject imageDataJson = imageDescriptionJson.getJSONObject("photo_data");
            Map<ScreenConfig, PhotoData> imageData = new HashMap<ScreenConfig, PhotoData>();

            for (Iterator<String> ii = imageDataJson.keys(); ii.hasNext();) {
                String screenConfigKey = ii.next();
                ScreenConfig screenConfig = screenConfigs.get(screenConfigKey);
                PhotoData photoDataForScreenConfig = decodePhotoData(imageDataJson.getJSONObject(screenConfigKey));
                imageData.put(screenConfig, photoDataForScreenConfig);
            }

            ImageDescription imageDescription = new ImageDescription(subtitle, description, sourceUri, originalImageUriPath, imageData);

            return imageDescription;
        } catch (JSONException e) {
            throw new Error(e.toString());
        }
    }

    private PhotoData decodePhotoData(JSONObject imageDataJson) throws Error {
        try {
            String type = imageDataJson.getString("type");

            if (type.equals("too-large")) {
                return new TooBigPhotoData();
            } else if (type.equals("image")) {
                JSONObject fullImageDescJson = imageDataJson.getJSONObject("full_image_desc");
                TileData fullImageDesc = decodeTileData(fullImageDescJson);

                JSONArray tilesDescJson = imageDataJson.getJSONArray("tiles_desc");
                List<TileData> tilesDesc = new ArrayList<TileData>();

                for (int ii = 0; ii < tilesDescJson.length(); ii++) {
                    JSONObject tileDescJson = tilesDescJson.getJSONObject(ii);
                    TileData tileDesc = decodeTileData(tileDescJson);
                    tilesDesc.add(tileDesc);
                }

                PhotoData photoData = new ImagePhotoData(fullImageDesc, tilesDesc);

                return photoData;
            } else if (type.equals("video")) {
                JSONObject firstFrameDescJson = imageDataJson.getJSONObject("first_frame_desc");
                TileData firstFrameDesc = decodeTileData(firstFrameDescJson);

                JSONObject videoDescJson = imageDataJson.getJSONObject("video_desc");
                TileData videoDesc = decodeTileData(videoDescJson);

                int timeBetweenFramesMs = imageDataJson.getInt("time_between_frames_ms");

                int framerate = imageDataJson.getInt("framerate");

                PhotoData photoData = new VideoPhotoData(firstFrameDesc, videoDesc, timeBetweenFramesMs, framerate);

                return photoData;
            } else {
                throw new Error("Unrecognized image data type");
            }
        } catch (JSONException e) {
            throw new Error(e.toString());
        }
    }

    private TileData decodeTileData(JSONObject tileDataJson) throws Error {
        try {
            int width = tileDataJson.getInt("width");

            int height = tileDataJson.getInt("height");

            String uriPath = tileDataJson.getString("uri_path");

            TileData tileData = new TileData(width, height, uriPath);

            return tileData;
        } catch (JSONException e) {
            throw new Error(e.toString());
        }
    }
}
