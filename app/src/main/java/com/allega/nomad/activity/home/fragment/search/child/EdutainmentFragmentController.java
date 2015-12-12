package com.allega.nomad.activity.home.fragment.search.child;

import android.view.View;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EdutainmentFragmentController extends SearchFragmentController<Edutainment> {

    protected final AppRestService appRestService = AppRestController.getAppRestService();

    public EdutainmentFragmentController(SearchFragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    protected void getItem(final String query) {
        appRestService.searchEdutainment(query, before, new ResponseCallback<Response<Edutainment>>(context) {
            @Override
            public void success(Response<Edutainment> edutainmentResponse) {
                fragment.showProgressBar(false);
                if (edutainmentResponse.getStatus() == 1) {
                    List<Edutainment> edutainmentList = edutainmentResponse.getResults().getData();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(edutainmentList);
                    realm.commitTransaction();
                    addItem(edutainmentList);

                    before = edutainmentResponse.getResults().getLastToken();
                    fragment.updateResult(query, edutainmentResponse.getResults().getData().size());
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
    protected void goToItem(Edutainment item) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(EdutainmentFragment.newInstance(item));
        }
    }

}