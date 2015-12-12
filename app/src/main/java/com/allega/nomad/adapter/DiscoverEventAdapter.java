package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.Event;
import com.allega.nomad.viewgroup.DiscoverEventViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverEventAdapter extends AbstractRecycleAdapter<Event> {

    public DiscoverEventAdapter(Context context, List<Event> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        DiscoverEventViewGroup discoverEventViewGroup = (DiscoverEventViewGroup) convertView;
        discoverEventViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new DiscoverEventViewGroup(context);
    }
}
