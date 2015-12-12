package com.allega.nomad.activity.home.fragment.serial;

import android.view.View;

import com.allega.nomad.adapter.SerialFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SerialController extends BaseFragmentController<SerialFragment> {

    private final SerialFragmentStateAdapter adapter;

    public SerialController(SerialFragment fragment, View view) {
        super(fragment, view);
        adapter = new SerialFragmentStateAdapter(context, fragment.getChildFragmentManager(), fragment.id);
        fragment.setupViewPager(adapter);
    }

}
