package com.allega.nomad.activity.home.fragment.discover.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.allega.nomad.R;
import com.allega.nomad.adapter.DiscoverAdAdapter;
import com.allega.nomad.adapter.DiscoverEdutainmentAdapter;
import com.allega.nomad.adapter.DiscoverEventAdapter;
import com.allega.nomad.adapter.DiscoverLiveChannelAdapter;
import com.allega.nomad.adapter.DiscoverMovieAdapter;
import com.allega.nomad.adapter.DiscoverSerialAdapter;
import com.allega.nomad.adapter.DiscoverSerialEpisodeAdapter;
import com.allega.nomad.adapter.DiscoverTvShowAdapter;
import com.allega.nomad.adapter.DiscoverTvShowEpisodeAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.model.Slider;
import com.allega.nomad.viewgroup.DiscoverSliderViewGroup;
import com.allega.nomad.viewgroup.SquareViewFlipper;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.GridLayoutManager;
import org.lucasr.twowayview.widget.ListLayoutManager;
import org.lucasr.twowayview.widget.SpacingItemDecoration;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverChildFragment extends BaseFragment {

    private static final String EXTRA_POSITION = "extra-position";

    @InjectView(R.id.view_flipper)
    protected SquareViewFlipper viewFlipper;
    @InjectView(R.id.movie_list_view)
    protected TwoWayView movieListView;
    @InjectView(R.id.movie_layout)
    protected LinearLayout movieLayout;
    @InjectView(R.id.event_list_view)
    protected TwoWayView eventListView;
    @InjectView(R.id.event_layout)
    protected LinearLayout eventLayout;
    @InjectView(R.id.edutainment_list_view)
    protected TwoWayView edutainmentListView;
    @InjectView(R.id.edutainment_layout)
    protected LinearLayout edutainmentLayout;
    @InjectView(R.id.ad_list_view)
    protected TwoWayView adListView;
    @InjectView(R.id.ad_layout)
    protected LinearLayout adLayout;
    @InjectView(R.id.tv_show_list_view)
    protected TwoWayView tvShowListView;
    @InjectView(R.id.tv_show_layout)
    protected LinearLayout tvShowLayout;
    @InjectView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @InjectView(R.id.live_channel_list_view)
    protected TwoWayView liveChannelListView;
    @InjectView(R.id.live_channel_layout)
    protected LinearLayout liveChannelLayout;
    @InjectView(R.id.serial_list_view)
    protected TwoWayView serialListView;
    @InjectView(R.id.serial_layout)
    protected LinearLayout serialLayout;
    int position;
    private DiscoverChildFragmentController controller;

    public static DiscoverChildFragment newInstance(int position) {
        DiscoverChildFragment discoverChildFragment = new DiscoverChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        discoverChildFragment.setArguments(bundle);
        return discoverChildFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(EXTRA_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_child, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (position == 0) {
            viewFlipper.setInAnimation(getActivity(), R.anim.right_in);
            viewFlipper.setOutAnimation(getActivity(), R.anim.left_out);
        }
        controller = new DiscoverChildFragmentController(this, view);
        controller.setupList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void setupSlider(List<Slider> sliderList) {
        for (Slider slider : sliderList) {
            DiscoverSliderViewGroup discoverSliderViewGroup = new DiscoverSliderViewGroup(getActivity());
            viewFlipper.addView(discoverSliderViewGroup);
            discoverSliderViewGroup.bind(slider);
        }
        if (sliderList.size() > 0) {
            viewFlipper.setVisibility(View.VISIBLE);
            viewFlipper.startFlipping();
        }
    }

    void setupMovieList(DiscoverMovieAdapter discoverMovieAdapter) {
        movieListView.setLayoutManager(new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL));
        movieListView.setAdapter(discoverMovieAdapter);
        movieListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(movieListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        movieListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateMovieList(DiscoverMovieAdapter discoverMovieAdapter) {
        discoverMovieAdapter.notifyDataSetChanged();
        if (discoverMovieAdapter.getItemCount() > 0) {
            movieLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            movieLayout.setVisibility(View.GONE);
        }
    }

    void setupEventList(DiscoverEventAdapter discoverEventAdapter) {
        eventListView.setLayoutManager(new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL));
        eventListView.setAdapter(discoverEventAdapter);
        eventListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(eventListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        eventListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateEventList(DiscoverEventAdapter discoverEventAdapter) {
        discoverEventAdapter.notifyDataSetChanged();
        if (discoverEventAdapter.getItemCount() > 0) {
            eventLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            eventLayout.setVisibility(View.GONE);
        }
    }

    void setupEdutainmentList(DiscoverEdutainmentAdapter discoverEdutainmentAdapter) {
        GridLayoutManager listLayoutManager = new GridLayoutManager(TwoWayLayoutManager.Orientation.HORIZONTAL, 2, 2);
        edutainmentListView.setLayoutManager(listLayoutManager);
        edutainmentListView.setAdapter(discoverEdutainmentAdapter);
        edutainmentListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(edutainmentListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        edutainmentListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateEdutainmentList(DiscoverEdutainmentAdapter discoverEdutainmentAdapter) {
        discoverEdutainmentAdapter.notifyDataSetChanged();
        if (discoverEdutainmentAdapter.getItemCount() > 0) {
            edutainmentLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            edutainmentLayout.setVisibility(View.GONE);
        }
    }

    void setupTvShowList(DiscoverTvShowAdapter discoverTvShowAdapter) {
        tvShowListView.setLayoutManager(new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL));
        tvShowListView.setAdapter(discoverTvShowAdapter);
        tvShowListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(tvShowListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        tvShowListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateTvShowList(DiscoverTvShowAdapter discoverTvShowAdapter) {
        discoverTvShowAdapter.notifyDataSetChanged();
        if (discoverTvShowAdapter.getItemCount() > 0) {
            tvShowLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            tvShowLayout.setVisibility(View.GONE);
        }
    }

    void setupTvShowEpisodeList(DiscoverTvShowEpisodeAdapter discoverTvShowEpisodeAdapter) {
        tvShowListView.setLayoutManager(new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL));
        tvShowListView.setAdapter(discoverTvShowEpisodeAdapter);
        tvShowListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(tvShowListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        tvShowListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateTvShowEpisodeList(DiscoverTvShowEpisodeAdapter discoverTvShowEpisodeAdapter) {
        discoverTvShowEpisodeAdapter.notifyDataSetChanged();
        if (discoverTvShowEpisodeAdapter.getItemCount() > 0) {
            tvShowLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            tvShowLayout.setVisibility(View.GONE);
        }
    }

    void setupLiveChannelList(DiscoverLiveChannelAdapter discoverLiveChannelAdapter) {
        liveChannelListView.setLayoutManager(new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL));
        liveChannelListView.setAdapter(discoverLiveChannelAdapter);
        liveChannelListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(liveChannelListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        liveChannelListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateLiveChannelList(DiscoverLiveChannelAdapter discoverLiveChannelAdapter) {
        discoverLiveChannelAdapter.notifyDataSetChanged();
        if (discoverLiveChannelAdapter.getItemCount() > 0) {
            liveChannelLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            liveChannelLayout.setVisibility(View.GONE);
        }
    }

    void setupSerialList(DiscoverSerialAdapter discoverSerialAdapter) {
        serialListView.setLayoutManager(new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL));
        serialListView.setAdapter(discoverSerialAdapter);
        serialListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(serialListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        serialListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateSerialList(DiscoverSerialAdapter discoverSerialAdapter) {
        discoverSerialAdapter.notifyDataSetChanged();
        if (discoverSerialAdapter.getItemCount() > 0) {
            serialLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            serialLayout.setVisibility(View.GONE);
        }
    }

    void setupSerialEpisodeList(DiscoverSerialEpisodeAdapter discoverSerialEpisodeAdapter) {
        serialListView.setLayoutManager(new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL));
        serialListView.setAdapter(discoverSerialEpisodeAdapter);
        serialListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(serialListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        serialListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateSerialEpisodeList(DiscoverSerialEpisodeAdapter discoverSerialEpisodeAdapter) {
        discoverSerialEpisodeAdapter.notifyDataSetChanged();
        if (discoverSerialEpisodeAdapter.getItemCount() > 0) {
            serialLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            serialLayout.setVisibility(View.GONE);
        }
    }

    void setupAdList(DiscoverAdAdapter discoverAdAdapter) {
        adListView.setLayoutManager(new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL));
        adListView.setAdapter(discoverAdAdapter);
        adListView.setHasFixedSize(true);

        ItemClickSupport itemClick = ItemClickSupport.addTo(adListView);
        itemClick.setOnItemClickListener(controller);

        int size = (int) getResources().getDimension(R.dimen.margin_x_small);
        adListView.addItemDecoration(new SpacingItemDecoration(size, size));
    }

    void updateAdList(DiscoverAdAdapter discoverAdAdapter) {
        discoverAdAdapter.notifyDataSetChanged();
        if (discoverAdAdapter.getItemCount() > 0) {
            adLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            adLayout.setVisibility(View.GONE);
        }
    }

}
