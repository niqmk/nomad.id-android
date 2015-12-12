package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.entity.NomadPoint;
import com.allega.nomad.viewgroup.NomadPointViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class NomadPointAdapter extends AbstractAdapter<NomadPoint> {

    public NomadPointAdapter(Context context, List<NomadPoint> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        NomadPointViewGroup nomadPointViewGroup = (NomadPointViewGroup) convertView;
        nomadPointViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new NomadPointViewGroup(context);
    }
}
