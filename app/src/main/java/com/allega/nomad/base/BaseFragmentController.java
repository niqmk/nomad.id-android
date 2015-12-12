package com.allega.nomad.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.allega.nomad.NomadApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.ButterKnife;

/**
 * Created by imnotpro on 5/5/15.
 */
public abstract class BaseFragmentController<T extends Fragment> {

    final protected BaseActivity activity;
    final protected T fragment;
    final protected NomadApplication nomadApplication;
    final protected Context context;

    public BaseFragmentController(T fragment, View view) {
        this.activity = (BaseActivity) fragment.getActivity();
        this.fragment = fragment;
        context = activity;
        nomadApplication = (NomadApplication) activity.getApplication();
        ButterKnife.inject(this, view);
    }

    public void sendGAEvent(String category, String action) {
        if (nomadApplication != null) {
            Tracker tracker = nomadApplication.getTracker();
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .build());
        }
    }
}