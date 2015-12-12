package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.allega.nomad.R;

import butterknife.ButterKnife;

/**
 * Created by imnotpro on 6/7/15.
 */
public class NomadPointHeaderViewGroup extends CardView {

    public NomadPointHeaderViewGroup(Context context) {
        super(context);
        init();
    }

    public NomadPointHeaderViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NomadPointHeaderViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_nomad_point_header, this, true);
        ButterKnife.inject(this);
    }

}
