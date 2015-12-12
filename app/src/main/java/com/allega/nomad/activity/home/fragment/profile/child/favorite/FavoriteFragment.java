package com.allega.nomad.activity.home.fragment.profile.child.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.ProfileWatchListAdapter;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class FavoriteFragment extends BaseFragment {

    @InjectView(R.id.movie_list_view)
    protected ListView movieListView;
    @InjectView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @InjectView(R.id.no_result_text_view)
    protected TextView noResultTextView;

    private FavoriteFragmentController controller;

    public static FavoriteFragment newInstance() {
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        return favoriteFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new FavoriteFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupLikeList(ProfileWatchListAdapter adapter) {
        movieListView.setAdapter(adapter);
    }

    void setVisibilityProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }

    public void updateList(ProfileWatchListAdapter adapter) {
        if (adapter.getCount() > 0) {
            adapter.notifyDataSetChanged();
        } else {
            noResultTextView.setVisibility(View.VISIBLE);
        }
    }
}
