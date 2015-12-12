package com.allega.nomad.activity.sign.register;

import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.sign.SignActivity;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.UpdateMemberEvent;
import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.PreferenceProvider;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.OnClick;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class RegisterFragmentController extends BaseFragmentController<RegisterFragment> implements FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final PreferenceProvider preferenceProvider;
    private final CallbackManager callbackManager;
    private final ResponseCallback<Response<Member>> responseLogin = new ResponseCallback<Response<Member>>(context) {
        @Override
        public void success(Response<Member> memberResponse) {
            fragment.hideProgress();
            if (memberResponse.getStatus() == 1) {
                Member responseMember = memberResponse.getResult();
                if (responseMember != null) {
                    Crashlytics.setUserEmail(responseMember.getEmail());
                    Crashlytics.setUserName(responseMember.getFullName());
                    Crashlytics.setUserIdentifier(String.valueOf(responseMember.getId()));
                }
                preferenceProvider.setMember(responseMember);
                preferenceProvider.setToken(responseMember.getAuthToken());
                Bus.getInstance().post(new UpdateMemberEvent());
                activity.finish();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            super.failure(error);
            fragment.hideProgress();
        }
    };

    public RegisterFragmentController(RegisterFragment fragment, View view) {
        super(fragment, view);
        preferenceProvider = new PreferenceProvider(context);
        callbackManager = ((SignActivity) activity).getCallbackManager();
    }

    @OnClick(R.id.register_button)
    protected void register() {
        fragment.showProgress();
        String email = fragment.getEmail();
        String password = fragment.getPassword();
        String passwordConfirmation = fragment.getPasswordConfirmation();
        appRestService.postMember(email, password, passwordConfirmation, new ResponseCallback<Response<Member>>(context) {
            @Override
            public void success(Response<Member> memberResponse) {
                if (memberResponse.getStatus() == 1) {
                    Member responseMember = memberResponse.getResult();
                    if (responseMember != null) {
                        Crashlytics.setUserEmail(responseMember.getEmail());
                        Crashlytics.setUserName(responseMember.getFullName());
                        Crashlytics.setUserIdentifier(String.valueOf(responseMember.getId()));
                    }
                    preferenceProvider.setMember(memberResponse.getResult());
                    preferenceProvider.setToken(memberResponse.getResult().getAuthToken());
                    new MaterialDialog.Builder(context)
                            .title(R.string.successfully_register_title)
                            .content(R.string.successfully_register_content)
                            .positiveText(R.string.ok)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    HomeActivity.startActivity(context);
                                }
                            })
                            .cancelable(false)
                            .show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                fragment.hideProgress();
            }
        });
    }

    @OnClick(R.id.cancel_button)
    protected void onBack() {
        activity.finish();
    }

    @OnClick(R.id.facebook_button)
    protected void onFacebook() {
        fragment.showProgress();
        List<String> permissionNeeds = Arrays.asList("public_profile,email");
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();
        } else {
            LoginManager.getInstance().logInWithReadPermissions(activity, permissionNeeds);
            LoginManager.getInstance().registerCallback(callbackManager, this);
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {
        fragment.hideProgress();
    }

    @Override
    public void onError(FacebookException e) {
        e.printStackTrace();
        fragment.hideProgress();
    }

    @Override
    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
        try {
            String id = jsonObject.getString("id");
            String email = jsonObject.getString("email");
            appRestService.postFacebook(id, email, responseLogin);
        } catch (JSONException e) {
            e.printStackTrace();
            fragment.hideProgress();
        }
    }
}