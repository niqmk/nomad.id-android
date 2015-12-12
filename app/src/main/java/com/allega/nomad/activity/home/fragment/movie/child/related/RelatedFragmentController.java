package com.allega.nomad.activity.home.fragment.movie.child.related;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.adapter.SuggestionMovieAdapter;
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
public class RelatedFragmentController extends BaseFragmentController<RelatedFragment> {

    private final SuggestionMovieAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final List<Movie> movieList = new ArrayList<>();
    private Realm realm = DatabaseManager.getInstance(context);

    public RelatedFragmentController(RelatedFragment fragment, View view) {
        super(fragment, view);
        adapter = new SuggestionMovieAdapter(context, movieList);
        fragment.setupSuggestionGridView(adapter);
        getRelated();
    }

    private void getRelated() {
        appRestService.getMovieRelateds(fragment.id, new ResponseCallback<Response<Movie>>(context) {
            @Override
            public void success(Response<Movie> movieResponse) {
                if (movieResponse.getResults() != null) {
                    movieList.addAll(movieResponse.getResults().getData());
                    adapter.notifyDataSetChanged();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(movieList);
                    realm.commitTransaction();
                }
            }

        });
    }

    @OnItemClick(R.id.item_list_view)
    public void onRelated(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(MovieFragment.newInstance(movieList.get(position)));
        }
    }
}