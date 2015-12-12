package com.allega.nomad.activity.eventlist.archive;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.adapter.MovieListAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ArchiveFragmentController extends BaseFragmentController<ArchiveFragment> {

    private final MovieListAdapter adapter;
    private final List<Event> eventList = new ArrayList<>();

    public ArchiveFragmentController(ArchiveFragment fragment, View view) {
        super(fragment, view);
        adapter = new MovieListAdapter(context, new ArrayList<Movie>());
        fragment.setupCategory(adapter);
    }

    @OnItemClick(R.id.movie_list_view)
    void onMovie(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = new HomeActivity();
            homeActivity.goToFragment(EventFragment.newInstance(eventList.get(position)));
        }
    }
}