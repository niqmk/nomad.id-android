package com.allega.nomad.activity.eventlist.live;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.adapter.EventListAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Event;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class LiveFragmentController extends BaseFragmentController<LiveFragment> {

    private final EventListAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final List<Event> eventList = new ArrayList<>();


    public LiveFragmentController(LiveFragment fragment, View view) {
        super(fragment, view);
        adapter = new EventListAdapter(context, eventList);
        fragment.setupCategory(adapter);
        getEventCategory();
    }

    private void getEventCategory() {
//        appRestService.getEventCategory(fragment.id, new ResponseCallback<Response<Event>>(context) {
//            @Override
//            public void success(Response<Event> eventResponse) {
//                if (eventResponse.getResults() != null) {
//                    eventList.addAll(eventResponse.getResults().getData());
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });
    }

    @OnItemClick(R.id.movie_list_view)
    void onMovie(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = new HomeActivity();
            homeActivity.goToFragment(EventFragment.newInstance(eventList.get(position)));
        }
    }
}