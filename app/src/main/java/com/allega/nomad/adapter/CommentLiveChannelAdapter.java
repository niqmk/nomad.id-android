package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.RealmAdapter;
import com.allega.nomad.entity.LiveChannelComment;
import com.allega.nomad.viewgroup.ReviewViewGroup;

import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CommentLiveChannelAdapter extends RealmAdapter<LiveChannelComment> {

    public CommentLiveChannelAdapter(Context context, RealmResults<LiveChannelComment> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        ReviewViewGroup reviewViewGroup = (ReviewViewGroup) convertView;
        reviewViewGroup.bind(getItem(position), position);
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new ReviewViewGroup(context);
    }
}
