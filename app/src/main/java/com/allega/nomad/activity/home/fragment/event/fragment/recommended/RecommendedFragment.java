package com.allega.nomad.activity.home.fragment.event.fragment.recommended;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.RecommendedEventAdapter;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class RecommendedFragment extends BaseFragment {

    private static final String ARG_ID = "arg-id";

    @InjectView(R.id.recommended_list_view)
    protected ListView recommendedListView;

    long id;
    private RecommendedFragmentController controller;

    public static RecommendedFragment newInstance(long id) {
        RecommendedFragment recommendedFragment = new RecommendedFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_ID, id);
        recommendedFragment.setArguments(bundle);
        return recommendedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(ARG_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_recommended, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new RecommendedFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupRecommendedList(RecommendedEventAdapter adapter) {
        recommendedListView.setAdapter(adapter);
    }
}
