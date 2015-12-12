package com.allega.nomad.activity.home.fragment.event.fragment.recommended;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.adapter.RecommendedEventAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Event;
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

    private final RecommendedEventAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private List<Event> eventList = new ArrayList<>();

    public RecommendedFragmentController(RecommendedFragment fragment, View view) {
        super(fragment, view);
        adapter = new RecommendedEventAdapter(context, eventList);
        fragment.setupRecommendedList(adapter);
        getRecommended();
    }

    private void getRecommended() {
        appRestService.getEventRecommended(fragment.id, new ResponseCallback<Response<Event>>(context) {
            @Override
            public void success(Response<Event> eventResponse) {
                if (eventResponse.getResults() != null) {
                    eventList.addAll(eventResponse.getResults().getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnItemClick(R.id.recommended_list_view)
    public void onRelated(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(EventFragment.newInstance(eventList.get(position)));
        }
    }
}