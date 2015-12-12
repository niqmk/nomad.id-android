package com.allega.nomad.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.UpdateCollectionEvent;
import com.allega.nomad.entity.Collection;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.FileManager;
import com.allega.nomad.util.VideoUtil;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by imnotpro on 7/6/15.
 */
public class DeleteConfirmationDialog extends DialogFragment {

    private static final String TAG = DeleteConfirmationDialog.class.getSimpleName();
    private static final String ARG_ID = "id";
    @InjectView(R.id.delete_description_text_view)
    protected TextView deleteDescriptionTextView;

    private Collection collection;

    public static void show(FragmentManager fragmentManager, long id) {
        DeleteConfirmationDialog changeEmailDialog = new DeleteConfirmationDialog();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_ID, id);
        changeEmailDialog.setArguments(bundle);
        changeEmailDialog.show(fragmentManager, TAG);
    }

    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getArguments().getLong(ARG_ID);
        Realm realm = DatabaseManager.getInstance(getActivity());
        collection = realm.where(Collection.class).equalTo(Collection.FIELD_ID, id).findFirst();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_delete_confirmation, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        File file = FileManager.getFile(getActivity(), VideoUtil.getVideo(collection.getDetail().getVideos()));
        if (file != null) {
            deleteDescriptionTextView.setText(getResources().getString(R.string.delete_confimation, readableFileSize(file.length())));
        } else {
            dismiss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.AppDialog);
        return dialog;
    }

    @OnClick(R.id.delete_button)
    protected void onDelete() {
        File file = FileManager.getFile(getActivity(), VideoUtil.getVideo(collection.getDetail().getVideos()));
        if (file != null && file.exists()) {
            file.delete();
            Bus.getInstance().post(new UpdateCollectionEvent());
        }
        dismiss();
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
