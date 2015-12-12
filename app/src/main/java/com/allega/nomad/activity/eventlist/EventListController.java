package com.allega.nomad.activity.eventlist;

import com.allega.nomad.adapter.EventListFragmentStateAdapter;
import com.allega.nomad.base.BaseController;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.EventPreview;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.storage.DatabaseManager;

import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EventListController extends BaseController<EventListActivity> {

    private final EventListFragmentStateAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private Realm realm;
    private EventPreview event;

    public EventListController(EventListActivity activity) {
        super(activity);
        realm = DatabaseManager.getInstance(context);
        event = realm.where(EventPreview.class).equalTo(Event.FIELD_ID, activity.id).findFirst();
        adapter = new EventListFragmentStateAdapter(activity, activity.getSupportFragmentManager(), activity.id);
        activity.setupViewPager(adapter);
        activity.setupHeader(event);
    }

}
