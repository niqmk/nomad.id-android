package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.eventcategory.fragment.category.CategoryFragment;
import com.allega.nomad.activity.eventcategory.fragment.live.LiveFragment;
import com.allega.nomad.activity.eventcategory.fragment.upcoming.UpcomingFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EventCategoryFragmentStateAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    public EventCategoryFragmentStateAdapter(Context context, FragmentManager fm) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.event_category_tab);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return LiveFragment.newInstance();
            case 2:
                return UpcomingFragment.newInstance();
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
