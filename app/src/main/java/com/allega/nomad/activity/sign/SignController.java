package com.allega.nomad.activity.sign;

import com.allega.nomad.adapter.SignFragmentStateAdapter;
import com.allega.nomad.base.BaseController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SignController extends BaseController<SignActivity> {

    private final SignFragmentStateAdapter adapter;

    public SignController(SignActivity activity) {
        super(activity);
        adapter = new SignFragmentStateAdapter(activity, activity.getSupportFragmentManager());
        activity.setupViewPager(adapter);
    }
}
