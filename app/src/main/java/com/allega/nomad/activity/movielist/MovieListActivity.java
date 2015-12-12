package com.allega.nomad.activity.movielist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.allega.nomad.R;
import com.allega.nomad.adapter.MovieListAdapter;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.entity.MoviePreview;
import com.allega.nomad.viewgroup.LoadMoreListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieListActivity extends BaseActivity {

    private static final String EXTRA_ID = "extra-id";

    @InjectView(R.id.movie_list_view)
    protected LoadMoreListView movieListView;
    long id;
    private MovieListController controller;
    private SearchView searchView;

    public static void startActivity(Context context, long id) {
        Intent intent = new Intent(context, MovieListActivity.class);
        intent.putExtra(EXTRA_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getLongExtra(EXTRA_ID, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.inject(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        controller = new MovieListController(this);
        movieListView.setOnLoadMoreListener(controller);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_and_search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    void setupViewPager(MovieListAdapter adapter) {
        movieListView.setAdapter(adapter);
    }

    public void setupScreen(MoviePreview moviePreview) {
        setTitle(moviePreview.getName());
    }

    void setLoadMore(boolean isLoadMore) {
        movieListView.setLoadMore(isLoadMore);
    }
}
