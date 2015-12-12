package com.allega.nomad.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.storage.DatabaseManager;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by imnotpro on 7/20/15.
 */
public abstract class RealmRecycleAdapter<T extends RealmObject> extends RecyclerView.Adapter<RealmRecycleAdapter.ViewHolder> {

    protected LayoutInflater inflater;
    protected RealmResults<T> realmResults;
    protected Context context;

    public RealmRecycleAdapter(Context context, RealmResults<T> realmResults) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        if (realmResults == null) {
            throw new IllegalArgumentException("RealmResults cannot be null");
        }
        this.context = context;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
        Realm.getInstance(DatabaseManager.getConfiguration(context)).addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        });
    }

    public RealmRecycleAdapter(Context context, RealmResults<T> realmResults, boolean autoRefresh) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        if (realmResults == null) {
            throw new IllegalArgumentException("RealmResults cannot be null");
        }
        this.context = context;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
        if (autoRefresh)
            DatabaseManager.getInstance(context).addChangeListener(new RealmChangeListener() {
                @Override
                public void onChange() {
                    notifyDataSetChanged();
                }
            });
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public T getItem(int i) {
        return realmResults.get(i);
    }

    @Override
    public RealmRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = onCreate(0, null, parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RealmRecycleAdapter.ViewHolder holder, int position) {
        onBind(position, holder.view, null);
    }

    protected abstract void onBind(int position, View convertView, ViewGroup parent);

    protected abstract View onCreate(int position, View convertView, ViewGroup parent);

    static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }
}
