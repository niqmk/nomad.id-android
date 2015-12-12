package com.allega.nomad.activity.home.fragment.category.child;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.adapter.EventCategoryAdapter;
import com.allega.nomad.entity.Event;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.viewgroup.decorator.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EventChildCategoryFragmentController extends ChildCategoryFragmentController {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    private final EventCategoryAdapter eventCategoryAdapter;
    private final List<Event> eventList = new ArrayList<>();
    private final Realm realm;

    public EventChildCategoryFragmentController(ChildCategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        eventCategoryAdapter = new EventCategoryAdapter(context, eventList);
    }

    @Override
    protected void setupList(RecyclerView recyclerView) {
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(listLayoutManager);
        recyclerView.setAdapter(eventCategoryAdapter);
        int size = (int) context.getResources().getDimension(R.dimen.margin_x_small);
        recyclerView.addItemDecoration(new DividerDecoration(context, size));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.setMargins(size, size, size, size);
    }

    @Override
    public void onItemClick(RecyclerView parent, View child, int position, long id) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(EventFragment.newInstance(eventList.get(position)));
        }
    }

    @Override
    protected void getItem() {
        if (fragment.category > 0) {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getEventCategory(fragment.category, before, null, null, null, new EventCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getEventCategory(fragment.category, before, "popular", null, null, new EventCategoryCallback(context));
                    break;
            }
        } else {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getEventAll(before, new EventCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getEventPopular(before, new EventCategoryCallback(context));
                    break;
            }
        }
    }

    private class EventCategoryCallback extends ResponseCallback<Response<Event>> {

        public EventCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Event> edutainmentResponse) {
            if (edutainmentResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<Event> temp = realm.copyToRealmOrUpdate(edutainmentResponse.getResults().getData());
                realm.commitTransaction();
                before = edutainmentResponse.getResults().getLastToken();
                for (Event event : temp) {
                    if (!eventList.contains(event))
                        eventList.add(event);
                }
                fragment.updateList(eventCategoryAdapter);
                String pos = before.split("-")[0];
                if (Integer.valueOf(pos) > 1) {
                    fragment.prepareLoadMore();
                } else {
                    fragment.disableLoad();
                }
            } else {
                before = null;
                fragment.disableLoad();
            }
            if (eventList.size() == 0) {
                fragment.showNoResult();
            }
        }
    }
}