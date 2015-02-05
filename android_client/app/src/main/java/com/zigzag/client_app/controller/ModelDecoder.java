package com.zigzag.client_app.controller;

import com.zigzag.client_app.model.Artifact;
import com.zigzag.client_app.model.ArtifactSource;
import com.zigzag.client_app.model.EntityId;
import com.zigzag.client_app.model.Generation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

            JSONObject artifactSourcesJson = generationJson.getJSONObject("artifact_sources");
            Map<EntityId, ArtifactSource> artifactSources = new HashMap<EntityId, ArtifactSource>();

            for (Iterator<String> ii = artifactSourcesJson.keys(); ii.hasNext();) {
                String artifactSourceId = ii.next();
                ArtifactSource artifactSource = decodeArtifactSource(
                        artifactSourcesJson.getJSONObject(artifactSourceId));
                artifactSources.put(new EntityId(artifactSourceId), artifactSource);
            }

            JSONArray artifactsJson = generationJson.getJSONArray("artifacts");
            List<Artifact> artifacts = new ArrayList<Artifact>();

            for (int ii = 0; ii < artifactsJson.length(); ii++) {
                artifacts.add(decodeArtifact(artifactsJson.getJSONObject(ii), artifactSources));
            }

            Generation generation = new Generation(id, timeAdded, timeClosed, artifacts);

            return generation;
        } catch (JSONException e) {
            throw new Error(e.toString());
        } catch (ParseException e) {
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

    private Artifact decodeArtifact(JSONObject artifactJson, Map<EntityId, ArtifactSource> artifactSources) throws Error {
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

            JSONArray imageUrlPathsJson = artifactJson.getJSONArray("image_url_paths");
            List<String> imageUrlPaths = new ArrayList<String>(imageUrlPathsJson.length());

            for (int ii = 0; ii < imageUrlPathsJson.length(); ii++) {
                imageUrlPaths.add(imageUrlPathsJson.getString(ii));
            }

            Artifact artifact = new Artifact(id, pageUrl, artifactSource, title, imageUrlPaths);

            return artifact;
        } catch (JSONException e) {
            throw new Error(e.toString());
        }
    }
}
