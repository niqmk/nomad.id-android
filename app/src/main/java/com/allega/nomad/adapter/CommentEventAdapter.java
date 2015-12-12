package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.RealmAdapter;
import com.allega.nomad.entity.EventComment;
import com.allega.nomad.viewgroup.ReviewViewGroup;

import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CommentEventAdapter extends RealmAdapter<EventComment> {

    public CommentEventAdapter(Context context, RealmResults<EventComment> items) {
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
