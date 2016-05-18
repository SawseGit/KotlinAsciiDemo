package com.chocosawse.kotlindemo;

import android.graphics.Bitmap;

/**
 * Created by Steven Anyanwu on 5/17/16.
 * Copyright(c) 2016 Level, Inc.
 */

public interface OptionsView {
    String getCharacters();
    int getCompression();
    Bitmap getBitmap();

    void setCharacters(String chars);
    void setCompression(int compression);
    void setBitmap(Bitmap bitmap);
}
