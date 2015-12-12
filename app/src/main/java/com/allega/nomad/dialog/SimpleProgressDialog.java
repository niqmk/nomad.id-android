package com.allega.nomad.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.allega.nomad.R;

/**
 * Created by imnotpro on 8/27/15.
 */
public class SimpleProgressDialog {

    public static ProgressDialog create(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getString(R.string.please_wait));
        return progressDialog;
    }
}
