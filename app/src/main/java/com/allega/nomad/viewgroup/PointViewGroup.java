package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.model.Point;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 7/25/15.
 */
public class PointViewGroup extends LinearLayout {

    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;
    @InjectView(R.id.value_text_view)
    protected TextView valueTextView;
    @InjectView(R.id.price_text_view)
    protected TextView priceTextView;
    @InjectView(R.id.price_value_text_view)
    protected TextView priceValueTextView;
    @InjectView(R.id.buy_layout)
    protected LinearLayout buyLayout;
    @InjectView(R.id.free_layout)
    protected LinearLayout freeLayout;

    private boolean isBuy;

    public PointViewGroup(Context context) {
        super(context);
        init();
    }

    public PointViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_point, this, true);
        ButterKnife.inject(this);
    }

    public void bind(Point item) {
        if (isBuy) {
            priceValueTextView.setText(getResources().getString(R.string.rp_value, item.getPriceInIdr()));
            priceTextView.setText(String.valueOf(item.getPoint()));
        } else {
            titleTextView.setText(item.getName());
            valueTextView.setText(String.valueOf(item.getPoint()));
        }
    }

    public void setType(boolean isBuy) {
        this.isBuy = isBuy;
        if (isBuy) {
            buyLayout.setVisibility(VISIBLE);
            freeLayout.setVisibility(GONE);
        } else {
            buyLayout.setVisibility(GONE);
            freeLayout.setVisibility(VISIBLE);
        }
    }
}
