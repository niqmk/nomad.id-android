package com.allega.nomad.activity.eventcategory;

import com.allega.nomad.adapter.EventCategoryFragmentStateAdapter;
import com.allega.nomad.base.BaseController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EventCategoryController extends BaseController<EventCategoryActivity> {

    private final EventCategoryFragmentStateAdapter adapter;

    public EventCategoryController(EventCategoryActivity activity) {
        super(activity);
        adapter = new EventCategoryFragmentStateAdapter(activity, activity.getSupportFragmentManager());
        activity.setupViewPager(adapter);
    }
}
