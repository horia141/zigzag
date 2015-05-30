package com.zigzag.client_app;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.ui.ArtifactView;
import com.zigzag.client_app.ui.PhotoDescriptionView;
import com.zigzag.common.model.Artifact;
import com.zigzag.common.model.ArtifactSource;
import com.zigzag.common.model.ImagePhotoData;
import com.zigzag.common.model.PhotoData;
import com.zigzag.common.model.PhotoDescription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediaCarouselFragment extends Fragment implements Controller.ArtifactResourcesListener {

    @Nullable private Artifact artifact;
    @Nullable private ArtifactSource artifactSource;
    @Nullable private Date dateAdded;

    public MediaCarouselFragment() {
        artifact = null;
        artifactSource = null;
        dateAdded = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ZigZag/MediaCarouselFragment", "Creating view");

        Bundle args = getArguments();
        String artifactPageUri = args.getString("artifact_id");
        artifact = Controller.getInstance(getActivity()).getArtifactByPageUri(artifactPageUri);
        artifactSource = Controller.getInstance(getActivity()).getSourceForArtifact(artifact);
        dateAdded = Controller.getInstance(getActivity()).getDateForArtifact(artifact);

        final ArtifactView rootView = (ArtifactView) inflater.inflate(
                R.layout.fragment_media_carousel, container, false);
        rootView.setArtifact(artifact, artifactSource, dateAdded);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Controller.getInstance(getActivity()).fetchArtifactResources(artifact, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Controller.getInstance(getActivity()).deregisterArtifactResources(artifact, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResourcesForArtifact(Artifact artifact, int photoDescriptionIdx, int tileOrFrameIdx, Bitmap image) {
        Log.i("ZigZag", String.format("Got %d/%d", photoDescriptionIdx, tileOrFrameIdx));

        if (artifact != this.artifact) {
            Log.e("ZigZag/MediaCarouselFragment", "Got out of sync image update");
            return;
        }

        if (photoDescriptionIdx < 0) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got a negative image index %d", photoDescriptionIdx));
            return;
        } else if (photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got an image for list index %d, but list" +
                    " has size %d", photoDescriptionIdx, artifact.getPhoto_descriptionsSize()));
            return;
        }

        ArtifactView artifactView = (ArtifactView) getView();
        PhotoDescription photoDescription = artifact.getPhoto_descriptions().get(photoDescriptionIdx);
        PhotoData photoData = photoDescription.getPhoto_data();

        if (photoData.isSetToo_big_photo_data()) {
            Log.e("ZigZag/MediaCarouselFragment", "Received image data for a very large image");
        } else if (photoData.isSetImage_photo_data()){
            if (tileOrFrameIdx < 0) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a negative tile index %d", tileOrFrameIdx));
                return;
            } else if (tileOrFrameIdx >= photoDescription.getPhoto_data().getImage_photo_data().getTilesSize()) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Out of bounds tile index with tile %d but it" +
                        " has size %d", tileOrFrameIdx, photoData.getImage_photo_data().getTilesSize()));
                return;
            }

            artifactView.setBitmapForTile(photoDescriptionIdx, tileOrFrameIdx, image);
        } else if (photoData.isSetVideo_photo_data()) {
            if (tileOrFrameIdx != 0) {
                Log.e("ZigZag/MediaCarouselFragment", String.format("Got a non-zero tile index %d", tileOrFrameIdx));
                return;
            }

            artifactView.setBitmapForFirstFrame(photoDescriptionIdx, image);
        }
    }

    @Override
    public void onVideoResourcesForArtifact(Artifact artifact, int photoDescriptionIdx, String localPathToVideo) {
        Log.i("ZigZag", String.format("Got Video %d", photoDescriptionIdx));

        if (artifact != this.artifact) {
            Log.e("ZigZag/MediaCarouselFragment", "Got out of sync video update");
            return;
        }

        if (photoDescriptionIdx < 0) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got a negative image index %d", photoDescriptionIdx));
            return;
        } else if (photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            Log.e("ZigZag/MediaCarouselFragment", String.format("Got an image for list index %d, but list" +
                    " has size %d", photoDescriptionIdx, artifact.getPhoto_descriptionsSize()));
            return;
        }

        ArtifactView artifactView = (ArtifactView) getView();
        PhotoDescription photoDescription = artifact.getPhoto_descriptions().get(photoDescriptionIdx);
        PhotoData photoData = photoDescription.getPhoto_data();

        if (!photoData.isSetVideo_photo_data()) {
            Log.e("ZigZag/MediaCarouselFragment", "Received video update for something which is not a video");
            return;
        }

        artifactView.setSourcePathForLocalVideo(photoDescriptionIdx, localPathToVideo);
    }

    @Override
    public void onError(String errorDescription) {
        Log.i("ZigZag", String.format("Error %s", errorDescription));
    }

    public static MediaCarouselFragment newInstance(Artifact artifact) {
        MediaCarouselFragment fragment = new MediaCarouselFragment();
        Bundle args = new Bundle();
        args.putString("artifact_id", artifact.getPage_uri());
        fragment.setArguments(args);

        return fragment;
    }
}
