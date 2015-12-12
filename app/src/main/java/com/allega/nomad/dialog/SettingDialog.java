package com.allega.nomad.dialog;

import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.storage.PreferenceProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 7/6/15.
 */
public class SettingDialog extends DialogFragment {

    private static final String TAG = SettingDialog.class.getSimpleName();
    private final AppRestService appRestService = AppRestController.getAppRestService();

    @InjectView(R.id.version_text_view)
    protected TextView versionTextView;
    private PreferenceProvider preferenceProvider;

    public static void show(FragmentManager fragmentManager) {
        SettingDialog changeEmailDialog = new SettingDialog();
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
        View view = inflater.inflate(R.layout.dialog_fragment_setting, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("v");
            stringBuilder.append(pInfo.versionName);
            versionTextView.setText(stringBuilder.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.AppDialog);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
