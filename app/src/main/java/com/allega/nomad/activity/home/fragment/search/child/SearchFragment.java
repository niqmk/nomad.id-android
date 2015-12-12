package com.allega.nomad.activity.home.fragment.search.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.SearchAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.SearchEvent;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SearchFragment extends BaseFragment {

    private static final String ARGUMENT_POSITION = "argument-position";
    @InjectView(R.id.search_list_view)
    protected ListView searchListView;
    @InjectView(R.id.total_search_text_view)
    protected TextView totalSearchTextView;
    @InjectView(R.id.progress_bar)
    protected ProgressBar progressBar;

    int position;
    private SearchFragmentController controller;

    public static SearchFragment newInstance(int position) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_POSITION, position);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARGUMENT_POSITION);
        Bus.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (position) {
            case 1:
                controller = new EventFragmentController(this, view);
                break;
            case 2:
                controller = new EdutainmentFragmentController(this, view);
                break;
            case 3:
                controller = new TvShowEpisodeFragmentController(this, view);
                break;
            case 4:
                controller = new LiveChannelFragmentController(this, view);
                break;
            case 5:
                controller = new SerialEpisodeFragmentController(this, view);
                break;
            case 6:
                controller = new AdFragmentController(this, view);
                break;
            default:
                controller = new MovieFragmentController(this, view);
                break;
        }
    }

    void updateResult(String query, int size) {
        totalSearchTextView.setText(getString(R.string.total_search, query, size));
        totalSearchTextView.setVisibility(View.VISIBLE);
    }

    public void onEvent(SearchEvent searchEvent) {
        String query = searchEvent.getQuery();
        if (query != null)
            controller.newSearch(query);
    }

    @Override
    public void onDestroyView() {
        Bus.getInstance().unregister(this);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    void setupList(SearchAdapter adapter) {
        searchListView.setAdapter(adapter);
    }

    void showProgressBar(boolean isShow) {
        if (isShow) {
            progressBar.setVisibility(View.VISIBLE);
            totalSearchTextView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
