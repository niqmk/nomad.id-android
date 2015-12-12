package com.allega.nomad.activity.eventcategory.fragment.upcoming;

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
public class UpcomingFragmentController extends BaseFragmentController<UpcomingFragment> {

    private final EventListAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final List<Event> eventList = new ArrayList<>();

    public UpcomingFragmentController(UpcomingFragment fragment, View view) {
        super(fragment, view);
        adapter = new EventListAdapter(context, eventList);
        fragment.setupCategory(adapter);
        getUpcoming();
    }

    private void getUpcoming() {
//        appRestService.getEventUpcoming(new ResponseCallback<Response<Event>>(context) {
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
    void onEvent(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = new HomeActivity();
            homeActivity.goToFragment(EventFragment.newInstance(eventList.get(position)));
        }
    }
}