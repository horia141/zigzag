package com.zigzag.client_app.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zigzag.client_app.R;
import com.zigzag.common.model.PhotoDescription;

public class PhotoDescriptionView extends LinearLayout {

    private enum State {
        CREATED,
        PHOTO_DESCRIPTION_SET
    }

    private State state;
    @Nullable private PhotoDescription photoDescription;
    private final ImagePhotoView imagePhotoView;
    private final VideoPhotoView videoPhotoView;
    private final TextView subtitleView;
    private final TextView descriptionView;

    public PhotoDescriptionView(Context context) {
        this(context, null);
    }

    public PhotoDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        state = State.CREATED;
        photoDescription = null;

        setOrientation(VERTICAL);

        inflate(getContext(), R.layout.photo_description_view, this);
        imagePhotoView = (ImagePhotoView) findViewById(R.id.image_photo);
        videoPhotoView = (VideoPhotoView) findViewById(R.id.video_photo);
        subtitleView = (TextView) findViewById(R.id.subtitle);
        descriptionView = (TextView) findViewById(R.id.description);
    }

    public void setPhotoDescription(PhotoDescription newPhotoDescription) {
        if (state != State.CREATED) {
            throw new IllegalStateException("Already set photo description");
        }

        // Update state.
        state = State.PHOTO_DESCRIPTION_SET;
        photoDescription = newPhotoDescription;

        // Update view components.
        if (photoDescription.getPhoto_data().isSetToo_big_photo_data()) {
        } else if (photoDescription.getPhoto_data().isSetImage_photo_data()) {
            imagePhotoView.setVisibility(VISIBLE);
            imagePhotoView.setData(photoDescription.getPhoto_data().getImage_photo_data());
        } else if (photoDescription.getPhoto_data().isSetVideo_photo_data()) {
            videoPhotoView.setVisibility(VISIBLE);
            videoPhotoView.setData(photoDescription.getPhoto_data().getVideo_photo_data());
        } else {
            throw new IllegalStateException("No valid photo data found");
        }

        if (!photoDescription.getSubtitle().equals("")) {
            subtitleView.setVisibility(VISIBLE);
            subtitleView.setText(photoDescription.getSubtitle());
        }

        if (!photoDescription.getDescription().equals("")) {
            descriptionView.setVisibility(VISIBLE);
            descriptionView.setText(photoDescription.getSubtitle());
        }

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }
}
