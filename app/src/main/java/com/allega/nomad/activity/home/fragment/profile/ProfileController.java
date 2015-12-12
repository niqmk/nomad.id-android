package com.allega.nomad.activity.home.fragment.profile;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.allega.nomad.adapter.ProfileFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ProfileController extends BaseFragmentController<ProfileFragment> implements ViewPager.OnPageChangeListener {

    private final ProfileFragmentStateAdapter adapter;

    public ProfileController(ProfileFragment fragment, View view) {
        super(fragment, view);
        adapter = new ProfileFragmentStateAdapter(fragment.getActivity(), fragment.getChildFragmentManager());
        fragment.setupViewPager(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        fragment.updateSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
