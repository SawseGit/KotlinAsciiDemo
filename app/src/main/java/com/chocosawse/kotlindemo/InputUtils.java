package com.chocosawse.kotlindemo;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InputUtils {

    public static void hideKeyboard(Activity a) {
        InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View focus = a.getCurrentFocus();
        if (focus == null) {
            return;
        }
        focus.clearFocus();
        imm.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void setOnComplete(final @NotNull EditText textView, final Runnable r) {
        textView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, @Nullable KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (r != null) {
                        r.run();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
