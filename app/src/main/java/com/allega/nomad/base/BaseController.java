package com.allega.nomad.base;

import android.app.Activity;
import android.content.Context;

import com.allega.nomad.NomadApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.ButterKnife;

/**
 * Created by imnotpro on 5/5/15.
 */
public abstract class BaseController<T extends Activity> {

    protected T activity;
    protected NomadApplication nomadApplication;
    protected Context context;

    public BaseController(T activity) {
        this.activity = activity;
        context = activity;
        nomadApplication = (NomadApplication) activity.getApplication();
        ButterKnife.inject(this, activity);
    }

    public void onDestroy() {

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