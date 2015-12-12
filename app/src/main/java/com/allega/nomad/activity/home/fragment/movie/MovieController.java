package com.allega.nomad.activity.home.fragment.movie;

import android.view.View;

import com.allega.nomad.adapter.MovieFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;

import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieController extends BaseFragmentController<MovieFragment> {

    private final MovieFragmentStateAdapter adapter;
    private final Realm realm = DatabaseManager.getInstance(context);
    private final Movie movie;
    private final AppRestService appRestService = AppRestController.getAppRestService();

    public MovieController(MovieFragment fragment, View view) {
        super(fragment, view);
        adapter = new MovieFragmentStateAdapter(context, fragment.getChildFragmentManager(), fragment.id);
        movie = realm.where(Movie.class).equalTo(Movie.FIELD_ID, fragment.id).findFirst();
        fragment.setupViewPager(adapter);
        getMovieDetail();
    }

    private void getMovieDetail() {
        appRestService.getMovie(fragment.id, new ResponseCallback<Response<Movie>>(context) {
            @Override
            public void success(Response<Movie> movieResponse) {
                if (movieResponse.getStatus() == 1) {
                    Movie movie = movieResponse.getResult();
                    realm.beginTransaction();
                    Movie realmMovie = realm.copyToRealmOrUpdate(movie);
                    realm.commitTransaction();
                }
            }
        });
    }

}
