package com.chocosawse.kotlindemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Steven Anyanwu on 5/17/16.
 * Copyright(c) 2016 Level, Inc.
 */
public class AsciiView extends View {

    private OptionsView mOptions;
    private Bitmap mScaledBitmap;
    private Bitmap mAsciiBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private boolean mHasInitialized = false;

    public AsciiView(Context context) {
        super(context);
        init();
    }

    public AsciiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AsciiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOptions(OptionsView options) {
        mOptions = options;
        refresh();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!mHasInitialized) {
            init();
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
    }

    public void refresh() {
        if (getHeight() > 0 && getWidth() > 0) {
            int w = mOptions.getBitmap().getWidth();
            int h = mOptions.getBitmap().getHeight();
            int s = mOptions.getCompression();
            mScaledBitmap = Bitmap.createScaledBitmap(mOptions.getBitmap(), w / s, h / s, false);
            mAsciiBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
            mCanvas = new Canvas(mAsciiBitmap);
            mHasInitialized = true;
        }
        invalidate();
    }

    public void drawAscii() {
        int L = 0;
        int px = 0;
        int r, g, b;
        float s = getHeight() / mScaledBitmap.getHeight();
        Log.d("Scale", "" + s);
        mCanvas.drawColor(Color.WHITE);
        for (int y = 0; y < mScaledBitmap.getHeight(); y++) {
            for (int x = 0; x < mScaledBitmap.getWidth(); x++) {
                px = mScaledBitmap.getPixel(x, y);
                r = (px >> 16) & 0xFF;
                g = (px >> 8) & 0xFF;
                b = px & 0xFF;
                L = (r + g + b)/ 3;
                char c = getChar(L);
                mCanvas.drawText("" + c, x * s, y * s, mPaint);
            }
        }
    }

    public char getChar(int lum) {
        int len = mOptions.getCharacters().length();
        int step = 256 / len;
        return mOptions.getCharacters().charAt(Math.min(len - 1, lum / step));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mHasInitialized) {
            drawAscii();
            canvas.drawBitmap(mAsciiBitmap, 0, 0, mPaint);
        }
    }
}
