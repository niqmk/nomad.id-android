package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.fragment.tvshow.fragment.comment.CommentFragment;
import com.allega.nomad.activity.home.fragment.tvshow.fragment.detail.DetailFragment;
import com.allega.nomad.activity.home.fragment.tvshow.fragment.recommended.RecommendedFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class TvShowFragmentStateAdapter extends FragmentStatePagerAdapter {

    private final long id;
    private long episodeId;
    private String[] titles;

    public TvShowFragmentStateAdapter(Context context, FragmentManager fm, long id) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.tv_show_tab);
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return CommentFragment.newInstance(episodeId);
            case 2:
                return RecommendedFragment.newInstance(id);
            default:
                return DetailFragment.newInstance(episodeId);
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

    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }
}
