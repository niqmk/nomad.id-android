package com.allega.nomad.activity.movielist;

import com.allega.nomad.R;
import com.allega.nomad.adapter.MovieListAdapter;
import com.allega.nomad.base.BaseController;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.MoviePreview;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.viewgroup.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;
import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieListController extends BaseController<MovieListActivity> implements LoadMoreListView.OnLoadMoreListener {

    private final MovieListAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final List<Movie> movieList = new ArrayList<>();
    private final Realm realm;
    private MoviePreview moviePreview;
    private String before = null;

    public MovieListController(MovieListActivity activity) {
        super(activity);
        adapter = new MovieListAdapter(context, movieList);
        activity.setupViewPager(adapter);
        realm = DatabaseManager.getInstance(context);
        moviePreview = realm.where(MoviePreview.class).equalTo(MoviePreview.FIELD_ID, activity.id).findFirst();
        getMovieGenre();
        activity.setupScreen(moviePreview);
    }

    private void getMovieGenre() {
//        appRestService.getMovieGenre(moviePreview.getId(), before, new ResponseCallback<Response<Movie>>(context) {
//            @Override
//            public void success(Response<Movie> movieResponse) {
//                if (movieResponse.getStatus() == 1) {
//                    movieList.clear();
//                    movieList.addAll(movieResponse.getResults().getData());
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(movieResponse.getResults().getData());
//                    realm.commitTransaction();
//                    adapter.notifyDataSetChanged();
//                    before = movieResponse.getResults().getLastToken();
//                    activity.setLoadMore(true);
//                } else {
//                    before = null;
//                    activity.setLoadMore(false);
//                }
//            }
//        });
    }

    @OnItemClick(R.id.movie_list_view)
    void onMovie(int position) {
    }

    @Override
    public void onLoadMore() {
        getMovieGenre();
    }
}
