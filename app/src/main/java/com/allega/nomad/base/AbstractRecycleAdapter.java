package com.allega.nomad.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 7/20/15.
 */
public abstract class AbstractRecycleAdapter<T> extends RecyclerView.Adapter<AbstractRecycleAdapter.ViewHolder> {

    protected LayoutInflater inflater;
    protected List<T> itemList;
    protected Context context;

    public AbstractRecycleAdapter(Context context, List<T> itemList) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
        this.itemList = itemList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public T getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public AbstractRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = onCreate(0, null, parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AbstractRecycleAdapter.ViewHolder holder, int position) {
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
