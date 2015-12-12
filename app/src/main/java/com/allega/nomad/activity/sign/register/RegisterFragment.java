package com.allega.nomad.activity.sign.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.allega.nomad.R;
import com.allega.nomad.activity.sign.SignActivity;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class RegisterFragment extends BaseFragment {

    @InjectView(R.id.email_edit_text)
    protected EditText emailEditText;
    @InjectView(R.id.password_edit_text)
    protected EditText passwordEditText;
    @InjectView(R.id.password_confirmation_edit_text)
    protected EditText passwordConfirmationEditText;
    private RegisterFragmentController controller;

    public static RegisterFragment newInstance() {
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new RegisterFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    String getEmail() {
        return emailEditText.getText().toString();
    }

    String getPassword() {
        return passwordEditText.getText().toString();
    }

    String getPasswordConfirmation() {
        return passwordConfirmationEditText.getText().toString();
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
