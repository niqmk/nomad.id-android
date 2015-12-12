package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.fragment.ad.fragment.comment.CommentFragment;
import com.allega.nomad.activity.home.fragment.ad.fragment.detail.DetailFragment;
import com.allega.nomad.activity.home.fragment.ad.fragment.recommended.RecommendedFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class AdFragmentStateAdapter extends FragmentStatePagerAdapter {

    private final long id;
    private String[] titles;

    public AdFragmentStateAdapter(Context context, FragmentManager fm, long id) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.edutainment_tab);
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return CommentFragment.newInstance(id);
            case 2:
                return RecommendedFragment.newInstance(id);
            default:
                return DetailFragment.newInstance(id);
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
