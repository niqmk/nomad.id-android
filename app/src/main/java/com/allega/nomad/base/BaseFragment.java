package com.allega.nomad.base;

import android.support.v4.app.Fragment;

import com.allega.nomad.NomadApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by imnotpro on 5/30/15.
 */
public class BaseFragment extends Fragment {
    public NomadApplication getNomadApplication() {
        if (getActivity() != null && getActivity().getApplication() instanceof NomadApplication)
            return (NomadApplication) getActivity().getApplication();
        return null;
    }

    public void sendGAScreenTracker(String screenName) {
        NomadApplication nomadApplication = getNomadApplication();
        if (nomadApplication != null) {
            Tracker tracker = nomadApplication.getTracker();
            tracker.setScreenName(screenName);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }
}
