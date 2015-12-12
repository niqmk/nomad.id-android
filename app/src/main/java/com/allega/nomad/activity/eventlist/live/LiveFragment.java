package com.allega.nomad.activity.eventlist.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.EventListAdapter;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class LiveFragment extends BaseFragment {

    private static final String ARG_ID = "arg-id";

    @InjectView(R.id.movie_list_view)
    ListView movieListView;
    long id;
    private LiveFragmentController controller;

    public static LiveFragment newInstance(long id) {
        LiveFragment liveFragment = new LiveFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_ID, id);
        liveFragment.setArguments(bundle);
        return liveFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(ARG_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new LiveFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupCategory(EventListAdapter adapter) {
        movieListView.setAdapter(adapter);
    }
}
