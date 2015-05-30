package com.zigzag.client_app.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zigzag.client_app.R;
import com.zigzag.common.model.Artifact;
import com.zigzag.common.model.ArtifactSource;
import com.zigzag.common.model.PhotoDescription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArtifactView extends ScrollView {


//    final PhotoDescriptionView capturedPhotoDescriptionView = photoDescriptionView;
//    ViewTreeObserver viewTreeObserver = photoDescriptionView.getViewTreeObserver();
//
//    if (info.viewTreeObserverHash != Math.abs(viewTreeObserver.hashCode())) {
//        info.viewTreeObserverHash = Math.abs(viewTreeObserver.hashCode());
//        photoDescriptionView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                Rect scrollBounds = new Rect();
//                parent.getHitRect(scrollBounds);
//                if (capturedPhotoDescriptionView.getLocalVisibleRect(scrollBounds)) {
//                    // capturedPhotoDescriptionView.enableVideo();
//                } else {
//                    // capturedPhotoDescriptionView.disableVideo();
//                }
//            }
//        });
//    }


    private enum State {
        CREATED,
        ARTIFACT_SET
    }

    private static final SimpleDateFormat DATE_ADDED_FORMATTER = new SimpleDateFormat("d MMM yyyy", Locale.US);

    private State state;
    @Nullable private Artifact artifact;
    @Nullable private ArtifactSource artifactSource;
    @Nullable private Date dateAdded;
    private final LinearLayout contentView;
    private final TextView sourceNameView;
    private final TextView dateAddedView;
    private final TextView titleView;
    private final List<PhotoDescriptionView> photoDescriptionViews;

    public ArtifactView(Context context) {
        this(context, null);
    }

    public ArtifactView(Context context, AttributeSet attrs) {
        super(context, attrs);

        state = State.CREATED;
        artifact = null;
        artifactSource = null;
        dateAdded = null;

        inflate(getContext(), R.layout.artifact_view, this);
        contentView = (LinearLayout) findViewById(R.id.content);
        sourceNameView = (TextView) findViewById(R.id.source_name);
        dateAddedView = (TextView) findViewById(R.id.date_added);
        titleView = (TextView) findViewById(R.id.title);
        photoDescriptionViews = new ArrayList<>();
    }

    public void setArtifact(Artifact newArtifact, ArtifactSource newArtifactSource, Date newDateAdded) {
        if (state != State.CREATED) {
            throw new IllegalStateException("Not in artifact setting state");
        }

        // Update state.
        state = State.ARTIFACT_SET;
        artifact = newArtifact;
        artifactSource = newArtifactSource;
        dateAdded = newDateAdded;

        // Update view components.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        sourceNameView.setText(newArtifactSource.getArtifact_title_name());
        dateAddedView.setText(DATE_ADDED_FORMATTER.format(newDateAdded));
        titleView.setText(newArtifact.getTitle());

        for (PhotoDescription photoDescription : newArtifact.getPhoto_descriptions()) {
            PhotoDescriptionView photoDescriptionView = (PhotoDescriptionView) inflater.inflate(
                    R.layout.artifact_view_photo_description, contentView, false);
            photoDescriptionView.setPhotoDescription(photoDescription);
            photoDescriptionViews.add(photoDescriptionView);
            contentView.addView(photoDescriptionView);
        }

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setBitmapForTile(int photoDescriptionIdx, int tileIdx, Bitmap bitmap) {
        if (state != State.ARTIFACT_SET) {
            throw new IllegalStateException("Not in bitmap setting state");
        }

        if (photoDescriptionIdx < 0 || photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            throw new IllegalArgumentException("Photo description index out of bounds");
        }

        // Update view components.
        photoDescriptionViews.get(photoDescriptionIdx).setBitmapForTile(tileIdx, bitmap);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setBitmapForFirstFrame(int photoDescriptionIdx, Bitmap bitmap) {
        if (state != State.ARTIFACT_SET) {
            throw new IllegalStateException("Not in bitmap setting state");
        }

        if (photoDescriptionIdx < 0 || photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            throw new IllegalArgumentException("Photo description index out of bounds");
        }

        // Update view components.
        photoDescriptionViews.get(photoDescriptionIdx).setBitmapForFirstFrame(bitmap);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setSourcePathForLocalVideo(int photoDescriptionIdx, String sourcePathForLocalVideo) {
        if (state != State.ARTIFACT_SET) {
            throw new IllegalStateException("Not in source path setting state");
        }

        if (photoDescriptionIdx < 0 || photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            throw new IllegalArgumentException("Photo description index out of bounds");
        }

        // Update view components.
        photoDescriptionViews.get(photoDescriptionIdx)
                .setSourcePathForLocalVideo(sourcePathForLocalVideo);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }
}
