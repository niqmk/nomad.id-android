package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.RealmAdapter;
import com.allega.nomad.entity.EdutainmentPreview;
import com.allega.nomad.entity.EventPreview;
import com.allega.nomad.entity.MoviePreview;
import com.allega.nomad.viewgroup.CategoryViewGroup;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CategoryAdapter extends RealmAdapter {

    public CategoryAdapter(Context context, RealmResults items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        CategoryViewGroup categoryViewGroup = (CategoryViewGroup) convertView;
        RealmObject realmObject = getItem(position);
        if (realmObject instanceof MoviePreview) {
            categoryViewGroup.bind((MoviePreview) realmObject);
        } else if (realmObject instanceof EventPreview) {
            categoryViewGroup.bind((EventPreview) realmObject);
        } else if (realmObject instanceof EdutainmentPreview) {
            categoryViewGroup.bind((EdutainmentPreview) realmObject);
        }
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new CategoryViewGroup(context);
    }
}
