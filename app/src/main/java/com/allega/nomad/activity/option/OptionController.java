package com.allega.nomad.activity.option;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.base.BaseController;
import com.allega.nomad.dialog.ChangeEmailDialog;
import com.allega.nomad.dialog.ChangePasswordDialog;
import com.allega.nomad.dialog.ChangeUsernameDialog;
import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;

import butterknife.OnClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class OptionController extends BaseController<OptionActivity> {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final PreferenceProvider preferenceProvider;

    public OptionController(OptionActivity activity) {
        super(activity);
        preferenceProvider = new PreferenceProvider(context);
    }

    @OnClick(R.id.logout_button)
    protected void onLogout() {
        appRestService.postLogout(new ResponseCallback<Response<Member>>(context) {
            @Override
            public void success(Response<Member> memberResponse) {
                preferenceProvider.clearPreference();
                DatabaseManager.clearPersonalDatabase(context);
                HomeActivity.startActivity(context);
            }
        });
    }

    @OnClick(R.id.change_username_button)
    protected void onChangeUsername() {
        ChangeUsernameDialog.show(activity.getSupportFragmentManager());
    }

    @OnClick(R.id.change_email_button)
    protected void onChangeEmail() {
        ChangeEmailDialog.show(activity.getSupportFragmentManager());
    }

    @OnClick(R.id.change_password_button)
    protected void onChangePassword() {
        ChangePasswordDialog.show(activity.getSupportFragmentManager());
    }
}
