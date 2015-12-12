package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.freenomad.FreeNomadActivity;

/**
 * Created by imnotpro on 6/7/15.
 */
public class NomadPointFooterViewGroup extends CardView implements View.OnClickListener {

    public NomadPointFooterViewGroup(Context context) {
        super(context);
        init();
    }

    public NomadPointFooterViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NomadPointFooterViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_nomad_point_footer, this, true);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FreeNomadActivity.startActivity(getContext());
    }
}
