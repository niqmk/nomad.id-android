package com.allega.nomad.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imnotpro on 5/4/15.
 */
public abstract class AbstractAdapter<T> extends BaseAdapter {

    protected Context context;
    private List<T> items = new ArrayList<T>();

    public AbstractAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
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