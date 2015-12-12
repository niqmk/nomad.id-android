package com.allega.nomad.activity.home.fragment.search.child;

import android.view.View;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.ad.AdFragment;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class AdFragmentController extends SearchFragmentController<Ad> {

    protected final AppRestService appRestService = AppRestController.getAppRestService();

    public AdFragmentController(SearchFragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    protected void getItem(final String query) {
        appRestService.searchAd(query, before, new ResponseCallback<Response<Ad>>(context) {
            @Override
            public void success(Response<Ad> adResponse) {
                fragment.showProgressBar(false);
                if (adResponse.getStatus() == 1) {
                    List<Ad> adList = adResponse.getResults().getData();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(adList);
                    realm.commitTransaction();
                    addItem(adList);

                    before = adResponse.getResults().getLastToken();
                    fragment.updateResult(query, adResponse.getResults().getData().size());
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
    protected void goToItem(Ad item) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(AdFragment.newInstance(item));
        }
    }

}