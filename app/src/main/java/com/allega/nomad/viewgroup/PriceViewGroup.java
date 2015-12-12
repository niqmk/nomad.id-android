package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.entity.Price;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 8/26/15.
 */
public class PriceViewGroup extends LinearLayout {
    @InjectView(R.id.duration_text_view)
    protected TextView durationTextView;
    @InjectView(R.id.price_text_view)
    protected TextView priceTextView;

    public PriceViewGroup(Context context) {
        super(context);
        init();
    }

    public PriceViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_price, this, true);
        ButterKnife.inject(this);
    }

    public void bind(Price price) {
        durationTextView.setText(getResources().getString(R.string.rent_for_days, price.getExpiryDays()));
        priceTextView.setText(getResources().getString(R.string.x_points, price.getPrice()));
    }

}
