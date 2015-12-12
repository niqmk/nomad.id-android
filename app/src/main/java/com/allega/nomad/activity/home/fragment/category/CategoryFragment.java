package com.allega.nomad.activity.home.fragment.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.CategoryFragmentStateAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.util.TextUtil;
import com.allega.nomad.viewgroup.HomeActionBarViewGroup;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CategoryFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final String ARGUMENT_MENU = "argument-menu";
    private static final String ARGUMENT_POSITION = "argument-position";
    private static final String ARGUMENT_AD = "argument-ad";

    @InjectView(R.id.category_tab_strip)
    protected PagerSlidingTabStrip categoryTabStrip;
    @InjectView(R.id.category_view_pager)
    protected ViewPager categoryViewPager;
    @InjectView(R.id.home_action_bar)
    protected HomeActionBarViewGroup homeActionBar;

    int menu;
    int position;
    boolean isAd;
    private CategoryFragmentController controller;

    public static CategoryFragment newInstance(int currentMenu, int position, boolean isAd) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_MENU, currentMenu);
        bundle.putInt(ARGUMENT_POSITION, position);
        bundle.putBoolean(ARGUMENT_AD, isAd);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menu = getArguments().getInt(ARGUMENT_MENU);
        position = getArguments().getInt(ARGUMENT_POSITION);
        isAd = getArguments().getBoolean(ARGUMENT_AD, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_category, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new CategoryFragmentController(this, view);
        homeActionBar.setListener(controller);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupCategory(CategoryFragmentStateAdapter categoryFragmentStateAdapter) {
        categoryFragmentStateAdapter.setup(menu, position, isAd);
        categoryViewPager.setOffscreenPageLimit(9);
        categoryViewPager.setAdapter(categoryFragmentStateAdapter);
        categoryTabStrip.setViewPager(categoryViewPager);
        categoryTabStrip.setOnPageChangeListener(this);
        categoryViewPager.setCurrentItem(0);
        ViewGroup viewGroup = (ViewGroup) categoryTabStrip.getChildAt(0);
        TextView textView = (TextView) viewGroup.getChildAt(0);
        textView.setTextColor(getResources().getColor(R.color.red));
        TextUtil.changeFontType(getActivity(), categoryTabStrip);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewGroup viewGroup = (ViewGroup) categoryTabStrip.getChildAt(0);
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
