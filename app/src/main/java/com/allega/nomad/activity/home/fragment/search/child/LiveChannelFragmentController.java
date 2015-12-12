package com.allega.nomad.activity.home.fragment.search.child;

import android.view.View;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.livechannel.LiveChannelFragment;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class LiveChannelFragmentController extends SearchFragmentController<LiveChannel> {

    protected final AppRestService appRestService = AppRestController.getAppRestService();

    public LiveChannelFragmentController(SearchFragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    protected void getItem(final String query) {
        appRestService.searchLiveChannel(query, before, new ResponseCallback<Response<LiveChannel>>(context) {
            @Override
            public void success(Response<LiveChannel> liveChannelResponse) {
                fragment.showProgressBar(false);
                if (liveChannelResponse.getStatus() == 1) {
                    List<LiveChannel> liveChannelList = liveChannelResponse.getResults().getData();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(liveChannelList);
                    realm.commitTransaction();
                    addItem(liveChannelList);

                    before = liveChannelResponse.getResults().getLastToken();
                    fragment.updateResult(query, liveChannelResponse.getResults().getData().size());
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
    protected void goToItem(LiveChannel item) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(LiveChannelFragment.newInstance(item));
        }
    }

}