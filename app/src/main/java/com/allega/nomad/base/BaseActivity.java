package com.allega.nomad.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.allega.nomad.NomadApplication;
import com.allega.nomad.dialog.SimpleProgressDialog;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by imnotpro on 5/5/15.
 */
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public NomadApplication getNomadApplication() {
        if (getApplication() instanceof NomadApplication)
            return (NomadApplication) getApplication();
        return null;
    }

    public synchronized void showProgress() {
        if (progressDialog != null)
            progressDialog.hide();
        progressDialog = SimpleProgressDialog.create(this);
        progressDialog.show();
    }

    public synchronized void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.hide();
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
