package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.fragment.search.child.SearchFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SearchFragmentStateAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    public SearchFragmentStateAdapter(Context context, FragmentManager fm) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.search_tab);
    }

    @Override
    public Fragment getItem(int position) {
        return SearchFragment.newInstance(position);
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
