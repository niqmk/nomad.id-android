package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.model.CategoryPreview;
import com.allega.nomad.viewgroup.HomeViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class HomeAdapter extends AbstractAdapter<CategoryPreview> {

    public HomeAdapter(Context context, List<CategoryPreview> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        HomeViewGroup homeViewGroup = (HomeViewGroup) convertView;
        homeViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new HomeViewGroup(context);
    }
}
