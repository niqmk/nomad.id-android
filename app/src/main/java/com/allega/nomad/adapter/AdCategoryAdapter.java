package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.viewgroup.AdCategoryViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class AdCategoryAdapter extends AbstractRecycleAdapter<Ad> {

    public AdCategoryAdapter(Context context, List<Ad> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        AdCategoryViewGroup adCategoryViewGroup = (AdCategoryViewGroup) convertView;
        adCategoryViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new AdCategoryViewGroup(context);
    }
}
