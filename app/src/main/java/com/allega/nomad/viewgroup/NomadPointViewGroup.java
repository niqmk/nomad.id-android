package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.entity.NomadPoint;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 6/7/15.
 */
public class NomadPointViewGroup extends CardView {

    @InjectView(R.id.value_text_view)
    protected TextView valueTextView;
    @InjectView(R.id.price_text_view)
    protected TextView priceTextView;

    public NomadPointViewGroup(Context context) {
        super(context);
        init();
    }

    public NomadPointViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NomadPointViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_nomad_point, this, true);
        ButterKnife.inject(this);
    }

    public void bind(NomadPoint nomadPoint) {
        valueTextView.setText(nomadPoint.getValue());
        if (nomadPoint.getPrice() != null) {
            priceTextView.setText(nomadPoint.getPrice());
            priceTextView.setVisibility(VISIBLE);
        } else {
            priceTextView.setVisibility(GONE);
        }
    }

}
