package com.allega.nomad.activity.home.fragment.tvshow.fragment.detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.TvShowEpisodeListAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.viewgroup.PlayerViewGroup;
import com.allega.nomad.viewgroup.VideoDetailViewGroup;

import org.apache.commons.lang3.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DetailFragment extends BaseFragment {

    private static final String ARGUMENT_EPISODE_ID = "argument-episode-id";

    @InjectView(R.id.player_view_group)
    protected PlayerViewGroup playerViewGroup;
    @InjectView(R.id.video_detail_view_group)
    protected VideoDetailViewGroup videoDetailViewGroup;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;
    @InjectView(R.id.episode_list_view)
    protected ExpandableListView episodeListView;
    @InjectView(R.id.scroll_view)
    protected ScrollView scrollView;
    long id;
    private boolean isFirst = true;
    private DetailFragmentController controller;

    public static DetailFragment newInstance(long episodeId) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_EPISODE_ID, episodeId);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(ARGUMENT_EPISODE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_tv_show_detail, container, false);
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
        episodeListView.setOnGroupClickListener(controller);
        episodeListView.setOnChildClickListener(controller);
    }

    @Override
    public void onDestroyView() {
        Bus.getInstance().unregister(controller);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    void bind(TvShowEpisode tvShowEpisode) {
        id = tvShowEpisode.getId();
        titleTextView.setText(tvShowEpisode.getTitle());
        if (!StringUtils.isEmpty(tvShowEpisode.getDescription())) {
            descriptionTextView.setText(tvShowEpisode.getDescription());
        } else {
            descriptionTextView.setText(R.string.no_info);
        }
        videoDetailViewGroup.bind(tvShowEpisode);
    }

    void setupVideo(TvShowEpisode tvShowEpisode) {
        playerViewGroup.bind(tvShowEpisode);
        videoDetailViewGroup.bind(tvShowEpisode);
        playerViewGroup.bind(tvShowEpisode.getVideos());
    }

    void setupEpisodeList(TvShowEpisodeListAdapter tvShowEpisodeListAdapter) {
        episodeListView.setAdapter(tvShowEpisodeListAdapter);
    }

    void updateEpisodeList(TvShowEpisodeListAdapter tvShowEpisodeListAdapter) {
        tvShowEpisodeListAdapter.notifyDataSetChanged();
        setListViewHeight(episodeListView);
        if (isFirst) {
            episodeListView.performItemClick(episodeListView.getAdapter().getView(0, null, null), 0, 0);
            isFirst = false;
        }
    }

    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public void forcePlay() {
        playerViewGroup.onWatch();
    }

    public void goToTop() {
        scrollView.scrollTo(0, 0);
    }

    void updateLike(TvShowEpisode tvShowEpisode) {
        videoDetailViewGroup.bind(tvShowEpisode);
    }
}
