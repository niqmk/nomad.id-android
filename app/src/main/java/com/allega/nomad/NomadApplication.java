package com.allega.nomad;

import android.app.Application;

import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.scheduler.DeleteFileTask;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.gcm.GcmNetworkManager;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by imnotpro on 5/30/15.
 */
public class NomadApplication extends Application {
    private Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(this);

        try {
            DatabaseManager.getInstance(this);
        } catch (RealmMigrationNeededException ex) {
            Realm.deleteRealm(DatabaseManager.getConfiguration(this));
        } catch (IllegalArgumentException e) {
            Realm.deleteRealm(DatabaseManager.getConfiguration(this));
            DatabaseManager.getInstance(this);
        }

        AppRestController.getInstance().generateRest(this);

        GcmNetworkManager gcmNetworkManager = GcmNetworkManager.getInstance(this);
        gcmNetworkManager.schedule(DeleteFileTask.generateTask());

        PreferenceProvider preferenceProvider = new PreferenceProvider(this);

        Member member = preferenceProvider.getMember();
        if (member != null) {
            Crashlytics.setUserEmail(member.getEmail());
            Crashlytics.setUserName(member.getFullName());
            Crashlytics.setUserIdentifier(String.valueOf(member.getId()));
        }
    }

    synchronized public Tracker getTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker(R.xml.app_tracker);
        }
        return tracker;
    }
}
