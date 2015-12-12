package com.allega.nomad.activity.moviecategory.fragment.latest;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.adapter.MovieListAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;
import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class LatestFragmentController extends BaseFragmentController<LatestFragment> {

    private final MovieListAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final List<Movie> movieList = new ArrayList<>();
    private final Realm realm;

    public LatestFragmentController(LatestFragment fragment, View view) {

        super(fragment, view);
        adapter = new MovieListAdapter(context, movieList);
        fragment.setupCategory(adapter);
        realm = DatabaseManager.getInstance(context);
        getLatestMovie();
    }

    private void getLatestMovie() {
        appRestService.getMovieLatest(new ResponseCallback<Response<Movie>>(context) {
            @Override
            public void success(Response<Movie> movieResponse) {
                if (movieResponse.getStatus() == Response.STATUS_OK) {
                    movieList.clear();
                    List<Movie> movies = movieResponse.getResults().getData();
                    movieList.addAll(movies);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(movies);
                    realm.commitTransaction();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnItemClick(R.id.movie_list_view)
    void onMovie(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(MovieFragment.newInstance(movieList.get(position)));
        }
    }
}