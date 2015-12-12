package com.allega.nomad.activity.home.fragment.edutainment;

import android.view.View;

import com.allega.nomad.adapter.EdutainmentFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EdutainmentController extends BaseFragmentController<EdutainmentFragment> {

    private final EdutainmentFragmentStateAdapter adapter;

    public EdutainmentController(EdutainmentFragment fragment, View view) {
        super(fragment, view);
        adapter = new EdutainmentFragmentStateAdapter(context, fragment.getChildFragmentManager(), fragment.id);
        fragment.setupViewPager(adapter);
    }

}
