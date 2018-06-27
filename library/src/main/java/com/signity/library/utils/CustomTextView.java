package com.signity.library.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by signity.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView{

    public CustomTextView(Context context) {
        super(context);
        init();
    }
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        /*Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/yugothic.ttf");
        setTypeface(tf);*/
    }
}
