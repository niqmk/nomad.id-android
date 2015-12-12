package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.viewgroup.LiveChannelCategoryViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class LiveChannelCategoryAdapter extends AbstractRecycleAdapter<LiveChannel> {

    public LiveChannelCategoryAdapter(Context context, List<LiveChannel> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        LiveChannelCategoryViewGroup liveChannelCategoryViewGroup = (LiveChannelCategoryViewGroup) convertView;
        liveChannelCategoryViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new LiveChannelCategoryViewGroup(context);
    }
}
