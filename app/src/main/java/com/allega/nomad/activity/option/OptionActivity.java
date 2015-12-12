package com.allega.nomad.activity.option;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseActivity;

/**
 * Created by imnotpro on 5/30/15.
 */
public class OptionActivity extends BaseActivity {

    private OptionController controller;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, OptionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        controller = new OptionController(this);
    }

}
