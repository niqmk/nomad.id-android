package com.allega.nomad.activity.home.fragment.search.child;

import android.view.View;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EventFragmentController extends SearchFragmentController<Event> {

    protected final AppRestService appRestService = AppRestController.getAppRestService();

    public EventFragmentController(SearchFragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    protected void getItem(final String query) {
        appRestService.searchEvent(query, before, new ResponseCallback<Response<Event>>(context) {
            @Override
            public void success(Response<Event> eventResponse) {
                fragment.showProgressBar(false);
                if (eventResponse.getStatus() == 1) {
                    List<Event> edutainmentList = eventResponse.getResults().getData();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(edutainmentList);
                    realm.commitTransaction();
                    addItem(edutainmentList);

                    before = eventResponse.getResults().getLastToken();
                    fragment.updateResult(query, eventResponse.getResults().getData().size());
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
    protected void goToItem(Event item) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = new HomeActivity();
            homeActivity.goToFragment(EventFragment.newInstance(item));
        }
    }

}