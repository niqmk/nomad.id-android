package com.allega.nomad.activity.sign.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.activity.sign.SignActivity;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.entity.Member;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class LoginFragment extends BaseFragment {

    @InjectView(R.id.facebook_button)
    protected ImageView facebookButton;
    @InjectView(R.id.email_edit_text)
    protected EditText emailEditText;
    @InjectView(R.id.password_edit_text)
    protected EditText passwordEditText;
    @InjectView(R.id.cancel_button)
    protected TextView cancelButton;
    @InjectView(R.id.login_button)
    protected TextView loginButton;
    private LoginFragmentController controller;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new LoginFragmentController(this, view);
    }

    Member getMember() {
        Member member = new Member();
        member.setUsername(emailEditText.getText().toString());
        member.setPassword(passwordEditText.getText().toString());
        return member;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void showProgress() {
        if (getActivity() instanceof SignActivity) {
            SignActivity signActivity = (SignActivity) getActivity();
            signActivity.showProgress();
        }
    }

    void hideProgress() {
        if (getActivity() instanceof SignActivity) {
            SignActivity signActivity = (SignActivity) getActivity();
            signActivity.hideProgress();
        }
    }
}
