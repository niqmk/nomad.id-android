package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.RealmRecycleAdapter;
import com.allega.nomad.entity.MovieClip;
import com.allega.nomad.viewgroup.ClipViewGroup;

import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ClipAdapter extends RealmRecycleAdapter<MovieClip> {

    public ClipAdapter(Context context, RealmResults<MovieClip> items) {
        super(context, items, false);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        ClipViewGroup clipViewGroup = (ClipViewGroup) convertView;
        clipViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new ClipViewGroup(context);
    }
}
