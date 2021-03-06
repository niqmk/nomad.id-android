package com.allega.nomad.activity.edutainmentcategory;

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
import com.allega.nomad.adapter.EdutainmentCategoryFragmentStateAdapter;
import com.allega.nomad.base.BaseActivity;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EdutainmentCategoryActivity extends BaseActivity {

    @InjectView(R.id.category_tab_strip)
    protected PagerSlidingTabStrip categoryTabStrip;
    @InjectView(R.id.category_view_pager)
    protected ViewPager categoryViewPager;
    private EdutainmentCategoryController controller;
    private SearchView searchView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, EdutainmentCategoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edutainment_category);
        ButterKnife.inject(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        controller = new EdutainmentCategoryController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    void setupViewPager(EdutainmentCategoryFragmentStateAdapter adapter) {
        categoryViewPager.setAdapter(adapter);
        categoryTabStrip.setViewPager(categoryViewPager);
    }
}
