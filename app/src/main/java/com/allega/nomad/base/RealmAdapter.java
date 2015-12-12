package com.allega.nomad.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/4/15.
 */
public abstract class RealmAdapter<T extends RealmObject> extends RealmBaseAdapter<T> implements ListAdapter {

    public RealmAdapter(Context context, RealmResults realmResults) {
        super(context, realmResults, true);
    }

    public RealmAdapter(Context context, RealmResults realmResults, boolean autoRefresh) {
        super(context, realmResults, autoRefresh);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = onCreate(position, convertView, parent);
        }
        onBind(position, convertView, parent);
        return convertView;
    }

    protected abstract void onBind(int position, View convertView, ViewGroup parent);

    protected abstract View onCreate(int position, View convertView, ViewGroup parent);

}