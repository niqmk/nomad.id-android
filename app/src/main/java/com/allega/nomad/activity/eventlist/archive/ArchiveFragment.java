package com.allega.nomad.activity.eventlist.archive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.MovieListAdapter;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ArchiveFragment extends BaseFragment {


    @InjectView(R.id.movie_list_view)
    ListView movieListView;
    private ArchiveFragmentController controller;

    public static ArchiveFragment newInstance() {
        ArchiveFragment archiveFragment = new ArchiveFragment();
        return archiveFragment;
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
        controller = new ArchiveFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupCategory(MovieListAdapter adapter) {
        movieListView.setAdapter(adapter);
    }
}
