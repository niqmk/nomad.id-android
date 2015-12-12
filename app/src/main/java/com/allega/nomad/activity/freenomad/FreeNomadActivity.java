package com.allega.nomad.activity.freenomad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ListView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.FreeNomadPointAdapter;
import com.allega.nomad.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class FreeNomadActivity extends BaseActivity {

    @InjectView(R.id.free_nomad_list_view)
    protected ListView freeNomadListView;
    private FreeNomadController controller;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FreeNomadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_nomad);
        ButterKnife.inject(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        controller = new FreeNomadController(this);
    }

    public void setupList(FreeNomadPointAdapter adapter) {
        freeNomadListView.setAdapter(adapter);
    }
}
