package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.allega.nomad.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by imnotpro on 6/7/15.
 */
public class PointHeaderViewGroup extends CardView {

    @InjectView(R.id.buy_layout)
    protected LinearLayout buyLayout;
    @InjectView(R.id.free_layout)
    protected LinearLayout freeLayout;
    private PointHeaderListener listener;

    public PointHeaderViewGroup(Context context) {
        super(context);
        init();
    }

    public PointHeaderViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointHeaderViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_point_header, this, true);
        ButterKnife.inject(this);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @OnClick(R.id.buy_layout)
    protected void onBuy() {
        if (listener != null) {
            listener.onBuy();
        }
        buyLayout.setBackgroundColor(getResources().getColor(R.color.orange));
        freeLayout.setBackgroundColor(getResources().getColor(R.color.gray));
    }

    @OnClick(R.id.free_layout)
    protected void onFree() {
        if (listener != null) {
            listener.onFree();
        }
        buyLayout.setBackgroundColor(getResources().getColor(R.color.gray));
        freeLayout.setBackgroundColor(getResources().getColor(R.color.orange));
    }

    public void setListener(PointHeaderListener listener) {
        this.listener = listener;
    }

    public interface PointHeaderListener {
        void onBuy();

        void onFree();
    }
}
