package com.example.cqupt.pleasantsafe.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;


/**
 * Created by Ming on 2017/11/11.
 */

public class HomeTextView extends AppCompatTextView {
    public HomeTextView(Context context) {

        this(context, null);
    }

    public HomeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setFocusableInTouchMode(true);
        setMarqueeRepeatLimit(-1);

    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {

        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }


    }
}
