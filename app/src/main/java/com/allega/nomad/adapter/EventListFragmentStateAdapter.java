package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.eventlist.archive.ArchiveFragment;
import com.allega.nomad.activity.eventlist.live.LiveFragment;
import com.allega.nomad.activity.eventlist.upcoming.UpcomingFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EventListFragmentStateAdapter extends FragmentStatePagerAdapter {

    private final long id;
    private String[] titles;

    public EventListFragmentStateAdapter(Context context, FragmentManager fm, long id) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.event_list_tab);
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return UpcomingFragment.newInstance();
            case 2:
                return ArchiveFragment.newInstance();
            default:
                return LiveFragment.newInstance(id);
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
