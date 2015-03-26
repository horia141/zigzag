package com.zigzag.client_app.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zigzag.client_app.R;

public class TileImageView extends LinearLayout implements BitmapSetListener {

    @Nullable private BitmapSetAdapter adapter;

    public TileImageView(Context context) {
        this(context, null);
    }

    public TileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = null;

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);

        if (adapter != null) {
            drawTiles();
        }
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
        drawTiles();
    }

    private void drawTiles() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int ii = adapter.getCount(); ii < getChildCount(); ii++) {
            removeViewAt(ii);
        }

        for (int ii = 0; ii < adapter.getCount(); ii++) {
            BitmapSetAdapter.TileInfo tileInfo = adapter.getTileInfo(ii);

            View tileView = getChildAt(ii);

            if (tileView == null) {
                tileView = inflater.inflate(R.layout.tile_image_view_tile, this);
            }

            HeightAdjustedProgressBar progressBar = (HeightAdjustedProgressBar) tileView.findViewById(R.id.waiting);
            ImageView imageView = (ImageView) tileView.findViewById(R.id.image);

            if (tileInfo.getBitmap() != null) {
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(tileInfo.getBitmap());
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setTileWidthAndHeight(tileInfo.getWidth(), tileInfo.getHeight());
                imageView.setVisibility(View.GONE);
            }
        }
    }
}
