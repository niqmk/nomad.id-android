package com.allega.nomad.activity.sign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.allega.nomad.R;
import com.allega.nomad.adapter.SignFragmentStateAdapter;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.service.analytic.GAConstant;
import com.astuetz.PagerSlidingTabStrip;
import com.facebook.CallbackManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SignActivity extends BaseActivity {

    private final Object lock = new Object();
    @InjectView(R.id.sign_tab_strip)
    protected PagerSlidingTabStrip signTabStrip;
    @InjectView(R.id.sign_view_pager)
    protected ViewPager signViewPager;
    private SignController controller;
    private CallbackManager callbackManager;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SignActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.inject(this);
        controller = new SignController(this);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.SIGN_SCREEN);
    }

    void setupViewPager(SignFragmentStateAdapter adapter) {
        signViewPager.setAdapter(adapter);
        signTabStrip.setViewPager(signViewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
