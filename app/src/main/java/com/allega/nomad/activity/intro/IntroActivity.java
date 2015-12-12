package com.allega.nomad.activity.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.service.analytic.GAConstant;

import butterknife.ButterKnife;

/**
 * Created by imnotpro on 5/30/15.
 */
public class IntroActivity extends BaseActivity {

    private IntroController controller;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, IntroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.inject(this);
        controller = new IntroController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.INTRO_SCREEN);
    }
}
