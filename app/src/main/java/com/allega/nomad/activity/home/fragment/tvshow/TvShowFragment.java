package com.allega.nomad.activity.home.fragment.tvshow;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.TvShowFragmentStateAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.entity.TvShow;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.service.analytic.GAConstant;
import com.allega.nomad.util.TextUtil;
import com.allega.nomad.viewgroup.HeaderActionBarViewGroup;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by imnotpro on 5/30/15.
 */
public class TvShowFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final String EXTRA_ID = "extra-id";
    private static final String EXTRA_EPISODE_ID = "extra-episode-id";

    @Optional
    @InjectView(R.id.tv_show_tab_strip)
    protected PagerSlidingTabStrip tvShowTabStrip;
    @Optional
    @InjectView(R.id.tv_show_view_pager)
    protected ViewPager tvShowViewPager;
    @InjectView(R.id.header_view_group)
    protected HeaderActionBarViewGroup headerViewGroup;
    long id;
    long episodeId;
    private TvShowController controller;

    public static TvShowFragment newInstance(TvShow tvShow) {
        TvShowFragment tvShowFragment = new TvShowFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, tvShow.getId());
        bundle.putLong(EXTRA_EPISODE_ID, tvShow.getFirstEpisodeId());
        tvShowFragment.setArguments(bundle);
        return tvShowFragment;
    }

    public static TvShowFragment newInstance(TvShowEpisode tvShowEpisode) {
        TvShowFragment tvShowFragment = new TvShowFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, tvShowEpisode.getTvShowId());
        bundle.putLong(EXTRA_EPISODE_ID, tvShowEpisode.getId());
        tvShowFragment.setArguments(bundle);
        return tvShowFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(EXTRA_ID, 0);
        episodeId = getArguments().getLong(EXTRA_EPISODE_ID, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tv_show, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new TvShowController(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.DETAIL_TV_SHOW_SCREEN);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            headerViewGroup.setVisibility(View.GONE);
            tvShowTabStrip.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            headerViewGroup.setVisibility(View.VISIBLE);
            tvShowTabStrip.setVisibility(View.VISIBLE);
        }
    }

    void setupViewPager(TvShowFragmentStateAdapter tvShowFragmentStateAdapter) {
        tvShowFragmentStateAdapter.setEpisodeId(episodeId);
        if (tvShowViewPager != null && tvShowTabStrip != null) {
            tvShowViewPager.setAdapter(tvShowFragmentStateAdapter);
            tvShowViewPager.setOffscreenPageLimit(5);
            tvShowTabStrip.setViewPager(tvShowViewPager);
            tvShowTabStrip.setOnPageChangeListener(this);
            tvShowViewPager.setCurrentItem(0);
            ViewGroup viewGroup = (ViewGroup) tvShowTabStrip.getChildAt(0);
            TextView textView = (TextView) viewGroup.getChildAt(0);
            textView.setTextColor(getResources().getColor(R.color.red));
            TextUtil.changeFontType(getActivity(), tvShowTabStrip);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewGroup viewGroup = (ViewGroup) tvShowTabStrip.getChildAt(0);
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
