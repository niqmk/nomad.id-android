package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.viewgroup.DiscoverEdutainmentViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverEdutainmentAdapter extends AbstractRecycleAdapter<Edutainment> {

    public DiscoverEdutainmentAdapter(Context context, List<Edutainment> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        DiscoverEdutainmentViewGroup discoverEdutainmentViewGroup = (DiscoverEdutainmentViewGroup) convertView;
        discoverEdutainmentViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new DiscoverEdutainmentViewGroup(context);
    }
}
