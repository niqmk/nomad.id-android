package com.allega.nomad.activity.moviecategory.fragment.genre;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.movielist.MovieListActivity;
import com.allega.nomad.adapter.CategoryAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.MoviePreview;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;

import butterknife.OnItemClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/30/15.
 */
public class GenreFragmentController extends BaseFragmentController<GenreFragment> {

    private final CategoryAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final Realm realm;
    private final RealmResults<MoviePreview> realmResults;

    public GenreFragmentController(GenreFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        realmResults = realm.where(MoviePreview.class).findAll();
        adapter = new CategoryAdapter(context, realmResults);
        fragment.setupCategory(adapter);
        getPreviewGenre();
    }

    private void getPreviewGenre() {
        appRestService.getMoviePreview(new ResponseCallback<Response<MoviePreview>>(context) {
            @Override
            public void success(Response<MoviePreview> moviePreviewResponse) {
                if (moviePreviewResponse.getResults() != null) {
                    realm.beginTransaction();
                    realm.clear(MoviePreview.class);
                    realm.copyToRealmOrUpdate(moviePreviewResponse.getResults().getData());
                    realm.commitTransaction();
                }
            }
        });
    }

    @OnItemClick(R.id.category_grid_view)
    void onCategory(int position) {
        MoviePreview moviePreview = realmResults.get(position);
        MovieListActivity.startActivity(context, moviePreview.getId());
    }
}