package com.allega.nomad.activity.home.fragment.livechannel;

import android.view.View;

import com.allega.nomad.adapter.LiveChannelFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class LiveChannelController extends BaseFragmentController<LiveChannelFragment> {

    private final LiveChannelFragmentStateAdapter adapter;

    public LiveChannelController(LiveChannelFragment fragment, View view) {
        super(fragment, view);
        adapter = new LiveChannelFragmentStateAdapter(context, fragment.getChildFragmentManager(), fragment.id);
        fragment.setupViewPager(adapter);
    }

}
