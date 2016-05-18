package com.chocosawse.kotlindemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mReverseButton;
    private Button mClearButton;
    private Button mRender;
    private OptionsView mOptions;
    private AsciiView mAsciiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOptions = new OptionsViewImpl((ViewGroup) findViewById(R.id.options));
        mOptions.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.doge));
        mReverseButton = (Button) findViewById(R.id.reverse);
        mClearButton = (Button) findViewById(R.id.clear);
        mRender = (Button) findViewById(R.id.render);
        mAsciiView = (AsciiView) findViewById(R.id.ascii);

        if (mAsciiView != null) {
            mAsciiView.setOptions(mOptions);
        }

        mReverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chars = mOptions.getCharacters();
                mOptions.setCharacters(reverse(chars));
                refresh();
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptions.setCharacters("");
                refresh();
            }
        });

        mRender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    private String reverse(String s) {
        return (new StringBuilder(s)).reverse().toString();
    }

    private void refresh() {
        mAsciiView.refresh();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View focus = getCurrentFocus();
        if (focus == null) {
            return;
        }
        focus.clearFocus();
        imm.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
