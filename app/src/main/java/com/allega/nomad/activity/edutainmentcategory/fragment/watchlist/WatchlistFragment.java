package com.allega.nomad.activity.edutainmentcategory.fragment.watchlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.EdutainmentListAdapter;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class WatchlistFragment extends BaseFragment {

    @InjectView(R.id.movie_list_view)
    protected ListView movieListView;
    private WatchlistFragmentController controller;

    public static WatchlistFragment newInstance() {
        WatchlistFragment watchlistFragment = new WatchlistFragment();
        return watchlistFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new WatchlistFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupCategory(EdutainmentListAdapter adapter) {
        movieListView.setAdapter(adapter);
    }
}
