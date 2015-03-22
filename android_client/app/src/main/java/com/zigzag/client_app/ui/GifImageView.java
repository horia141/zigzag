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

public class GifImageView extends ImageView {
    @Nullable private AnimationSetImageData imageData;
    private final List<Bitmap> frames;
    private final List<AnimationTask> animationTasks;

    public GifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageData = null;
        frames = new ArrayList<>();
        animationTasks = new ArrayList<>();
    }

    public void setImageData(AnimationSetImageData imageData, List<Bitmap> initialFrames) {
        this.imageData = imageData;

        frames.clear();
        for (int ii = 0; ii < imageData.getFramesDesc().size(); ii++) {
            frames.add(initialFrames.get(ii));
        }

        AnimationTask animationTask = new AnimationTask();
        animationTask.execute();
        animationTasks.add(animationTask);
    }

    public void setFrameBitmap(int frameIndex, Bitmap bitmap) {
        // assert imageData != null
        // assert 0 <= frameIndex < imageData.getFramesDesc().size()
        frames.set(frameIndex, bitmap);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for (AnimationTask task : animationTasks) {
            task.cancel(true);
        }
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
        for (Bitmap frame : frames) {
            if (frame == null) {
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

            GifImageView.this.setImageBitmap(frames.get(framesCounter));
            GifImageView.this.invalidate();
        }
    }
}
