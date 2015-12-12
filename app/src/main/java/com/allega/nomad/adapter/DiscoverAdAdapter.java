package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.viewgroup.DiscoverAdViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverAdAdapter extends AbstractRecycleAdapter<Ad> {

    public DiscoverAdAdapter(Context context, List<Ad> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        DiscoverAdViewGroup discoverAdViewGroup = (DiscoverAdViewGroup) convertView;
        discoverAdViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new DiscoverAdViewGroup(context);
    }
}
