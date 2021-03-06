package com.zigzag.client_app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zigzag.client_app.R;
import com.zigzag.client_app.controller.Controller;
import com.zigzag.common.model.Artifact;
import com.zigzag.common.model.ArtifactSource;
import com.zigzag.common.model.PhotoDescription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArtifactView extends ScrollView implements UiPhotoHolder {

    public interface OnLongClickListener {
        void onLongClick(int photoDescriptionIdx);
    }

    private enum State {
        CREATED,
        ARTIFACT_SET
    }

    private static final SimpleDateFormat DATE_ADDED_FORMATTER = new SimpleDateFormat("d MMM yyyy", Locale.US);

    private State state;
    @Nullable private Artifact artifact;
    @Nullable private ArtifactSource artifactSource;
    @Nullable private Date dateAdded;
    @Nullable private OnLongClickListener onLongClickListener;
    private final LinearLayout contentView;
    private final Button debugButton;
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
        onLongClickListener = null;

        inflate(getContext(), R.layout.artifact_view, this);
        contentView = (LinearLayout) findViewById(R.id.content);
        debugButton = (Button) findViewById(R.id.debug_button);
        sourceNameView = (TextView) findViewById(R.id.source_name);
        dateAddedView = (TextView) findViewById(R.id.date_added);
        titleView = (TextView) findViewById(R.id.title);
        photoDescriptionViews = new ArrayList<>();

        if (Controller.getInstance(context).isDebug()) {
            debugButton.setVisibility(VISIBLE);
            debugButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDebugButtonClicked(view);
                }
            });
        }
    }

    public void setArtifact(Artifact newArtifact, ArtifactSource newArtifactSource,
            Date newDateAdded, boolean firstArtifact) {
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

        final ArtifactView parent = this;

        for (int ii = 0; ii < newArtifact.getPhoto_descriptionsSize(); ii++) {
            PhotoDescription photoDescription = newArtifact.getPhoto_descriptions().get(ii);
            final PhotoDescriptionView photoDescriptionView = (PhotoDescriptionView) inflater.inflate(
                    R.layout.artifact_view_photo_description, contentView, false);
            photoDescriptionView.setPhotoDescription(photoDescription);
            photoDescriptionViews.add(photoDescriptionView);
            // Add before the last element, since the last element is the debug button.
            contentView.addView(photoDescriptionView, contentView.getChildCount() - 1);

            // VideoPhotoViews need some extra care. They should only start playing when we scroll
            // to them. The ArtifactView controls when this happens. Also, if this is the first
            // artifact, we'll always play it, since otherwise there is no scroll to trigger it.
            if (photoDescription.getPhoto_data().isSetVideo_photo_data()) {
                if (firstArtifact) {
                    photoDescriptionView.enableVideo();
                }

                photoDescriptionView.getViewTreeObserver().addOnScrollChangedListener(
                        new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        Rect scrollBounds = new Rect();
                        parent.getHitRect(scrollBounds);
                        if (photoDescriptionView.getLocalVisibleRect(scrollBounds)) {
                            photoDescriptionView.enableVideo();
                        } else {
                            photoDescriptionView.disableVideo();
                        }
                    }
                });
            }

            final int photoDescriptionIdx = ii;
            photoDescriptionView.setOnLongClickListener(new PhotoDescriptionView.OnLongClickListener() {
                @Override
                public void onLongClick() {
                    onPhotoDescriptionLongClick(photoDescriptionIdx);
                }
            });
        }

        // TODO(horia141): why this code? why here? It has to do with VideoViews. They steal the
        // focus or something like that. When an artifact view becomes visible, if there are videos,
        // the screen will become focused on the last of them. This is usually a bad thing to do, as
        // well as being non-uniform. This code seems to fix it. Rather, a scrollTo anytime after
        // the video views have done their thing. This location was good enough. It does not get
        // called that many times, so it is not a performance problem.
        // Also, why here? It is the lowest point where we can address it. It is a "ui" thing and
        // should be dealt with here.
        final ArtifactView capturedThis = this;
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                capturedThis.scrollTo(0, 0);
            }
        });

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setBitmapForTile(int photoDescriptionIdx, int tileIdx, Bitmap bitmap) {
        if (state != State.ARTIFACT_SET) {
            throw new IllegalStateException("Not in bitmap setting state");
        }

        if (photoDescriptionIdx < 0 || photoDescriptionIdx >= artifact.getPhoto_descriptionsSize()) {
            throw new IndexOutOfBoundsException("Photo description index out of bounds");
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
            throw new IndexOutOfBoundsException("Photo description index out of bounds");
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
            throw new IndexOutOfBoundsException("Photo description index out of bounds");
        }

        // Update view components.
        photoDescriptionViews.get(photoDescriptionIdx)
                .setSourcePathForLocalVideo(sourcePathForLocalVideo);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    @Override
    public void clearPhotoResources() {
        if (state != State.ARTIFACT_SET) {
            throw new IllegalStateException("Not in resources clearing state");
        }

        for (PhotoDescriptionView view : photoDescriptionViews) {
            view.clearPhotoResources();
        }
    }

    public void setOnLongClickListener(@Nullable OnLongClickListener newOnLongClickListener) {
        onLongClickListener = newOnLongClickListener;
    }

    public void onDebugButtonClicked(View v) {
        if (v != debugButton) {
            return;
        }

        Intent openInMailIntent = Controller.getInstance(getContext())
                .getOpenMailIntentForArtifact(getContext(), artifact);

        if (openInMailIntent == null) {
            Toast.makeText(getContext(), getContext().getString(R.string.cannot_open_in_mail),
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        getContext().startActivity(openInMailIntent);
    }

    private void onPhotoDescriptionLongClick(int photoDescriptionIdx) {
        if (onLongClickListener == null) {
            return;
        }

        onLongClickListener.onLongClick(photoDescriptionIdx);
    }
}
