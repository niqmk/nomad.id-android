package com.allega.nomad.activity.intro;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.base.BaseController;

import butterknife.OnClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class IntroController extends BaseController<IntroActivity> {

    public IntroController(IntroActivity activity) {
        super(activity);
    }

    @OnClick(R.id.home_button)
    protected void onHome() {
        HomeActivity.startActivity(context);
    }

    @OnClick(R.id.login_button)
    protected void onLogin() {
        HomeActivity.startActivityToLogin(context);
    }
}
