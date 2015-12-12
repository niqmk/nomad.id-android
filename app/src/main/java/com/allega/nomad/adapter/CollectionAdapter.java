package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.entity.Collection;
import com.allega.nomad.viewgroup.CollectionViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CollectionAdapter extends AbstractAdapter<Collection> {

    public CollectionAdapter(Context context, List<Collection> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        CollectionViewGroup viewGroup = (CollectionViewGroup) convertView;
        viewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new CollectionViewGroup(context);
    }
}
