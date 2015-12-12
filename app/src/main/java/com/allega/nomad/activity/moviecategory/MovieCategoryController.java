package com.allega.nomad.activity.moviecategory;

import com.allega.nomad.adapter.MovieCategoryFragmentStateAdapter;
import com.allega.nomad.base.BaseController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieCategoryController extends BaseController<MovieCategoryActivity> {

    private final MovieCategoryFragmentStateAdapter adapter;

    public MovieCategoryController(MovieCategoryActivity activity) {
        super(activity);
        adapter = new MovieCategoryFragmentStateAdapter(activity, activity.getSupportFragmentManager());
        activity.setupViewPager(adapter);
    }
}
