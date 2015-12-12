package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by imnotpro on 6/2/15.
 */
public class HeaderSearchViewGroup extends LinearLayout {

    @InjectView(R.id.search_view)
    protected SearchView searchView;

    public HeaderSearchViewGroup(Context context) {
        super(context);
        init();
    }

    public HeaderSearchViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderSearchViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_header_search, this, true);
        ButterKnife.inject(this);
        searchView.setIconified(false);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQuery("", false);
                return true;
            }
        });
    }

    public void setOnQueryTextListener(SearchView.OnQueryTextListener onQueryTextListener) {
        searchView.setOnQueryTextListener(onQueryTextListener);
    }

    @OnClick(R.id.back_button)
    public void onBack() {
        if (getContext() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getContext();
            homeActivity.backFragment();
        }
    }
}
