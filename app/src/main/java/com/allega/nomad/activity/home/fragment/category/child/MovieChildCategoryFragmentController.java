package com.allega.nomad.activity.home.fragment.category.child;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.adapter.MovieCategoryAdapter;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.viewgroup.decorator.GridDividerDecoration;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieChildCategoryFragmentController extends ChildCategoryFragmentController {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    private final MovieCategoryAdapter movieCategoryAdapter;
    private final List<Movie> movieList = new ArrayList<>();
    private final Realm realm;

    public MovieChildCategoryFragmentController(ChildCategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        movieCategoryAdapter = new MovieCategoryAdapter(context, movieList);
    }

    @Override
    protected void setupList(RecyclerView recyclerView) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieCategoryAdapter);
        int size = (int) context.getResources().getDimension(R.dimen.margin_x_small);
        recyclerView.addItemDecoration(new GridDividerDecoration(context, size));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.setMargins(size, size, size, size);
    }

    @Override
    public void onItemClick(RecyclerView parent, View child, int position, long id) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(MovieFragment.newInstance(movieCategoryAdapter.getItem(position)));
        }
    }

    @Override
    protected void getItem() {
        if (fragment.category > 0) {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getMovieGenre(fragment.category, before, null, null, null, new MovieCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getMovieGenre(fragment.category, before, "popular", null, null, new MovieCategoryCallback(context));
                    break;
                case 2:
                    appRestService.getMovieGenre(fragment.category, before, null, "paid", null, new MovieCategoryCallback(context));
                    break;
                case 3:
                    appRestService.getMovieGenre(fragment.category, before, null, "free", null, new MovieCategoryCallback(context));
                    break;
                case 4:
                    appRestService.getMovieGenre(fragment.category, before, null, null, true, new MovieCategoryCallback(context));
                    break;
            }
        } else {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getMovieAll(before, new MovieCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getMoviePopular(before, new MovieCategoryCallback(context));
                    break;
                case 2:
                    appRestService.getMoviePaid(before, new MovieCategoryCallback(context));
                    break;
                case 3:
                    appRestService.getMovieFree(before, new MovieCategoryCallback(context));
                    break;
                case 4:
                    appRestService.getMovieUpcoming(before, new MovieCategoryCallback(context));
                    break;
            }
        }
    }

    private class MovieCategoryCallback extends ResponseCallback<Response<Movie>> {

        public MovieCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Movie> movieResponse) {
            if (movieResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<Movie> temp = realm.copyToRealmOrUpdate(movieResponse.getResults().getData());
                realm.commitTransaction();
                before = movieResponse.getResults().getLastToken();
                for (Movie movie : temp) {
                    if (!movieList.contains(movie))
                        movieList.add(movie);
                }
                fragment.updateList(movieCategoryAdapter);
                String pos = before.split("-")[0];
                if (Integer.valueOf(pos) > 1) {
                    fragment.prepareLoadMore();
                } else {
                    fragment.disableLoad();
                }
            } else {
                before = null;
                fragment.disableLoad();
            }
            if (movieList.size() == 0) {
                fragment.showNoResult();
            }
        }
    }
}