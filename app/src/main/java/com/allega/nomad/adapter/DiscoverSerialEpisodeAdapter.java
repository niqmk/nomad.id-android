package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.viewgroup.DiscoverSerialViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverSerialEpisodeAdapter extends AbstractRecycleAdapter<SerialEpisode> {

    public DiscoverSerialEpisodeAdapter(Context context, List<SerialEpisode> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        DiscoverSerialViewGroup discoverSerialViewGroup = (DiscoverSerialViewGroup) convertView;
        discoverSerialViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new DiscoverSerialViewGroup(context);
    }
}
