package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.search.SearchFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by imnotpro on 6/2/15.
 */
public class HeaderActionBarViewGroup extends LinearLayout {

    public HeaderActionBarViewGroup(Context context) {
        super(context);
        init();
    }

    public HeaderActionBarViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderActionBarViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_actionbar_title, this, true);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.back_button)
    public void onBack() {
        if (getContext() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getContext();
            homeActivity.backFragment();
        }
    }

    @OnClick(R.id.search_button)
    protected void onSearch() {
        if (getContext() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getContext();
            homeActivity.goToFragment(SearchFragment.newInstance());
        }
    }
}
