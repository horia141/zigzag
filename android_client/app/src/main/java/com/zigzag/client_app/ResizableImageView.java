package com.zigzag.client_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Helper image which resizes its height to maintain the drawable's aspect ratio.
 * <p>
 * The drawable must have a width, but the height is computed from that. Meant to
 * be a drop-in replacement for ImageView.
 *
 */
public class ResizableImageView extends ImageView {
  /**
   * Construct a resizable image view.
   * 
   * @param context
   * @param attrs
   */
  public ResizableImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * Callback executed when the view is measured.
   * 
   * @param widthMeasureSpec
   * @param heightMeasureSpec
   */
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
}
