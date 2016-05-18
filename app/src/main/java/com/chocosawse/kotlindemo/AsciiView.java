package com.chocosawse.kotlindemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private List<Character> mChars;
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
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(10);
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
        float s = getHeight() / mScaledBitmap.getHeight();
        mChars = getSortedChars();
        mPaint.setTextSize(s);
        mCanvas.drawColor(Color.WHITE);
        for (int y = 0; y < mScaledBitmap.getHeight(); y++) {
            for (int x = 0; x < mScaledBitmap.getWidth(); x++) {
                char c = getChar(mChars, getLumFromPixel(mScaledBitmap.getPixel(x, y)));
                mCanvas.drawText("" + c, x * s, y * s, mPaint);
            }
        }
    }

    public List<Character> getSortedChars() {
        ArrayList<Character> chars = new ArrayList<>();
        for (char c: mOptions.getCharacters().toCharArray()) {
            chars.add(c);
        }
        Collections.sort(chars, new LuminanceComparator(mPaint.getTextSize()));
        return chars;
    }

    int r, g, b;
    public int getLumFromPixel(int px) {
        r = (px >> 16) & 0xFF;
        g = (px >> 8) & 0xFF;
        b = px & 0xFF;
        return (r + g + b)/ 3;
    }

    public char getChar(List<Character> chars, int lum) {
        int len = mOptions.getCharacters().length();
        if (len > 0) {
            int step = 256 / len;
            return chars.get(Math.min(len - 1, lum / step));
        }
        return ' ';
    }

    private class LuminanceComparator implements Comparator<Character> {
        private Bitmap mCharBitmap;
        private Bitmap mScaledCharBitmap;
        private Canvas mCharCanvas;

        public LuminanceComparator(float textSize) {
            mCharBitmap = Bitmap.createBitmap((int) textSize, (int) textSize, Bitmap.Config.RGB_565);
            mCharCanvas = new Canvas(mCharBitmap);
        }

        public Integer getLumFromChar(char c) {
            mCharCanvas.drawColor(Color.WHITE);
            mCharCanvas.drawText("" + c, 0, 0, mPaint);
            mScaledCharBitmap = Bitmap.createScaledBitmap(mCharBitmap, 1, 1, true);
            int lum = getLumFromPixel(mScaledCharBitmap.getPixel(0, 0));
            mScaledCharBitmap.recycle();
            return lum;
        }

        @Override
        public int compare(Character lhs, Character rhs) {
            return getLumFromChar(lhs).compareTo(getLumFromChar(rhs));
        }
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
