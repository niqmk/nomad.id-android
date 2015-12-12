package com.allega.nomad.activity.edutainmentlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.allega.nomad.R;
import com.allega.nomad.adapter.EdutainmentListAdapter;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.entity.EdutainmentPreview;
import com.allega.nomad.viewgroup.LoadMoreListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EdutainmentListActivity extends BaseActivity {

    private static final String EXTRA_ID = "extra-id";

    @InjectView(R.id.edutainment_list_view)
    protected LoadMoreListView edutainmentListView;
    long id;
    private EdutainmentListController controller;
    private SearchView searchView;

    public static void startActivity(Context context, long id) {
        Intent intent = new Intent(context, EdutainmentListActivity.class);
        intent.putExtra(EXTRA_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getLongExtra(EXTRA_ID, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edutainment_list);
        ButterKnife.inject(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        controller = new EdutainmentListController(this);
        edutainmentListView.setOnLoadMoreListener(controller);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_and_search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    void setupList(EdutainmentListAdapter adapter) {
        edutainmentListView.setAdapter(adapter);
    }

    public void setupHeader(EdutainmentPreview edutainmentPreview) {
        setTitle(edutainmentPreview.getName());
    }

    void setLoadMore(boolean isLoadMore) {
        edutainmentListView.setLoadMore(isLoadMore);
    }
}
