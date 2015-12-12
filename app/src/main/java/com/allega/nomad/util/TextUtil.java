package com.allega.nomad.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by imnotpro on 8/16/15.
 */
public class TextUtil {

    public static void changeFontType(Context context, PagerSlidingTabStrip pagerSlidingTabStrip) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        ViewGroup viewGroup = (ViewGroup) pagerSlidingTabStrip.getChildAt(0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            TextView textView = (TextView) viewGroup.getChildAt(i);
            textView.setTypeface(typeface);
        }

    }
}
