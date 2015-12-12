package com.allega.nomad.activity.home.fragment.search.child;

import android.view.View;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.tvshow.TvShowFragment;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class TvShowEpisodeFragmentController extends SearchFragmentController<TvShowEpisode> {

    protected final AppRestService appRestService = AppRestController.getAppRestService();

    public TvShowEpisodeFragmentController(SearchFragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    protected void getItem(final String query) {
        appRestService.searchTvShow(query, before, new ResponseCallback<Response<TvShowEpisode>>(context) {
            @Override
            public void success(Response<TvShowEpisode> episodeResponse) {
                fragment.showProgressBar(false);
                if (episodeResponse.getStatus() == 1) {
                    List<TvShowEpisode> tvShowEpisodeList = episodeResponse.getResults().getData();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(tvShowEpisodeList);
                    realm.commitTransaction();
                    addItem(tvShowEpisodeList);

                    before = episodeResponse.getResults().getLastToken();
                    fragment.updateResult(query, episodeResponse.getResults().getData().size());
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
    protected void goToItem(TvShowEpisode item) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(TvShowFragment.newInstance(item));
        }
    }

}