package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.viewgroup.DiscoverLiveChannelViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverLiveChannelAdapter extends AbstractRecycleAdapter<LiveChannel> {

    public DiscoverLiveChannelAdapter(Context context, List<LiveChannel> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        DiscoverLiveChannelViewGroup discoverLiveChannelViewGroup = (DiscoverLiveChannelViewGroup) convertView;
        discoverLiveChannelViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new DiscoverLiveChannelViewGroup(context);
    }
}
