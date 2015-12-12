package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.viewgroup.MovieCategoryViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieCategoryAdapter extends AbstractRecycleAdapter<Movie> {

    public MovieCategoryAdapter(Context context, List<Movie> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        MovieCategoryViewGroup movieCategoryViewGroup = (MovieCategoryViewGroup) convertView;
        movieCategoryViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new MovieCategoryViewGroup(context);
    }
}
