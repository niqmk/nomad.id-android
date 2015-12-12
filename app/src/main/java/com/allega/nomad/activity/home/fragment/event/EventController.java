package com.allega.nomad.activity.home.fragment.event;

import android.view.View;

import com.allega.nomad.adapter.EventFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EventController extends BaseFragmentController<EventFragment> {

    private final EventFragmentStateAdapter adapter;

    public EventController(EventFragment fragment, View view) {
        super(fragment, view);
        adapter = new EventFragmentStateAdapter(context, fragment.getChildFragmentManager(), fragment.id);
        fragment.setupViewPager(adapter);
    }

}
