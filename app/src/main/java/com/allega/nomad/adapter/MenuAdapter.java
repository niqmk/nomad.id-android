package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.model.Menu;
import com.allega.nomad.viewgroup.MenuViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 7/21/15.
 */
public class MenuAdapter extends AbstractAdapter<Menu> {

    private int currentSelected = -1;

    public MenuAdapter(Context context, List<Menu> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        MenuViewGroup menuViewGroup = (MenuViewGroup) convertView;
        menuViewGroup.setActivated(currentSelected == position);
        menuViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new MenuViewGroup(context);
    }

    public int getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(int currentSelected) {
        this.currentSelected = currentSelected;
    }
}
