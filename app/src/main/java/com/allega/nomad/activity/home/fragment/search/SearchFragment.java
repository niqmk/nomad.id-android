package com.allega.nomad.activity.home.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.SearchAdapter;
import com.allega.nomad.adapter.SearchFragmentStateAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.util.TextUtil;
import com.allega.nomad.viewgroup.HeaderSearchViewGroup;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SearchFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.search_tab_strip)
    protected PagerSlidingTabStrip searchTabStrip;
    @InjectView(R.id.search_view_pager)
    protected ViewPager searchViewPager;
    @InjectView(R.id.clear_button)
    protected TextView clearButton;
    @InjectView(R.id.recent_list_view)
    protected ListView recentListView;
    @InjectView(R.id.recent_container)
    protected LinearLayout recentContainer;
    @InjectView(R.id.header_search_view_group)
    protected HeaderSearchViewGroup headerSearchViewGroup;
    @InjectView(R.id.search_view)
    protected SearchView searchView;
    private SearchController controller;

    public static SearchFragment newInstance() {
        SearchFragment searchFragment = new SearchFragment();
        return searchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new SearchController(this, view);
        headerSearchViewGroup.setOnQueryTextListener(controller);
    }

    public void setupViewPager(SearchFragmentStateAdapter searchFragmentStateAdapter, SearchAdapter recentlySearchAdapter) {
        searchViewPager.setOffscreenPageLimit(15);
        searchViewPager.setAdapter(searchFragmentStateAdapter);
        searchTabStrip.setViewPager(searchViewPager);

        recentListView.setAdapter(recentlySearchAdapter);
        searchTabStrip.setOnPageChangeListener(this);
        searchViewPager.setCurrentItem(0);
        ViewGroup viewGroup = (ViewGroup) searchTabStrip.getChildAt(0);
        TextView textView = (TextView) viewGroup.getChildAt(0);
        textView.setTextColor(getResources().getColor(R.color.red));
        TextUtil.changeFontType(getActivity(), searchTabStrip);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker("Screen Screen");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewGroup viewGroup = (ViewGroup) searchTabStrip.getChildAt(0);
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

    public void hideRecentlySearch() {
        searchView.clearFocus();
        recentContainer.setVisibility(View.GONE);
    }

    public void showRecentlySearch() {
        recentContainer.setVisibility(View.VISIBLE);
    }

    public void updateQuery(String query) {
        searchView.setQuery(query, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
