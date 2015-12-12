package com.allega.nomad.viewgroup;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.RatingBar;

import com.allega.nomad.R;

/**
 * Created by imnotpro on 7/20/15.
 */
public class CustomSmallRatingBar extends RatingBar {

    private static int COLOR;
    private static int COLOR_DEFAULT;

    public CustomSmallRatingBar(Context context) {
        super(context);
        init();
    }

    public CustomSmallRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSmallRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        COLOR = getResources().getColor(R.color.orange);
        COLOR_DEFAULT = getResources().getColor(R.color.light_gray);

        LayerDrawable layerDrawable = (LayerDrawable) getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(0)), COLOR_DEFAULT);
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(1)), COLOR_DEFAULT);
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), COLOR);
    }
}
