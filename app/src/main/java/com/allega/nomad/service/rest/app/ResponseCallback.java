package com.allega.nomad.service.rest.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.allega.nomad.R;
import com.allega.nomad.activity.sign.SignActivity;
import com.allega.nomad.dialog.ResendEmailDialog;
import com.allega.nomad.service.rest.app.response.Errors;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.service.rest.app.response.ResponsePaging;

import org.apache.commons.lang3.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/7/15.
 */
public abstract class ResponseCallback<T> implements Callback<T> {

    private Context context;

    public ResponseCallback(Context context) {
        this.context = context;
    }

    @Override
    public final void success(T t, retrofit.client.Response response) {
        long status = 1;
        Errors errors = null;
        String message = null;
        String debugMessage = null;
        if (t instanceof Response) {
            Response serverResponse = (Response) t;
            status = serverResponse.getStatus();
            errors = serverResponse.getErrors();
            message = serverResponse.getSystemMessage();
            debugMessage = serverResponse.getDebugMessage();
        } else if (t instanceof ResponsePaging) {
            ResponsePaging serverResponse = (ResponsePaging) t;
            status = serverResponse.getStatus();
            message = serverResponse.getSystemMessage();
            debugMessage = serverResponse.getDebugMessage();
        }
        if (!StringUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        if (!StringUtils.isEmpty(debugMessage))
            new MaterialDialog.Builder(context)
                    .title(R.string.debug_message)
                    .content(debugMessage)
                    .positiveText(R.string.ok)
                    .show();

        if (status == 1 || status == 0) {
            try {
                success(t);
            } catch (NullPointerException e) {
                //Error from fragment already destroyed
            }
        } else if (status == 2) {
            Toast.makeText(context, R.string.error_token_title, Toast.LENGTH_SHORT).show();
            SignActivity.startActivity(context);
            failure(null);
        } else if (status == 4 && errors != null) {
            new MaterialDialog.Builder(context)
                    .title(R.string.error_register_title)
                    .content(errors.getFirstError())
                    .positiveText(R.string.ok)
                    .show();
            failure(null);
        } else if (status == 5) {
            ResendEmailDialog.show(context);
            failure(null);
        }
    }

    public abstract void success(T t);

    @Override
    public void failure(RetrofitError error) {
        if (error != null && error.getMessage() != null)
            Log.d("Rest Service", error.getMessage());
    }
}
