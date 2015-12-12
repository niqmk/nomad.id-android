package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.model.Point;
import com.allega.nomad.viewgroup.PointViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 7/25/15.
 */
public class PointAdapter extends AbstractAdapter<Point> {

    private boolean isBuy;

    public PointAdapter(Context context, List items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        PointViewGroup pointViewGroup = (PointViewGroup) convertView;
        pointViewGroup.setType(isBuy);
        pointViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new PointViewGroup(context);
    }

    public void setType(boolean isBuy) {
        this.isBuy = isBuy;
    }
}
