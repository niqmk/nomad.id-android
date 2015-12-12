package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.TvShow;
import com.allega.nomad.viewgroup.TvShowCategoryViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class TvShowCategoryAdapter extends AbstractRecycleAdapter<TvShow> {

    public TvShowCategoryAdapter(Context context, List<TvShow> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        TvShowCategoryViewGroup tvShowCategoryViewGroup = (TvShowCategoryViewGroup) convertView;
        tvShowCategoryViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new TvShowCategoryViewGroup(context);
    }
}
