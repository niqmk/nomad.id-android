package com.allega.nomad.activity.eventlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.allega.nomad.R;
import com.allega.nomad.adapter.EventListFragmentStateAdapter;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.entity.EventPreview;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EventListActivity extends BaseActivity {

    private static final String EXTRA_ID = "extra-id";

    @InjectView(R.id.category_tab_strip)
    protected PagerSlidingTabStrip categoryTabStrip;
    @InjectView(R.id.category_view_pager)
    protected ViewPager categoryViewPager;
    long id;
    private EventListController controller;
    private SearchView searchView;

    public static void startActivity(Context context, long id) {
        Intent intent = new Intent(context, EventListActivity.class);
        intent.putExtra(EXTRA_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getLongExtra(EXTRA_ID, id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edutainment_category);
        ButterKnife.inject(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        controller = new EventListController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_and_search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    void setupViewPager(EventListFragmentStateAdapter adapter) {
        categoryViewPager.setAdapter(adapter);
        categoryTabStrip.setViewPager(categoryViewPager);
    }

    public void setupHeader(EventPreview event) {
        setTitle(event.getName());
    }
}
