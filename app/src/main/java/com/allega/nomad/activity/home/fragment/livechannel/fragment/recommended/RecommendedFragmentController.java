package com.allega.nomad.activity.home.fragment.livechannel.fragment.recommended;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.livechannel.LiveChannelFragment;
import com.allega.nomad.adapter.RecommendedLiveChannelAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class RecommendedFragmentController extends BaseFragmentController<RecommendedFragment> {

    private final RecommendedLiveChannelAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private List<LiveChannel> liveChannelList = new ArrayList<>();

    public RecommendedFragmentController(RecommendedFragment fragment, View view) {
        super(fragment, view);
        adapter = new RecommendedLiveChannelAdapter(context, liveChannelList);
        fragment.setupRecommendedList(adapter);
        getRecommended();
    }

    private void getRecommended() {
        appRestService.getLiveChannelRecommended(fragment.id, new ResponseCallback<Response<LiveChannel>>(context) {
            @Override
            public void success(Response<LiveChannel> edutainmentResponse) {
                if (edutainmentResponse.getResults() != null) {
                    liveChannelList.addAll(edutainmentResponse.getResults().getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnItemClick(R.id.recommended_list_view)
    public void onRelated(int position) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(LiveChannelFragment.newInstance(liveChannelList.get(position)));
        }
    }
}