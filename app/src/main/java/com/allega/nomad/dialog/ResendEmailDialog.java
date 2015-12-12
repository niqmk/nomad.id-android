package com.allega.nomad.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.PreferenceProvider;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by imnotpro on 7/6/15.
 */
public class ResendEmailDialog extends DialogFragment {

    private static final String TAG = ResendEmailDialog.class.getSimpleName();
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private PreferenceProvider preferenceProvider;

    public static void show(Context context) {
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            ResendEmailDialog changeEmailDialog = new ResendEmailDialog();
            changeEmailDialog.show(activity.getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceProvider = new PreferenceProvider(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_resend_email, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.AppDialog);
        return dialog;
    }

    @OnClick(R.id.resend_button)
    protected void onResent() {
        appRestService.postResendEmail(preferenceProvider.getMember().getId(), new ResponseCallback<Response>(getActivity()) {
            @Override
            public void success(Response memberResponse) {
                if (memberResponse.getStatus() == 1) {
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), R.string.email_confirmation_send, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.ok_button)
    protected void onOk() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
