package com.allega.nomad.activity.home.fragment.category;

import android.view.View;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.search.SearchFragment;
import com.allega.nomad.adapter.CategoryFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.viewgroup.HomeActionBarViewGroup;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CategoryFragmentController extends BaseFragmentController<CategoryFragment> implements HomeActionBarViewGroup.HomeActionBarListener {

    private final CategoryFragmentStateAdapter adapter;

    public CategoryFragmentController(CategoryFragment fragment, View view) {
        super(fragment, view);
        adapter = new CategoryFragmentStateAdapter(activity, fragment.getChildFragmentManager(), fragment.menu, fragment.isAd);
        fragment.setupCategory(adapter);
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