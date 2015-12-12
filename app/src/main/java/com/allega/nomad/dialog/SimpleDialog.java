package com.allega.nomad.dialog;

import android.content.Context;
import android.widget.Toast;

import com.allega.nomad.R;
import com.allega.nomad.activity.sign.SignActivity;

/**
 * Created by imnotpro on 8/15/15.
 */
public class SimpleDialog {

    public static void showNeedLogin(final Context context) {
        Toast.makeText(context, context.getString(R.string.need_login), Toast.LENGTH_SHORT).show();
        SignActivity.startActivity(context);
    }
}
