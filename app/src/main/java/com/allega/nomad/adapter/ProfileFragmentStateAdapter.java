package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.fragment.profile.child.collection.CollectionFragment;
import com.allega.nomad.activity.home.fragment.profile.child.favorite.FavoriteFragment;
import com.allega.nomad.activity.home.fragment.profile.child.message.MessageFragment;
import com.allega.nomad.activity.home.fragment.profile.child.profile.ProfileFragment;
import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ProfileFragmentStateAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {


    public ProfileFragmentStateAdapter(Context context, FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return MessageFragment.newInstance();
            case 2:
                return FavoriteFragment.newInstance();
            case 3:
                return CollectionFragment.newInstance();
            default:
                return ProfileFragment.newInstance();
        }
    }

    @Override
    public int getPageIconResId(int position) {
        switch (position) {
            case 1:
                return R.drawable.ic_comment;
            case 2:
                return R.drawable.ic_favorite;
            case 3:
                return R.drawable.ic_download;
            default:
                return R.drawable.ic_user_profile;
        }
    }

}
