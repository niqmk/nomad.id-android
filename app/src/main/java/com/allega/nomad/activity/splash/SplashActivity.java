package com.allega.nomad.activity.splash;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.service.analytic.GAConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SplashActivity extends BaseActivity {

    @InjectView(R.id.version_text_view)
    protected TextView versionTextView;

    protected SplashController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("v");
            stringBuilder.append(pInfo.versionName);
            versionTextView.setText(stringBuilder.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        controller = new SplashController(this);
        controller.getCategory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.SPLASH_SCREEN);
    }
}
