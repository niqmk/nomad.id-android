package com.allega.nomad.activity.home.fragment.profile.child.profile;

import android.view.View;
import android.widget.Toast;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.UpdateMemberEvent;
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
import com.facebook.login.LoginManager;

import butterknife.OnClick;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ProfileFragmentController extends BaseFragmentController<ProfileFragment> {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final PreferenceProvider preferenceProvider;

    public ProfileFragmentController(ProfileFragment fragment, View view) {
        super(fragment, view);
        preferenceProvider = new PreferenceProvider(context);
        fragment.bind(preferenceProvider.getMember());
        getProfile();
    }

    private void getProfile() {
        appRestService.getMember(preferenceProvider.getMember().getId(), new ResponseCallback<Response<Member>>(context) {
            @Override
            public void success(Response<Member> memberResponse) {
                if (memberResponse.getStatus() == 1) {
                    fragment.bind(memberResponse.getResult());
                }
            }
        });
    }

    @OnClick(R.id.change_password_button)
    protected void onChangePassword() {
        if (preferenceProvider.getMember().isPasswordNull()) {
            ChangePasswordDialog.showNoPass(fragment.getChildFragmentManager());
        } else {
            ChangePasswordDialog.show(fragment.getChildFragmentManager());
        }
    }

    @OnClick(R.id.change_email_button)
    protected void onChangeEmail() {
        if (preferenceProvider.getMember().isPasswordNull()) {
            Toast.makeText(activity, R.string.no_password_toash, Toast.LENGTH_SHORT).show();
        } else {
            ChangeEmailDialog.show(fragment.getChildFragmentManager());
        }
    }

    @OnClick(R.id.change_username_button)
    protected void onChangeUsername() {
        if (preferenceProvider.getMember().isPasswordNull()) {
            Toast.makeText(activity, R.string.no_password_toash, Toast.LENGTH_SHORT).show();
        } else {
            ChangeUsernameDialog.show(fragment.getChildFragmentManager());
        }
    }

    public void onEventMainThread(UpdateMemberEvent updateMemberEvent) {
        fragment.bind(preferenceProvider.getMember());
    }

    @OnClick(R.id.logout_button)
    protected void onLogout() {
        activity.showProgress();
        appRestService.postLogout(new ResponseCallback<Response<Member>>(context) {
            @Override
            public void success(Response<Member> memberResponse) {
                preferenceProvider.clearPreference();
                DatabaseManager.clearPersonalDatabase(context);
                LoginManager.getInstance().logOut();
                activity.hideProgress();
                HomeActivity.startActivity(context);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                activity.hideProgress();
            }
        });
    }
}