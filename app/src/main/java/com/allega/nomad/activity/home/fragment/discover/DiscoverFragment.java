package com.allega.nomad.activity.home.fragment.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.DiscoverFragmentStateAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.service.analytic.GAConstant;
import com.allega.nomad.util.TextUtil;
import com.allega.nomad.viewgroup.HomeActionBarViewGroup;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.discover_tab_strip)
    protected PagerSlidingTabStrip homeTabStrip;
    @InjectView(R.id.discover_view_pager)
    protected ViewPager homeViewPager;
    @InjectView(R.id.home_action_bar)
    protected HomeActionBarViewGroup homeActionBar;

    private DiscoverFragmentController controller;

    public static DiscoverFragment newInstance() {
        DiscoverFragment discoverFragment = new DiscoverFragment();
        return discoverFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new DiscoverFragmentController(this, view);
        homeActionBar.setListener(controller);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.DISCOVER_SCREEN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupDiscover(DiscoverFragmentStateAdapter homeAdapter) {
        homeViewPager.setOffscreenPageLimit(9);
        homeViewPager.setAdapter(homeAdapter);
        homeTabStrip.setViewPager(homeViewPager);
        homeTabStrip.setOnPageChangeListener(this);
        homeViewPager.setCurrentItem(0);
        ViewGroup viewGroup = (ViewGroup) homeTabStrip.getChildAt(0);
        TextView textView = (TextView) viewGroup.getChildAt(0);
        textView.setTextColor(getResources().getColor(R.color.red));
        TextUtil.changeFontType(getActivity(), homeTabStrip);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewGroup viewGroup = (ViewGroup) homeTabStrip.getChildAt(0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            TextView textView = (TextView) viewGroup.getChildAt(i);
            if (i == position) {
                textView.setTextColor(getResources().getColor(R.color.red));
            } else {
                textView.setTextColor(getResources().getColor(R.color.black));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
