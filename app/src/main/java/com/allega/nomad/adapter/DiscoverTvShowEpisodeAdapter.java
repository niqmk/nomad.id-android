package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.viewgroup.DiscoverTvShowViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverTvShowEpisodeAdapter extends AbstractRecycleAdapter<TvShowEpisode> {

    public DiscoverTvShowEpisodeAdapter(Context context, List<TvShowEpisode> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        DiscoverTvShowViewGroup discoverTvShowViewGroup = (DiscoverTvShowViewGroup) convertView;
        discoverTvShowViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new DiscoverTvShowViewGroup(context);
    }
}
