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

import com.afollestad.materialdialogs.MaterialDialog;
import com.allega.nomad.R;
import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.request.MemberRequest;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.PreferenceProvider;
import com.ctrlplusz.anytextview.AnyTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by imnotpro on 7/6/15.
 */
public class ChangePasswordDialog extends DialogFragment {

    private static final String ARG_NO_PASS = "no-pass";

    private static final String TAG = ChangePasswordDialog.class.getSimpleName();
    private final AppRestService appRestService = AppRestController.getAppRestService();
    @InjectView(R.id.existing_password_edit_text)
    protected EditText existingPasswordEditText;
    @InjectView(R.id.password_edit_text)
    protected EditText passwordEditText;
    @InjectView(R.id.password_confirmation_edit_text)
    protected EditText passwordConfirmationEditText;
    @InjectView(R.id.title_text_view)
    AnyTextView titleTextView;
    private PreferenceProvider preferenceProvider;
    private boolean isNoPass = false;

    public static void show(FragmentManager fragmentManager) {
        ChangePasswordDialog changeEmailDialog = new ChangePasswordDialog();
        changeEmailDialog.show(fragmentManager, TAG);
    }

    public static void showNoPass(FragmentManager fragmentManager) {
        ChangePasswordDialog changeEmailDialog = new ChangePasswordDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_NO_PASS, true);
        changeEmailDialog.setArguments(bundle);
        changeEmailDialog.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceProvider = new PreferenceProvider(getActivity());
        if (getArguments() != null) {
            isNoPass = getArguments().getBoolean(ARG_NO_PASS, false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_change_password, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isNoPass) {
            existingPasswordEditText.setVisibility(View.GONE);
            titleTextView.setText(R.string.set_password);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.AppDialog);
        return dialog;
    }

    @OnClick(R.id.save_button)
    protected void onSave() {
        String existingPassword = existingPasswordEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirmation = passwordConfirmationEditText.getText().toString();
        Member member = preferenceProvider.getMember();
        MemberRequest memberRequest = new MemberRequest(member);
        if (!isNoPass)
            memberRequest.setCurrentPassword(existingPassword);
        memberRequest.setPassword(password);
        memberRequest.setPasswordConfirmation(passwordConfirmation);
        appRestService.putMember(preferenceProvider.getMember().getId(), memberRequest, new ResponseCallback<Response<Member>>(getActivity()) {
            @Override
            public void success(Response<Member> memberResponse) {
                if (memberResponse.getStatus() == 1) {
                    preferenceProvider.setMember(memberResponse.getResult());
                    preferenceProvider.setToken(memberResponse.getResult().getAuthToken());
                    dismiss();
                } else if (memberResponse.getMessage() != null) {
                    new MaterialDialog.Builder(getActivity())
                            .title(R.string.error)
                            .content(memberResponse.getMessage())
                            .positiveText(R.string.ok)
                            .show();
                } else if (memberResponse.getErrors().getFirstError() != null) {
                    new MaterialDialog.Builder(getActivity())
                            .title(R.string.error)
                            .content(memberResponse.getErrors().getFirstError())
                            .positiveText(R.string.ok)
                            .show();
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
