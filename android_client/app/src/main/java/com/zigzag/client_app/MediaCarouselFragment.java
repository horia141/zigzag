package com.zigzag.client_app;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.ui.ArtifactView;
import com.zigzag.common.model.Artifact;
import com.zigzag.common.model.ArtifactSource;
import com.zigzag.common.model.ImagePhotoData;
import com.zigzag.common.model.PhotoData;
import com.zigzag.common.model.PhotoDescription;

import java.util.Date;

public class MediaCarouselFragment extends Fragment
        implements Controller.ArtifactResourcesListener {

    @Nullable private Artifact artifact;

    public MediaCarouselFragment() {
        artifact = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarousel", "Creating view");

        // Next section assumes that (1) args contains "artifact_id", which is a String representing
        // an artifact URI, and (2) that said URI exists in the system.
        Bundle args = getArguments();
        String artifactPageUri = args.getString("artifact_id");
        artifact = Controller.getInstance(getActivity()).getArtifactByPageUri(artifactPageUri);
        ArtifactSource artifactSource = Controller.getInstance(getActivity())
                .getSourceForArtifact(artifact);
        Date dateAdded = Controller.getInstance(getActivity()).getDateForArtifact(artifact);

        final ArtifactView rootView = (ArtifactView) inflater.inflate(
                R.layout.fragment_media_carousel, container, false);
        rootView.setArtifact(artifact, artifactSource, dateAdded);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("ZigZag/MediaCarousel", "Starting");
        Controller.getInstance(getActivity()).fetchArtifactResources(artifact, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("ZigZag/MediaCarousel", "Stopping");
        Controller.getInstance(getActivity()).deregisterArtifactResources(artifact, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResourcesForArtifact(Artifact artifact, int photoDescriptionIdx,
            int tileOrFrameIdx, Bitmap image) {
        Log.i("ZigZag/MediaCarousel", String.format("ResourcesForArtifact for photo %d and tile %d",
                photoDescriptionIdx, tileOrFrameIdx));

        if (artifact != this.artifact) {
            Log.e("ZigZag/MediaCarousel", "Got out of sync photo update");
            return;
        }

        if (photoDescriptionIdx < 0 || photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            Log.e("ZigZag/MediaCarousel", String.format("Out of bounds photo at %d, but only"
                    + " %d photos", photoDescriptionIdx, artifact.getPhoto_descriptionsSize()));
            return;
        }

        ArtifactView artifactView = (ArtifactView) getView();
        PhotoDescription photoDescription = artifact.getPhoto_descriptions().get(photoDescriptionIdx);
        PhotoData photoData = photoDescription.getPhoto_data();

        if (photoData.isSetToo_big_photo_data()) {
            Log.e("ZigZag/MediaCarousel", "Received image data for a very large image");
        } else if (photoData.isSetImage_photo_data()){
            ImagePhotoData imagePhotoData = photoDescription.getPhoto_data().getImage_photo_data();

            if (tileOrFrameIdx < 0 || tileOrFrameIdx >= imagePhotoData.getTilesSize()) {
                Log.e("ZigZag/MediaCarousel", String.format("Out of bounds tile index at %d,"
                        + " but only %d tiles, for photo %d", tileOrFrameIdx,
                        imagePhotoData.getTilesSize(), photoDescriptionIdx));
                return;
            }

            artifactView.setBitmapForTile(photoDescriptionIdx, tileOrFrameIdx, image);
        } else if (photoData.isSetVideo_photo_data()) {
            if (tileOrFrameIdx != 0) {
                Log.e("ZigZag/MediaCarousel", String.format("Non-zero tile index %d,"
                        + " for photo %d", tileOrFrameIdx, photoDescriptionIdx));
                return;
            }

            artifactView.setBitmapForFirstFrame(photoDescriptionIdx, image);
        }
    }

    @Override
    public void onVideoResourcesForArtifact(Artifact artifact, int photoDescriptionIdx,
            String localPathToVideo) {
        Log.i("ZigZag/MediaCarousel", String.format("VideoResourcesForArtifact for photo %d",
                photoDescriptionIdx));

        if (artifact != this.artifact) {
            Log.e("ZigZag/MediaCarousel", "Got out of sync photo update");
            return;
        }

        if (photoDescriptionIdx < 0 || photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            Log.e("ZigZag/MediaCarousel", String.format("Out of bounds photo at %d, but only"
                    + " %d photos", photoDescriptionIdx, artifact.getPhoto_descriptionsSize()));
            return;
        }

        ArtifactView artifactView = (ArtifactView) getView();
        PhotoDescription photoDescription = artifact.getPhoto_descriptions().get(photoDescriptionIdx);
        PhotoData photoData = photoDescription.getPhoto_data();

        if (!photoData.isSetVideo_photo_data()) {
            Log.e("ZigZag/MediaCarousel", "Received video update for something which is not a video");
            return;
        }

        artifactView.setSourcePathForLocalVideo(photoDescriptionIdx, localPathToVideo);
    }

    @Override
    public void onError(String errorDescription) {
        Log.i("ZigZag/MediaCarousel", String.format("Error %s", errorDescription));
    }

    public static MediaCarouselFragment newInstance(Artifact artifact) {
        MediaCarouselFragment fragment = new MediaCarouselFragment();
        Bundle args = new Bundle();
        args.putString("artifact_id", artifact.getPage_uri());
        fragment.setArguments(args);

        return fragment;
    }
}
