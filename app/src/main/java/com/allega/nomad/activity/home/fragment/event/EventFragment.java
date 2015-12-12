package com.allega.nomad.activity.home.fragment.event;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.EventFragmentStateAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.entity.Event;
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
public class EventFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final String EXTRA_ID = "extra-id";

    @Optional
    @InjectView(R.id.movie_tab_strip)
    protected PagerSlidingTabStrip movieTabStrip;
    @Optional
    @InjectView(R.id.movie_view_pager)
    protected ViewPager movieViewPager;
    @InjectView(R.id.header_view_group)
    protected HeaderActionBarViewGroup headerViewGroup;
    long id;
    private EventController controller;

    public static EventFragment newInstance(Event event) {
        EventFragment eventFragment = new EventFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, event.getId());
        eventFragment.setArguments(bundle);
        return eventFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(EXTRA_ID, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new EventController(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.DETAIL_EVENT_SCREEN);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            headerViewGroup.setVisibility(View.GONE);
            movieTabStrip.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            headerViewGroup.setVisibility(View.VISIBLE);
            movieTabStrip.setVisibility(View.VISIBLE);
        }
    }

    void setupViewPager(EventFragmentStateAdapter movieFragmentStateAdapter) {
        if (movieViewPager != null && movieTabStrip != null) {
            movieViewPager.setAdapter(movieFragmentStateAdapter);
            movieViewPager.setOffscreenPageLimit(5);
            movieTabStrip.setViewPager(movieViewPager);
            movieTabStrip.setOnPageChangeListener(this);
            movieViewPager.setCurrentItem(0);
            ViewGroup viewGroup = (ViewGroup) movieTabStrip.getChildAt(0);
            TextView textView = (TextView) viewGroup.getChildAt(0);
            textView.setTextColor(getResources().getColor(R.color.red));
            TextUtil.changeFontType(getActivity(), movieTabStrip);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewGroup viewGroup = (ViewGroup) movieTabStrip.getChildAt(0);
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
