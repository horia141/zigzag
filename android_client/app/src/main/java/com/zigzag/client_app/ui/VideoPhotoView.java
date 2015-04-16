package com.zigzag.client_app.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zigzag.client_app.R;
import com.zigzag.common.model.VideoPhotoData;

public class VideoPhotoView extends LinearLayout implements BitmapSetListener {

    private enum State {
        CREATED,
        DATA_SET,
        SHOW_FIRST_FRAME,
        SHOW_VIDEO
    }

    @Nullable private BitmapSetAdapter adapter;
    @Nullable private VideoPhotoData videoPhotoData;
    @Nullable private String localPathToVideo;
    private boolean videoEnabled;
    private State state;

    public VideoPhotoView(Context context) {
        this(context, null);
    }

    public VideoPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = null;
        videoPhotoData = null;
        localPathToVideo = null;
        videoEnabled = false;
        state = State.CREATED;

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (adapter != null) {
            adapter.removeListener(this);
        }

        adapter = null;
        videoPhotoData = null;
        localPathToVideo = null;
        state = State.CREATED;
    }

    public void setAdapter(BitmapSetAdapter newAdapter, VideoPhotoData newVideoPhotoData, @Nullable String newLocalPathToVideo) {
        if (adapter != null) {
            adapter.removeListener(this);
        }

        adapter = newAdapter;
        adapter.addListener(this);
        videoPhotoData = newVideoPhotoData;
        localPathToVideo = newLocalPathToVideo;
        state = State.DATA_SET;
        notifyDataSetChanged();
    }

    public void enableVideo() {
        if (videoEnabled) {
            return;
        }

        videoEnabled = true;
        notifyDataSetChanged();
    }

    public void disableVideo() {
        if (!videoEnabled) {
            return;
        }

        videoEnabled = false;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        switch (state) {
            case CREATED:
                // This should be an error
                break;
            case DATA_SET:
                BitmapSetAdapter.TileInfo tileInfo = adapter.getTileInfo(0);
                if (localPathToVideo != null) {
                    setupVideo();
                    state = State.SHOW_VIDEO;
                } else if (tileInfo.getBitmap() != null) {
                    state = State.SHOW_FIRST_FRAME;
                }
                break;
            case SHOW_FIRST_FRAME:
                if (localPathToVideo != null) {
                    setupVideo();
                    state = State.SHOW_VIDEO;
                }
                break;
            case SHOW_VIDEO:
                break;
        }

        draw();
    }

    public void draw() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tileView = getChildAt(0);

        if (tileView == null) {
            tileView = inflater.inflate(R.layout.video_photo_view_tile, VideoPhotoView.this);
        }

        final ResizableProgressBar progressBar = (ResizableProgressBar) tileView.findViewById(R.id.waiting);
        final ResizableImageView imageView = (ResizableImageView) tileView.findViewById(R.id.image);
        final ResizableVideoView videoView = (ResizableVideoView) tileView.findViewById(R.id.video);

        switch (state) {
            case CREATED:
                progressBar.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                break;
            case DATA_SET:
                BitmapSetAdapter.TileInfo tileInfo1 = adapter.getTileInfo(0);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setTileWidthAndHeight(tileInfo1.getWidth(), tileInfo1.getHeight());
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                break;
            case SHOW_FIRST_FRAME:
                BitmapSetAdapter.TileInfo tileInfo2 = adapter.getTileInfo(0);
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(tileInfo2.getBitmap());
                videoView.setVisibility(View.GONE);
                break;
            case SHOW_VIDEO:
                BitmapSetAdapter.TileInfo tileInfo3 = adapter.getTileInfo(0);
                progressBar.setVisibility(View.GONE);
                if (videoEnabled) {
                    imageView.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setTileWidthAndHeight(videoPhotoData.getVideo().getWidth(), videoPhotoData.getVideo().getHeight());
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(tileInfo3.getBitmap());
                    videoView.setVisibility(View.GONE);
                }
                break;
        }

        tileView.requestLayout();
    }

    private void setupVideo() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tileView = getChildAt(0);
        if (tileView == null) {
            tileView = inflater.inflate(R.layout.video_photo_view_tile, VideoPhotoView.this);
        }
        final ResizableVideoView videoView = (ResizableVideoView) tileView.findViewById(R.id.video);
        videoView.setVideoPath(localPathToVideo);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (videoEnabled) {
                    videoView.start();
                }
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (videoEnabled) {
                    videoView.start();
                }
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e("ZigZag/VideoPhotoView", String.format("Error %d %d", what, extra));
                return true;
            }
        });
    }
}
