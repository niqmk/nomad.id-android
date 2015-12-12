package com.allega.nomad.activity.home.fragment.search.child;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.adapter.SearchAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.storage.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;
import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public abstract class SearchFragmentController<T> extends BaseFragmentController<SearchFragment> {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    protected final Realm realm;
    private final List<T> itemList = new ArrayList<>();
    private final SearchAdapter<T> adapter;
    protected String before;
    private String query;

    public SearchFragmentController(SearchFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        adapter = new SearchAdapter<>(context, itemList);
        fragment.setupList(adapter);
    }

    @OnItemClick(R.id.search_list_view)
    protected void onItemClick(int position) {
        goToItem(itemList.get(position));
    }

    protected void addItem(List<T> itemList) {
        this.itemList.addAll(itemList);
        adapter.notifyDataSetChanged();
        fragment.updateResult(query, itemList.size());
    }

    public void newSearch(String query) {
        fragment.showProgressBar(true);
        this.query = query;
        before = null;
        itemList.clear();
        getItem(query);
    }

    protected abstract void getItem(String query);

    protected abstract void goToItem(T item);
}