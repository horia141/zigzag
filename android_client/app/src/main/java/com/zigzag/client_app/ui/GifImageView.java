package com.zigzag.client_app.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;

import com.zigzag.client_app.model.AnimationSetImageData;

import java.util.ArrayList;
import java.util.List;

public class GifImageView extends ImageView implements BitmapSetListener {

    @Nullable private BitmapSetAdapter<GifImageView> adapter;
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

    public void setImageDataAndAdapter(AnimationSetImageData imageData, BitmapSetAdapter<GifImageView> newAdapter) {
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
        // Nothing to do here.
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();

        if (d != null) {
            // Ceil not round - avoid thin vertical gaps along the left/right edges
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) Math.ceil((float) width
                    * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public boolean allFramesLoaded() {
        if (adapter == null) {
            return false;
        }

        for (int ii = 0; ii < adapter.getCount(); ii++) {
            if (adapter.getBitmap(ii) == null) {
                return false;
            }
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
                    if (!allFramesLoaded()) {
                        publishProgress(-1);
                    } else {
                        publishProgress(framesCounter);
                        framesCounter = (framesCounter + 1) % framesCount;
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
            if (framesCounter == -1) {
                return;
            }

            Log.e("ZigZag/AnimationTask", String.format("here %d %d", GifImageView.this.hashCode(), this.hashCode()));

            if (adapter == null) {
                return;
            }

            GifImageView.this.setImageBitmap(adapter.getBitmap(framesCounter));
            GifImageView.this.invalidate();
        }
    }
}
