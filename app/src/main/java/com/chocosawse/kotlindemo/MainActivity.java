package com.chocosawse.kotlindemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button mReverseButton;
    private Button mClearButton;
    private Button mRender;
    private Button mDogeButton;
    private Button mDickButtButton;
    private EditText mCharET;
    private EditText mCompressionET;
    private OptionsView mOptions;
    private AsciiView mAsciiView;
    private Bitmap mDogeBitmap;
    private Bitmap mDickButtBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOptions = new OptionsViewImpl((ViewGroup) findViewById(R.id.options));
        mDogeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.doge);
        mDickButtBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dickbutt);
        mOptions.setBitmap(mDogeBitmap);
        mReverseButton = (Button) findViewById(R.id.reverse);
        mClearButton = (Button) findViewById(R.id.clear);
        mRender = (Button) findViewById(R.id.render);
        mAsciiView = (AsciiView) findViewById(R.id.ascii);
        mCharET = (EditText) findViewById(R.id.chars);
        mCompressionET = (EditText) findViewById(R.id.compression);
        mDogeButton = (Button) findViewById(R.id.doge);
        mDickButtButton = (Button) findViewById(R.id.db);

        if (mAsciiView != null) {
            mAsciiView.setOptions(mOptions);
        }

        InputUtils.setOnComplete(mCompressionET, new Runnable() {
            @Override
            public void run() {
                InputUtils.hideKeyboard(MainActivity.this);
                refresh();
            }
        });

        InputUtils.setOnComplete(mCharET, new Runnable() {
            @Override
            public void run() {
                InputUtils.hideKeyboard(MainActivity.this);
                refresh();
            }
        });

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

        mDogeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptions.setBitmap(mDogeBitmap);
                refresh();
            }
        });

        mDickButtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptions.setBitmap(mDickButtBitmap);
                refresh();
            }
        });
    }

    private String reverse(String s) {
        return (new StringBuilder(s)).reverse().toString();
    }

    private void refresh() {
        mAsciiView.refresh();
        InputUtils.hideKeyboard(this);
    }
}
