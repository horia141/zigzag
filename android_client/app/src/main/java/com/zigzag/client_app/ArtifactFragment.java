package com.zigzag.client_app;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zigzag.client_app.controller.Controller;
import com.zigzag.client_app.ui.ArtifactView;
import com.zigzag.client_app.ui.UiPhotoHolder;
import com.zigzag.common.model.Artifact;
import com.zigzag.common.model.ArtifactSource;
import com.zigzag.common.model.ImagePhotoData;
import com.zigzag.common.model.PhotoData;
import com.zigzag.common.model.PhotoDescription;

import java.util.Date;

public class ArtifactFragment extends Fragment
        implements Controller.ArtifactResourcesListener, UiPhotoHolder {

    private static final String ARTIFACT_LOCAL_ID = "artifact_local_id";
    private static final String ARTIFACT_PAGE_URI = "artifact_page_uri";

    @Nullable private Artifact artifact;
    private boolean photoResourcesAttached;

    public ArtifactFragment() {
        artifact = null;
        photoResourcesAttached = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Next section assumes that (1) args contains "artifact_id", which is a String representing
        // an artifact URI, and (2) that said URI exists in the system.
        Bundle args = getArguments();
        int artifactLocalId = args.getInt(ARTIFACT_LOCAL_ID);
        String artifactPageUri = args.getString(ARTIFACT_PAGE_URI);
        artifact = Controller.getInstance(getActivity()).getArtifactByPageUri(artifactPageUri);

        if (artifact == null) {
            // This should not normally happen, since we usually get here through a normal flow of
            // {@link StartUpActivity} -> {@link MediaCarouselActivity} ->
            // {@link ArtifactCarouselFragment} -> {@link ArtifactFragment}. However, sometimes the
            // system seems to hang on to this fragment, and starts it up without the proper flow.
            // In that case, the {@link Controller} will be fresh. We need to reload it. Or better
            // yet, start a fresh flow.
            Intent startUpIntent = new Intent(getActivity(), StartUpActivity.class);
            startActivity(startUpIntent);
            return (ArtifactView) inflater.inflate(
                    R.layout.fragment_artifact, container, false);
        }
        ArtifactSource artifactSource = Controller.getInstance(getActivity())
                .getSourceForArtifact(artifact);
        Date dateAdded = Controller.getInstance(getActivity()).getDateForArtifact(artifact);

        boolean firstArtifact = artifactLocalId == 0;
        final ArtifactView rootView = (ArtifactView) inflater.inflate(
                R.layout.fragment_artifact, container, false);
        rootView.setArtifact(artifact, artifactSource, dateAdded, firstArtifact);
        rootView.setOnLongClickListener(new ArtifactView.OnLongClickListener() {
            @Override
            public void onLongClick(int photoDescriptionIdx) {
                onPhotoDescriptionLongClick(photoDescriptionIdx);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (artifact == null) {
            return;
        }

        attachPhotoResources();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (artifact == null) {
            return;
        }

        Controller.getInstance(getActivity()).deregisterArtifactResources(artifact, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResourcesForArtifact(Artifact artifact, int photoDescriptionIdx,
            int tileOrFrameIdx, Bitmap image) {

        if (artifact != this.artifact) {
            Log.e("ZigZag/MediaCarouselF", "Got out of sync photo update");
            return;
        }

        if (photoDescriptionIdx < 0 || photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            Log.e("ZigZag/MediaCarouselF", String.format("Out of bounds photo at %d, but only"
                    + " %d photos", photoDescriptionIdx, artifact.getPhoto_descriptionsSize()));
            return;
        }

        ArtifactView artifactView = (ArtifactView) getView();
        PhotoDescription photoDescription = artifact.getPhoto_descriptions().get(photoDescriptionIdx);
        PhotoData photoData = photoDescription.getPhoto_data();

        if (photoData.isSetToo_big_photo_data()) {
            Log.e("ZigZag/MediaCarouselF", "Received image data for a very large image");
        } else if (photoData.isSetImage_photo_data()){
            ImagePhotoData imagePhotoData = photoDescription.getPhoto_data().getImage_photo_data();

            if (tileOrFrameIdx < 0 || tileOrFrameIdx >= imagePhotoData.getTilesSize()) {
                Log.e("ZigZag/MediaCarouselF", String.format("Out of bounds tile index at %d,"
                        + " but only %d tiles, for photo %d", tileOrFrameIdx,
                        imagePhotoData.getTilesSize(), photoDescriptionIdx));
                return;
            }

            artifactView.setBitmapForTile(photoDescriptionIdx, tileOrFrameIdx, image);
        } else if (photoData.isSetVideo_photo_data()) {
            if (tileOrFrameIdx != 0) {
                Log.e("ZigZag/MediaCarouselF", String.format("Non-zero tile index %d,"
                        + " for photo %d", tileOrFrameIdx, photoDescriptionIdx));
                return;
            }

            artifactView.setBitmapForFirstFrame(photoDescriptionIdx, image);
        }
    }

    @Override
    public void onVideoResourcesForArtifact(Artifact artifact, int photoDescriptionIdx,
            String localPathToVideo) {

        if (artifact != this.artifact) {
            Log.e("ZigZag/MediaCarouselF", "Got out of sync photo update");
            return;
        }

        if (photoDescriptionIdx < 0 || photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            Log.e("ZigZag/MediaCarouselF", String.format("Out of bounds photo at %d, but only"
                    + " %d photos", photoDescriptionIdx, artifact.getPhoto_descriptionsSize()));
            return;
        }

        ArtifactView artifactView = (ArtifactView) getView();
        PhotoDescription photoDescription = artifact.getPhoto_descriptions().get(photoDescriptionIdx);
        PhotoData photoData = photoDescription.getPhoto_data();

        if (!photoData.isSetVideo_photo_data()) {
            Log.e("ZigZag/MediaCarouselF", "Received video update for something which is not a video");
            return;
        }

        artifactView.setSourcePathForLocalVideo(photoDescriptionIdx, localPathToVideo);
    }

    @Override
    public void onError(String errorDescription) {
        Log.e("ZigZag/MediaCarouselF", String.format("Error %s", errorDescription));
    }

    public void attachPhotoResources() {
        if (photoResourcesAttached) {
            return;
        }

        Controller.getInstance(getActivity()).fetchArtifactResources(artifact, this);
        photoResourcesAttached = true;
    }

    @Override
    public void clearPhotoResources() {
        ((ArtifactView)getView()).clearPhotoResources();
        photoResourcesAttached = false;
    }

    private void onPhotoDescriptionLongClick(int idx) {
        PhotoDescription photoDescription = artifact.getPhoto_descriptions().get(idx);
        Intent openInBrowserIntent = Controller.getInstance(getActivity())
                .getOpenBrowserIntentForPhotoDescription(getActivity(), photoDescription);

        if (openInBrowserIntent == null) {
            Toast.makeText(getActivity(), getString(R.string.cannot_open_in_browser), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        startActivity(openInBrowserIntent);
    }

    public static ArtifactFragment newInstance(Artifact artifact, int artifactLocalId) {
        ArtifactFragment fragment = new ArtifactFragment();
        Bundle args = new Bundle();
        args.putInt(ARTIFACT_LOCAL_ID, artifactLocalId);
        args.putString(ARTIFACT_PAGE_URI, artifact.getPage_uri());
        fragment.setArguments(args);

        return fragment;
    }
}
