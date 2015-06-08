package com.zigzag.client_app.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.zigzag.common.model.VideoPhotoData;

public class VideoPhotoView extends ViewGroup {

    private enum State {
        CREATED,
        VIDEO_PHOTO_DATA_SET,
        SOURCE_PATH_FOR_LOCAL_VIDEO_SET
    }

    private State state;
    @Nullable private VideoPhotoData data;
    @Nullable private ProgressBar progressBar;
    @Nullable private ImageView imageForFirstFrame;
    @Nullable private VideoView video;
    private boolean videoLoaded;
    private boolean enabled;

    public VideoPhotoView(Context context) {
        this(context, null);
    }

    public VideoPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        state = State.CREATED;
        data = null;
        progressBar = null;
        imageForFirstFrame = null;
        video = null;
        videoLoaded = false;
        enabled = false;
    }

    public void setVideoPhotoData(VideoPhotoData newData) {
        if (state != State.CREATED) {
            throw new IllegalStateException("Not in video photo data setting state");
        }

        // Update state.
        state = State.VIDEO_PHOTO_DATA_SET;
        data = newData;

        // Update view.
        progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminate(true);

        imageForFirstFrame = new ImageView(getContext());

        video = new VideoView(getContext());
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoLoaded = true;
                if (enabled) {
                    video.start();
                }
            }
        });
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (enabled) {
                    video.start();
                }
            }
        });
        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e("ZigZag/VideoPhotoView", String.format("Error %d %d", what, extra));
                return true;
            }
        });

        addView(progressBar);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setBitmapForFirstFrame(Bitmap bitmap) {
        if (state != State.VIDEO_PHOTO_DATA_SET) {
            if (state == State.SOURCE_PATH_FOR_LOCAL_VIDEO_SET) {
                return;
            }

            throw new IllegalStateException("Not in bitmap for first frame setting state");
        }

        // Update view components.
        removeViewAt(0);
        progressBar = null;
        imageForFirstFrame.setImageBitmap(bitmap);
        addView(imageForFirstFrame);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setSourcePathForLocalVideo(String sourcePathForLocalVideo) {
        if (state != State.VIDEO_PHOTO_DATA_SET && state != State.SOURCE_PATH_FOR_LOCAL_VIDEO_SET) {
            throw new IllegalStateException("Not in source path for local video setting state");
        }

        // Update state.
        state = State.SOURCE_PATH_FOR_LOCAL_VIDEO_SET;

        // Update view components.
        removeViewAt(0);
        progressBar = null;
        imageForFirstFrame = null;
        video.setVideoPath(sourcePathForLocalVideo);
        addView(video);
        videoLoaded = false;

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void enable() {
        if (state != State.VIDEO_PHOTO_DATA_SET && state != State.SOURCE_PATH_FOR_LOCAL_VIDEO_SET) {
            throw new IllegalStateException("Not in enabling state");
        }

        // Update view components.
        enabled = true;
        if (videoLoaded && !video.isPlaying()) {
            video.start();
        }
    }

    public void disable() {
        if (state != State.VIDEO_PHOTO_DATA_SET && state != State.SOURCE_PATH_FOR_LOCAL_VIDEO_SET) {
            throw new IllegalStateException("Not in disabling state");
        }

        // Update view components.
        enabled = false;
        if (videoLoaded && video.isPlaying()) {
            video.pause();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        assert widthMode == MeasureSpec.EXACTLY;
        assert heightMode == MeasureSpec.UNSPECIFIED;

        View view = getChildAt(0);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        float aspectRatio = (float) data.getFirst_frame().getHeight() / (float) data.getFirst_frame().getWidth();
        int height = (int) (width * aspectRatio);
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(widthMeasureSpec, newHeightMeasureSpec);
        layoutParams.setMargins(0, 0, 0, 0);
        view.setLayoutParams(layoutParams);
        view.setPadding(0, 0, 0, 0);
        measureChild(view, widthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View view = getChildAt(0);
        view.layout(left, top, right, bottom);
    }
}
