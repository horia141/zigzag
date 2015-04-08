package com.zigzag.client_app.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zigzag.client_app.R;
import com.zigzag.client_app.model.VideoPhotoData;

public class VideoView extends LinearLayout implements BitmapSetListener {

    @Nullable BitmapSetAdapter adapter;
    @Nullable VideoPhotoData videoPhotoData;

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = null;
        videoPhotoData = null;

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
    }

    public void setAdapter(BitmapSetAdapter newAdapter) {
        if (adapter != null) {
            adapter.removeListener(this);
        }

        adapter = newAdapter;
        adapter.addListener(this);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        draw();
    }

    public void draw() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tileView = getChildAt(0);

        if (tileView == null) {
            tileView = inflater.inflate(R.layout.tile_image_view_tile, VideoView.this);
        }

        HeightAdjustedProgressBar progressBar = (HeightAdjustedProgressBar) tileView.findViewById(R.id.waiting);
        ResizableImageView imageView = (ResizableImageView) tileView.findViewById(R.id.image);

        if (adapter == null || adapter.getCount() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        } else {
            BitmapSetAdapter.TileInfo tileInfo = adapter.getTileInfo(0);
            if (tileInfo.getBitmap() == null) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setTileWidthAndHeight(tileInfo.getWidth(), tileInfo.getHeight());
                imageView.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(tileInfo.getBitmap());
            }
        }
    }
}
