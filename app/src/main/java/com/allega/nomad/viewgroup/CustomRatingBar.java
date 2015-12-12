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
public class CustomRatingBar extends RatingBar {

    private static int COLOR;

    public CustomRatingBar(Context context) {
        super(context);
        init();
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        COLOR = getResources().getColor(R.color.orange);

        LayerDrawable layerDrawable = (LayerDrawable) getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(0)), COLOR);
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(1)), COLOR);
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), COLOR);
    }
}
