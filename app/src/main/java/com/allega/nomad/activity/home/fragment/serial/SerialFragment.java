package com.allega.nomad.activity.home.fragment.serial;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.SerialFragmentStateAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.entity.Serial;
import com.allega.nomad.entity.SerialEpisode;
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
public class SerialFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final String EXTRA_ID = "extra-id";
    private static final String EXTRA_EPISODE_ID = "extra-episode-id";

    @Optional
    @InjectView(R.id.serial_tab_strip)
    protected PagerSlidingTabStrip serialTabStrip;
    @Optional
    @InjectView(R.id.serial_view_pager)
    protected ViewPager serialViewPager;
    @InjectView(R.id.header_view_group)
    protected HeaderActionBarViewGroup headerViewGroup;
    long id;
    long episodeId;
    private SerialController controller;

    public static SerialFragment newInstance(Serial serial) {
        SerialFragment serialFragment = new SerialFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, serial.getId());
        bundle.putLong(EXTRA_EPISODE_ID, serial.getFirstEpisodeId());
        serialFragment.setArguments(bundle);
        return serialFragment;
    }

    public static SerialFragment newInstance(SerialEpisode serialEpisode) {
        SerialFragment serialFragment = new SerialFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, serialEpisode.getTvShowId());
        bundle.putLong(EXTRA_EPISODE_ID, serialEpisode.getId());
        serialFragment.setArguments(bundle);
        return serialFragment;
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
        View view = inflater.inflate(R.layout.activity_serial, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new SerialController(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.DETAIL_SERIAL_SCREEN);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            headerViewGroup.setVisibility(View.GONE);
            serialTabStrip.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            headerViewGroup.setVisibility(View.VISIBLE);
            serialTabStrip.setVisibility(View.VISIBLE);
        }
    }

    void setupViewPager(SerialFragmentStateAdapter serialFragmentStateAdapter) {
        serialFragmentStateAdapter.setEpisodeId(episodeId);
        if (serialViewPager != null && serialTabStrip != null) {
            serialViewPager.setAdapter(serialFragmentStateAdapter);
            serialViewPager.setOffscreenPageLimit(5);
            serialTabStrip.setViewPager(serialViewPager);
            serialTabStrip.setOnPageChangeListener(this);
            serialViewPager.setCurrentItem(0);
            ViewGroup viewGroup = (ViewGroup) serialTabStrip.getChildAt(0);
            TextView textView = (TextView) viewGroup.getChildAt(0);
            textView.setTextColor(getResources().getColor(R.color.red));
            TextUtil.changeFontType(getActivity(), serialTabStrip);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewGroup viewGroup = (ViewGroup) serialTabStrip.getChildAt(0);
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
