package com.zigzag.client_app.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.zigzag.client_app.R;
import com.zigzag.common.model.PhotoDescription;

public class PhotoDescriptionView extends LinearLayout implements UiPhotoHolder {

    public interface OnLongClickListener {
        void onLongClick();
    }

    private enum State {
        CREATED,
        PHOTO_DESCRIPTION_SET
    }

    private State state;
    @Nullable private PhotoDescription photoDescription;
    @Nullable private OnLongClickListener onLongClickListener;
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
        onLongClickListener = null;

        setOrientation(VERTICAL);

        inflate(getContext(), R.layout.photo_description_view, this);
        imagePhotoView = (ImagePhotoView) findViewById(R.id.image_photo);
        videoPhotoView = (VideoPhotoView) findViewById(R.id.video_photo);
        subtitleView = (TextView) findViewById(R.id.subtitle);
        descriptionView = (TextView) findViewById(R.id.description);

        imagePhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onImageOrPhotoLongClick();
            }
        });
        videoPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onImageOrPhotoLongClick();
            }
        });
    }

    public void setPhotoDescription(PhotoDescription newPhotoDescription) {
        if (state != State.CREATED) {
            throw new IllegalStateException("Not in description setting state");
        }

        // Update state.
        state = State.PHOTO_DESCRIPTION_SET;
        photoDescription = newPhotoDescription;

        boolean setSubtitle = newPhotoDescription.getSubtitle().matches(".*\\w.*");
        if (setSubtitle) {
            subtitleView.setVisibility(VISIBLE);
            subtitleView.setText(newPhotoDescription.getSubtitle());
        }

        // Update view components.
        if (newPhotoDescription.getPhoto_data().isSetToo_big_photo_data()) {
            // Nothing to do here.
        } else if (newPhotoDescription.getPhoto_data().isSetImage_photo_data()) {
            imagePhotoView.setVisibility(VISIBLE);
            imagePhotoView.setImagePhotoData(newPhotoDescription.getPhoto_data().getImage_photo_data());
        } else if (newPhotoDescription.getPhoto_data().isSetVideo_photo_data()) {
            videoPhotoView.setVisibility(VISIBLE);
            videoPhotoView.setVideoPhotoData(newPhotoDescription.getPhoto_data().getVideo_photo_data());
        } else {
            throw new IllegalStateException("No valid photo data found");
        }

        boolean setDescription = newPhotoDescription.getDescription().matches(".*\\w.*");
        if (setDescription) {
            descriptionView.setVisibility(VISIBLE);
            descriptionView.setText(newPhotoDescription.getDescription());
        }

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setBitmapForTile(int tileIdx, Bitmap bitmap) {
        if (state != State.PHOTO_DESCRIPTION_SET) {
            throw new IllegalStateException("Not in image setting state");
        }

        // Update view components.
        imagePhotoView.setBitmapForTile(tileIdx, bitmap);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setBitmapForFirstFrame(Bitmap bitmap) {
        if (state != State.PHOTO_DESCRIPTION_SET) {
            throw new IllegalStateException("Not in video setting state");
        }

        // Update view components.
        videoPhotoView.setBitmapForFirstFrame(bitmap);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setSourcePathForLocalVideo(String sourcePathForLocalVideo) {
        if (state != State.PHOTO_DESCRIPTION_SET) {
            throw new IllegalStateException("Not in video setting state");
        }

        // Update view components.
        videoPhotoView.setSourcePathForLocalVideo(sourcePathForLocalVideo);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void enableVideo() {
        if (state != State.PHOTO_DESCRIPTION_SET) {
            throw new IllegalStateException("Not in video enabling state");
        }

        // Update view components.
        videoPhotoView.enable();
    }

    public void disableVideo() {
        if (state != State.PHOTO_DESCRIPTION_SET) {
            throw new IllegalStateException("Not in video enabling state");
        }

        // Update view components.
        videoPhotoView.disable();
    }

    @Override
    public void clearPhotoResources() {
        if (state != State.PHOTO_DESCRIPTION_SET) {
            throw new IllegalStateException("Not in resource clearing state");
        }

        // Update view components.
        if (photoDescription.getPhoto_data().isSetToo_big_photo_data()) {
            // Nothing to do here.
        } else if (photoDescription.getPhoto_data().isSetImage_photo_data()) {
            imagePhotoView.clearPhotoResources();
        } else if (photoDescription.getPhoto_data().isSetVideo_photo_data()) {
            videoPhotoView.clearPhotoResources();
        } else {
            throw new IllegalStateException("No valid photo data found");
        }
    }

    public void setOnLongClickListener(@Nullable OnLongClickListener newOnLongClickListener) {
        onLongClickListener = newOnLongClickListener;
    }

    private boolean onImageOrPhotoLongClick() {
        if (onLongClickListener == null) {
            return false;
        }

        onLongClickListener.onLongClick();
        return true;
    }
}
