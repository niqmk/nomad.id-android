package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.entity.WatchListVideo;
import com.allega.nomad.viewgroup.VideoListViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class HomeWatchListAdapter extends AbstractAdapter<WatchListVideo> {

    public HomeWatchListAdapter(Context context, List<WatchListVideo> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        VideoListViewGroup videoListViewGroup = (VideoListViewGroup) convertView;
        videoListViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new VideoListViewGroup(context);
    }
}
