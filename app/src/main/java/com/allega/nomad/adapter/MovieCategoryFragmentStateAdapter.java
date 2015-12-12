package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.moviecategory.fragment.genre.GenreFragment;
import com.allega.nomad.activity.moviecategory.fragment.latest.LatestFragment;
import com.allega.nomad.activity.moviecategory.fragment.upcoming.UpcomingFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieCategoryFragmentStateAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    public MovieCategoryFragmentStateAdapter(Context context, FragmentManager fm) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.movie_category_tab);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return LatestFragment.newInstance();
            case 2:
                return UpcomingFragment.newInstance();
            default:
                return GenreFragment.newInstance();
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
