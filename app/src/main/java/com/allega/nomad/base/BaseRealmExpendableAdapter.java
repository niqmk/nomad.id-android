package com.allega.nomad.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.allega.nomad.storage.DatabaseManager;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by imnotpro on 8/9/15.
 */
public abstract class BaseRealmExpendableAdapter<G extends RealmObject, C extends RealmObject> extends BaseExpandableListAdapter {

    protected LayoutInflater inflater;
    protected RealmResults<G> realmResults;
    protected Context context;

    public BaseRealmExpendableAdapter(Context context, RealmResults<G> realmResults, boolean automaticUpdate) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        if (realmResults == null) {
            throw new IllegalArgumentException("RealmResults cannot be null");
        }

        this.context = context;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
        if (automaticUpdate) {
            Realm realm = DatabaseManager.getInstance(context);
            realm.addChangeListener(new RealmChangeListener() {
                @Override
                public void onChange() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public G getGroup(int groupPosition) {
        return realmResults.get(groupPosition);
    }

    @Override
    public C getChild(int groupPosition, int childPosition) {
        return getChild(realmResults.get(groupPosition), childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = onCreateGroupView(groupPosition, isExpanded, convertView, parent);
        }
        onBindGroupView(groupPosition, isExpanded, convertView, parent);
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = onCreateChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        }
        onBindChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        return convertView;
    }

    protected abstract void onBindGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    protected abstract View onCreateGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    protected abstract void onBindChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

    protected abstract View onCreateChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

    public abstract C getChild(G group, int childPosition);
}
