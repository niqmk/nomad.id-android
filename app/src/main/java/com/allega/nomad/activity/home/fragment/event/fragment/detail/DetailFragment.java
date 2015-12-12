package com.allega.nomad.activity.home.fragment.event.fragment.detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.entity.Event;
import com.allega.nomad.viewgroup.PlayerViewGroup;
import com.allega.nomad.viewgroup.VideoDetailViewGroup;

import org.apache.commons.lang3.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DetailFragment extends BaseFragment {

    private static final String ARGUMENT_ID = "argument-id";

    @InjectView(R.id.player_view_group)
    protected PlayerViewGroup playerViewGroup;
    @InjectView(R.id.video_detail_view_group)
    protected VideoDetailViewGroup videoDetailViewGroup;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;
    @InjectView(R.id.scroll_view)
    protected ScrollView scrollView;

    long id;
    private DetailFragmentController controller;

    public static DetailFragment newInstance(long id) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_ID, id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(ARGUMENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            scrollView.setVisibility(View.GONE);
            videoDetailViewGroup.setVisibility(View.GONE);
            titleTextView.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            scrollView.setVisibility(View.VISIBLE);
            videoDetailViewGroup.setVisibility(View.VISIBLE);
            titleTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        playerViewGroup.stopTracking();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new DetailFragmentController(this, view);
        Bus.getInstance().register(controller);
        videoDetailViewGroup.setVideoDetailListener(controller);
        playerViewGroup.setListener(controller, getChildFragmentManager());
    }

    @Override
    public void onDestroyView() {
        Bus.getInstance().unregister(controller);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    void bind(Event event) {
        titleTextView.setText(event.getTitle());
        if (!StringUtils.isEmpty(event.getDescription())) {
            descriptionTextView.setText(event.getDescription());
        } else {
            descriptionTextView.setText(R.string.no_info);
        }
        videoDetailViewGroup.bind(event);
    }

    void setupVideo(Event event) {
        playerViewGroup.bind(event);
        videoDetailViewGroup.bind(event);
        playerViewGroup.bind(event.getVideos());
    }

    public void forcePlay() {
        playerViewGroup.onWatch();
    }

    void updateLike(Event event) {
        videoDetailViewGroup.bind(event);
    }
}
