package com.allega.nomad.activity.home.fragment.discover;

import android.view.View;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.search.SearchFragment;
import com.allega.nomad.adapter.DiscoverFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.viewgroup.HomeActionBarViewGroup;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverFragmentController extends BaseFragmentController<DiscoverFragment> implements HomeActionBarViewGroup.HomeActionBarListener {

    private final DiscoverFragmentStateAdapter adapter;

    public DiscoverFragmentController(DiscoverFragment fragment, View view) {
        super(fragment, view);
        adapter = new DiscoverFragmentStateAdapter(activity, fragment.getChildFragmentManager());
        fragment.setupDiscover(adapter);
    }

    @Override
    public void onDrawer() {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.showDrawer();
        }
    }

    @Override
    public void onSearch() {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(SearchFragment.newInstance());
        }
    }
}