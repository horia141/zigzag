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
        DATA_SET,
        SHOW_FIRST_FRAME,
        SHOW_VIDEO
    }

    @Nullable private VideoPhotoData data;
    @Nullable private ProgressBar progressBar;
    @Nullable private ImageView imageForFirstFrame;
    @Nullable private VideoView video;
    private State state;
    private boolean videoEnabled;

    public VideoPhotoView(Context context) {
        this(context, null);
    }

    public VideoPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        data = null;
        progressBar = null;
        imageForFirstFrame = null;
        video = null;
        state = State.CREATED;
        videoEnabled = false;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (video != null) {
            video.stopPlayback();
            video = null;
        }
    }

    public void setData(VideoPhotoData newData) {
        if (state != State.CREATED) {
            return;
        }

        // First, clear up the current view.
        for (int ii = getChildCount() - 1; ii >= 0; ii--) {
            removeViewAt(ii);
        }

        // Then, clear up all views.
        progressBar = null;
        imageForFirstFrame = null;
        video = null;

        // Reset the view data with the new information.
        data = newData;

        progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminate(true);

        imageForFirstFrame = new ImageView(getContext());

        video = new VideoView(getContext());
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                video.start();
            }
        });
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.start();
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

        // Change to the new state.
        state = State.DATA_SET;
    }

    public void setBitmapForFirstFrame(Bitmap bitmap) {
        if (data == null) {
            return;
        }

        if (state == State.SHOW_VIDEO) {
            return;
        }

        removeViewAt(0);
        progressBar = null;

        imageForFirstFrame.setImageBitmap(bitmap);
        addView(imageForFirstFrame);

        // Change to the new state.
        state = State.SHOW_FIRST_FRAME;
    }

    public void setSourcePathForLocalVideo(String sourcePathForLocalVideo) {
        if (data == null) {
            return;
        }

        removeViewAt(0);
        progressBar = null;
        imageForFirstFrame = null;

        video.setVideoPath(sourcePathForLocalVideo);

        addView(video);

        state = State.SHOW_VIDEO;
    }

    public void enableVideo() {
        videoEnabled = true;
    }

    public void disableVideo() {
        videoEnabled = false;
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
