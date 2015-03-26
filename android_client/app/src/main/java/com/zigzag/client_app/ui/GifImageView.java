package com.zigzag.client_app.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zigzag.client_app.R;
import com.zigzag.client_app.model.AnimationSetImageData;

import java.util.ArrayList;
import java.util.List;

public class GifImageView extends LinearLayout implements BitmapSetListener {

    @Nullable private BitmapSetAdapter adapter;
    @Nullable private AnimationSetImageData imageData;
    private final List<AnimationTask> animationTasks;

    public GifImageView(Context context) {
        this(context, null);
    }

    public GifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = null;
        imageData = null;
        animationTasks = new ArrayList<>();

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (adapter != null) {
            adapter.removeListener(this);
        }

        for (AnimationTask task : animationTasks) {
            task.cancel(true);
        }
    }

    public void setImageDataAndAdapter(AnimationSetImageData imageData, BitmapSetAdapter newAdapter) {
        this.imageData = imageData;

        if (adapter != null) {
            adapter.removeListener(this);
        }

        adapter = newAdapter;
        adapter.addListener(this);
        notifyDataSetChanged();

        AnimationTask animationTask = new AnimationTask();
        animationTask.execute();
        animationTasks.add(animationTask);
    }

    @Override
    public void notifyDataSetChanged() {
        if (atLeastFirstFrameLoaded()) {
            drawFrame(0);
        } else {
            drawFrame(-1);
        }
    }

    private void drawFrame(int framesCounter) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tileView = getChildAt(0);

        if (tileView == null) {
            tileView = inflater.inflate(R.layout.tile_image_view_tile, GifImageView.this);
        }

        HeightAdjustedProgressBar progressBar = (HeightAdjustedProgressBar) tileView.findViewById(R.id.waiting);
        ImageView imageView = (ImageView) tileView.findViewById(R.id.image);

        if (framesCounter == -1 || adapter == null) {
            progressBar.setVisibility(View.VISIBLE);
            if (adapter != null && adapter.getCount() > 0) {
                BitmapSetAdapter.TileInfo tileInfo = adapter.getTileInfo(0);
                progressBar.setTileWidthAndHeight(tileInfo.getWidth(), tileInfo.getHeight());
            }
            imageView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(adapter.getTileInfo(framesCounter).getBitmap());
        }
    }

    private boolean allFramesLoaded() {
        if (adapter == null) {
            return false;
        }

        for (int ii = 0; ii < adapter.getCount(); ii++) {
            if (adapter.getTileInfo(ii).getBitmap() == null) {
                return false;
            }
        }

        return true;
    }

    private boolean atLeastFirstFrameLoaded() {
        if (adapter == null) {
            return false;
        }

        if (adapter.getTileInfo(0).getBitmap() == null) {
            return false;
        }

        return true;
    }

    private class AnimationTask extends AsyncTask<Boolean, Integer, Boolean> {
        long timeBetweenFrames;
        int framesCount;

        public AnimationTask() {
            this.timeBetweenFrames = imageData.getTimeBetweenFrames();
            this.framesCount = imageData.getFramesDesc().size();
        }

        @Override
        protected Boolean doInBackground(Boolean... unused) {
            int framesCounter = 0;

            while (true) {
                try {
                    Thread.sleep(timeBetweenFrames);

                    if (isCancelled()) {
                        break;
                    }

                    // We should somehow protect access to allFramesLoaded, or rather the set of
                    // bitmaps it scans with a lock. I don't think the complexity is warranted.
                    // This method is read only and if it doesn't read the latest state for a
                    // bitmap, it'll get it next time.
                    if (allFramesLoaded()) {
                        publishProgress(framesCounter);
                        framesCounter = (framesCounter + 1) % framesCount;
                    } else if (atLeastFirstFrameLoaded()) {
                        publishProgress(0);
                    } else {
                        publishProgress(-1);
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... framesCounterParams) {
            int framesCounter = framesCounterParams[0];
            Log.e("ZigZag/AnimationTask", String.format("here %d %d", GifImageView.this.hashCode(), this.hashCode()));
            drawFrame(framesCounter);
        }
    }
}
