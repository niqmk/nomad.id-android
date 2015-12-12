package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.model.FavoriteVideo;
import com.allega.nomad.viewgroup.ProfileFavoriteViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ProfileFavoriteAdapter extends AbstractAdapter<FavoriteVideo> {

    public ProfileFavoriteAdapter(Context context, List<FavoriteVideo> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        ProfileFavoriteViewGroup viewGroup = (ProfileFavoriteViewGroup) convertView;
        viewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new ProfileFavoriteViewGroup(context);
    }
}
