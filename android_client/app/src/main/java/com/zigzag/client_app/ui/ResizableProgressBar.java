package com.zigzag.client_app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ResizableProgressBar extends ProgressBar {

    private int tileWidth;
    private int tileHeight;

    public ResizableProgressBar(Context context) {
        this(context, null);
    }

    public ResizableProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        tileWidth = 0;
        tileHeight = 0;
    }

    public void setTileWidthAndHeight(int newTileWidth, int newTileHeight) {
        tileWidth = newTileWidth;
        tileHeight = newTileHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (tileWidth > 0 && tileHeight > 0) {
            // Ceil not round - avoid thin vertical gaps along the left/right edges
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) Math.ceil((float) width
                    * (float) tileHeight / (float) tileWidth);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
