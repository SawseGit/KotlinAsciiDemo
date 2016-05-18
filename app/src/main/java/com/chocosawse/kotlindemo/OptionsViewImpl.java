package com.chocosawse.kotlindemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Steven Anyanwu on 5/17/16.
 * Copyright(c) 2016 Level, Inc.
 */
public class OptionsViewImpl implements OptionsView {

    private EditText mCharText;
    private EditText mCompressionText;
    private Bitmap mBitmap;

    public OptionsViewImpl(ViewGroup v) {
        mCharText = (EditText) v.findViewById(R.id.chars);
        mCompressionText = (EditText) v.findViewById(R.id.compression);
    }

    @Override
    public String getCharacters() {
        return mCharText.getText().toString();
    }

    @Override
    public int getCompression() {
        return Integer.parseInt(mCompressionText.getText().toString());
    }

    @Override
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public void setCharacters(String chars) {
        mCharText.setText(chars);
    }

    @Override
    public void setCompression(int compression) {
        mCompressionText.setText(compression);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
