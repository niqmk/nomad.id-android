package com.allega.nomad.activity.home.fragment.search.child;

import android.view.View;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieFragmentController extends SearchFragmentController<Movie> {

    protected final AppRestService appRestService = AppRestController.getAppRestService();

    public MovieFragmentController(SearchFragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    protected void getItem(final String query) {
        appRestService.searchMovie(query, before, new ResponseCallback<Response<Movie>>(context) {
            @Override
            public void success(Response<Movie> movieResponse) {
                fragment.showProgressBar(false);
                if (movieResponse.getStatus() == 1) {
                    List<Movie> movieList = movieResponse.getResults().getData();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(movieList);
                    realm.commitTransaction();
                    addItem(movieList);

                    before = movieResponse.getResults().getLastToken();
                    fragment.updateResult(query, movieResponse.getResults().getData().size());
//                    activity.setLoadMore(true);
                } else {
                    before = null;
//                    activity.setLoadMore(false);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                fragment.showProgressBar(false);
                super.failure(error);
            }
        });
    }

    @Override
    protected void goToItem(Movie item) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(MovieFragment.newInstance(item));
        }
    }

}