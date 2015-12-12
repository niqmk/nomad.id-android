package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.edutainmentcategory.fragment.category.CategoryFragment;
import com.allega.nomad.activity.edutainmentcategory.fragment.popular.PopularFragment;
import com.allega.nomad.activity.edutainmentcategory.fragment.watchlist.WatchlistFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EdutainmentCategoryFragmentStateAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    public EdutainmentCategoryFragmentStateAdapter(Context context, FragmentManager fm) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.edutainment_category_tab);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return PopularFragment.newInstance();
            case 2:
                return WatchlistFragment.newInstance();
            default:
                return CategoryFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
