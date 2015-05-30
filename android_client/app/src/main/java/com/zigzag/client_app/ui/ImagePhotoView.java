package com.zigzag.client_app.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zigzag.common.model.ImagePhotoData;
import com.zigzag.common.model.TileData;

import java.util.ArrayList;
import java.util.List;

public class ImagePhotoView extends ViewGroup {

    private enum State {
        CREATED,
        IMAGE_PHOTO_DATA_SET
    }

    private State state;
    @Nullable private ImagePhotoData data;
    private final List<ProgressBar> progressBarsForTiles;
    private final List<ImageView> imagesForTiles;

    public ImagePhotoView(Context context) {
        this(context, null);
    }

    public ImagePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        state = State.CREATED;
        data = null;
        progressBarsForTiles = new ArrayList<>();
        imagesForTiles = new ArrayList<>();
    }

    public void setImagePhotoData(ImagePhotoData newData) {
        if (state != State.CREATED) {
            throw new IllegalStateException("Not in image photo data setting state");
        }

        // Update state.
        state = State.IMAGE_PHOTO_DATA_SET;
        data = newData;

        // Update view.
        for (int ii = 0; ii < data.getTilesSize(); ii++) {
            ProgressBar progressBarForTile = new ProgressBar(getContext());
            progressBarForTile.setIndeterminate(true);
            progressBarsForTiles.add(progressBarForTile);

            ImageView imageForTile = new ImageView(getContext());
            imagesForTiles.add(imageForTile);
        }

        for (ProgressBar progressBarForTile : progressBarsForTiles) {
            addView(progressBarForTile);
        }

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    public void setBitmapForTile(int tileIdx, Bitmap bitmap) {
        if (state != State.IMAGE_PHOTO_DATA_SET) {
            throw new IllegalStateException("Not in bitmap setting state");
        }

        if (tileIdx < 0 || tileIdx >= data.getTilesSize()) {
            throw new IndexOutOfBoundsException("Tile index out of bounds");
        }

        // Update view components.
        removeViewAt(tileIdx);
        progressBarsForTiles.set(tileIdx, null);
        ImageView imageForTile = imagesForTiles.get(tileIdx);
        imageForTile.setImageBitmap(bitmap);
        addView(imageForTile, tileIdx);

        // Request new drawing and layout.
        invalidate();
        requestLayout();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        assert widthMode == MeasureSpec.EXACTLY;
        assert heightMode == MeasureSpec.UNSPECIFIED;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;

        for (int ii = 0; ii < data.getTilesSize(); ii++) {
            TileData tile = data.getTiles().get(ii);
            View viewForTile = getChildAt(ii);
            float tileAspectRatio = (float) tile.getHeight() / (float) tile.getWidth();
            int tileHeight = (int)(width * tileAspectRatio);
            int tileHeightMeasureSpec = MeasureSpec.makeMeasureSpec(tileHeight, MeasureSpec.EXACTLY);
            MarginLayoutParams tileLayoutParams = new ViewGroup.MarginLayoutParams(widthMeasureSpec, tileHeightMeasureSpec);
            tileLayoutParams.setMargins(0, 0, 0, 0);
            viewForTile.setLayoutParams(tileLayoutParams);
            viewForTile.setPadding(0, 0, 0, 0);
            measureChild(viewForTile, widthMeasureSpec, tileHeightMeasureSpec);
            height += tileHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int currentTop = top;

        for (int ii = 0; ii < data.getTilesSize(); ii++) {
            View viewForTile = getChildAt(ii);
            int nextTop = currentTop + viewForTile.getMeasuredHeight();
            viewForTile.layout(left, currentTop, right, nextTop);
            currentTop = nextTop;
        }
    }
}
