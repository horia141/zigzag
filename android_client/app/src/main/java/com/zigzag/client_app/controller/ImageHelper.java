package com.zigzag.client_app.controller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

class ImageHelper {
    static Bitmap getResizedRoundedCornerBitmap(Bitmap bitmap, int newWidth, int pixels) {
        float aspectRatio = (float)bitmap.getHeight() / bitmap.getWidth();
        int newHeight = (int)Math.ceil(aspectRatio * newWidth);
        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        Bitmap output = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, newWidth, newHeight);
        RectF rectF = new RectF(rect);
        float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapScaled, rect, rect, paint);

        // Draw border
        RectF rectFp1 = new RectF(1, 1, newWidth - 2, newHeight - 2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(rectF, roundPx, roundPx,paint);
        canvas.drawRoundRect(rectFp1, roundPx, roundPx,paint);

        return output;
    }
}