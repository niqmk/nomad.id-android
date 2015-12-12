package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

/**
 * Created by imnotpro on 8/12/15.
 */
public class SquareViewFlipper extends ViewFlipper {
    public SquareViewFlipper(Context context) {
        super(context);
    }

    public SquareViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
