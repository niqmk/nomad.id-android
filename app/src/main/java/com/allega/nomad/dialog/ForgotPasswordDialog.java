package com.allega.nomad.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.allega.nomad.R;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.PreferenceProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by imnotpro on 7/6/15.
 */
public class ForgotPasswordDialog extends DialogFragment {

    private static final String TAG = ForgotPasswordDialog.class.getSimpleName();
    private final AppRestService appRestService = AppRestController.getAppRestService();
    @InjectView(R.id.email_edit_text)
    protected EditText emailEditText;
    private PreferenceProvider preferenceProvider;

    public static void show(FragmentManager fragmentManager) {
        ForgotPasswordDialog changeEmailDialog = new ForgotPasswordDialog();
        changeEmailDialog.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceProvider = new PreferenceProvider(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_forgot_password, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.AppDialog);
        return dialog;
    }

    @OnClick(R.id.send_button)
    protected void onSend() {
        String email = emailEditText.getText().toString();
        appRestService.postForgotPassword(email, new ResponseCallback<Response>(getActivity()) {
            @Override
            public void success(Response response) {
                if (response.getStatus() == 1) {
                    Toast.makeText(getActivity(), R.string.forgot_password, Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }

    @OnClick(R.id.cancel_button)
    protected void onCancel() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
