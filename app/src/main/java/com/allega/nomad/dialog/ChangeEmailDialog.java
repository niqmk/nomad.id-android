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
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.UpdateMemberEvent;
import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.request.MemberRequest;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.PreferenceProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by imnotpro on 7/6/15.
 */
public class ChangeEmailDialog extends DialogFragment {

    private static final String TAG = ChangeEmailDialog.class.getSimpleName();
    private final AppRestService appRestService = AppRestController.getAppRestService();
    @InjectView(R.id.email_edit_text)
    protected EditText emailEditText;
    @InjectView(R.id.password_edit_text)
    protected EditText passwordEditText;
    private PreferenceProvider preferenceProvider;

    public static void show(FragmentManager fragmentManager) {
        ChangeEmailDialog changeEmailDialog = new ChangeEmailDialog();
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
        View view = inflater.inflate(R.layout.dialog_fragment_change_email, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.AppDialog);
        return dialog;
    }

    @OnClick(R.id.save_button)
    protected void onSave() {
        String email = emailEditText.getText().toString();
        MemberRequest memberRequest = new MemberRequest(preferenceProvider.getMember());
        memberRequest.setEmail(email);
        appRestService.putMember(preferenceProvider.getMember().getId(), memberRequest, new ResponseCallback<Response<Member>>(getActivity()) {
            @Override
            public void success(Response<Member> memberResponse) {
                if (memberResponse.getStatus() == 1) {
                    preferenceProvider.setMember(memberResponse.getResult());
                    preferenceProvider.setToken(memberResponse.getResult().getAuthToken());
                    Bus.getInstance().post(new UpdateMemberEvent());
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
