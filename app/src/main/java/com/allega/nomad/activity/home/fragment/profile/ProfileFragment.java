package com.allega.nomad.activity.home.fragment.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.ProfileFragmentStateAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.service.analytic.GAConstant;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ProfileFragment extends BaseFragment {

    private static final String EXTRA_POSITION = "extra-position";

    @InjectView(R.id.profile_view_pager)
    protected ViewPager profileViewPager;
    @InjectView(R.id.profile_tab_strip)
    protected PagerSlidingTabStrip profileTabStrip;

    private ProfileController controller;
    private int position;

    public static ProfileFragment newInstance(int position) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(EXTRA_POSITION, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new ProfileController(this, view);
        profileTabStrip.setOnPageChangeListener(controller);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.PROFILE_SCREEN);
    }

    void setupViewPager(ProfileFragmentStateAdapter adapter) {
        profileViewPager.setOffscreenPageLimit(9);
        profileViewPager.setAdapter(adapter);
        profileTabStrip.setViewPager(profileViewPager);
        ViewGroup child = (ViewGroup) profileTabStrip.getChildAt(0);
        for (int i = 0; i < child.getChildCount(); ++i) {
            View view = child.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.white));
            }
        }
        profileViewPager.setCurrentItem(position);
        updateSelected(position);
    }

    public void updateSelected(int position) {
        ViewGroup tabContainer = (ViewGroup) profileTabStrip.getChildAt(0);
        for (int i = 0; i < tabContainer.getChildCount(); ++i) {
            tabContainer.getChildAt(i).setSelected(i == position);
        }
    }
}
