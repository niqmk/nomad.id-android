package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.Serial;
import com.allega.nomad.viewgroup.SerialCategoryViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SerialCategoryAdapter extends AbstractRecycleAdapter<Serial> {

    public SerialCategoryAdapter(Context context, List<Serial> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        SerialCategoryViewGroup serialCategoryViewGroup = (SerialCategoryViewGroup) convertView;
        serialCategoryViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new SerialCategoryViewGroup(context);
    }
}
