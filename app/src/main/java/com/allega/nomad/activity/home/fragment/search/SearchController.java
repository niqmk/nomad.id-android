package com.allega.nomad.activity.home.fragment.search;

import android.support.v7.widget.SearchView;
import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.adapter.SearchAdapter;
import com.allega.nomad.adapter.SearchFragmentStateAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.SearchEvent;
import com.allega.nomad.storage.PreferenceProvider;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SearchController extends BaseFragmentController<SearchFragment> implements SearchView.OnQueryTextListener {

    private final SearchFragmentStateAdapter searchFragmentStateAdapter;
    private final PreferenceProvider preferenceProvider;
    private final List<String> recentlySearchList = new ArrayList<>();
    private final SearchAdapter recentlySearchAdapter;

    public SearchController(SearchFragment fragment, View view) {
        super(fragment, view);
        preferenceProvider = new PreferenceProvider(context);
        searchFragmentStateAdapter = new SearchFragmentStateAdapter(context, fragment.getChildFragmentManager());
        recentlySearchList.addAll(preferenceProvider.getRecentlySearch());
        recentlySearchAdapter = new SearchAdapter<String>(context, recentlySearchList);
        fragment.setupViewPager(searchFragmentStateAdapter, recentlySearchAdapter);
        if (recentlySearchList.size() == 0)
            fragment.hideRecentlySearch();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        fragment.hideRecentlySearch();
        if (!recentlySearchList.contains(query)) {
            recentlySearchList.add(0, query);
            preferenceProvider.setRecentlySearch(recentlySearchList);
        }
        Bus.getInstance().post(new SearchEvent(query));
        return true;
    }

    @OnItemClick(R.id.recent_list_view)
    protected void onRecently(int position) {
        fragment.hideRecentlySearch();
        String query = recentlySearchList.get(position);
        fragment.updateQuery(query);
    }

    @OnClick(R.id.clear_button)
    protected void onClear() {
        fragment.hideRecentlySearch();
        recentlySearchList.clear();
        recentlySearchAdapter.notifyDataSetChanged();
        preferenceProvider.setRecentlySearch(recentlySearchList);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (StringUtils.isEmpty(newText)) {
            fragment.showRecentlySearch();
        }
        return false;
    }
}
