package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.viewgroup.EdutainmentCategoryViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EdutainmentCategoryAdapter extends AbstractRecycleAdapter<Edutainment> {

    public EdutainmentCategoryAdapter(Context context, List<Edutainment> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        EdutainmentCategoryViewGroup edutainmentCategoryViewGroup = (EdutainmentCategoryViewGroup) convertView;
        edutainmentCategoryViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new EdutainmentCategoryViewGroup(context);
    }
}
