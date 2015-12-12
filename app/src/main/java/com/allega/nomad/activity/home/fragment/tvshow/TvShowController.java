package com.allega.nomad.activity.home.fragment.tvshow;

import android.view.View;

import com.allega.nomad.adapter.TvShowFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class TvShowController extends BaseFragmentController<TvShowFragment> {

    private final TvShowFragmentStateAdapter adapter;

    public TvShowController(TvShowFragment fragment, View view) {
        super(fragment, view);
        adapter = new TvShowFragmentStateAdapter(context, fragment.getChildFragmentManager(), fragment.id);
        fragment.setupViewPager(adapter);
    }

}
