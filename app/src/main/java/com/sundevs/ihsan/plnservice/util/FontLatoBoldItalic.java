package com.sundevs.ihsan.plnservice.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ihsan on 01/11/2015.
 */
public class FontLatoBoldItalic extends TextView {


    public FontLatoBoldItalic(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-BoldItalic.ttf");
        this.setTypeface(face);
    }

    public FontLatoBoldItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-BoldItalic.ttf");
        this.setTypeface(face);
    }

    public FontLatoBoldItalic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "Lato-BoldItalic.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}