package com.allega.nomad.activity.home.fragment.ad;

import android.view.View;

import com.allega.nomad.adapter.AdFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class AdController extends BaseFragmentController<AdFragment> {

    private final AdFragmentStateAdapter adapter;

    public AdController(AdFragment fragment, View view) {
        super(fragment, view);
        adapter = new AdFragmentStateAdapter(context, fragment.getChildFragmentManager(), fragment.id);
        fragment.setupViewPager(adapter);
    }

}
