package com.allega.nomad.activity.home.fragment.profile.child.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.CollectionAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CollectionFragment extends BaseFragment {

    @InjectView(R.id.collection_list_view)
    protected ListView collectionListView;
    @InjectView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @InjectView(R.id.no_result_text_view)
    protected TextView noResultTextView;
    private CollectionFragmentController controller;

    public static CollectionFragment newInstance() {
        CollectionFragment collectionFragment = new CollectionFragment();
        return collectionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new CollectionFragmentController(this, view);
        Bus.getInstance().register(controller);
    }

    @Override
    public void onDestroyView() {
        Bus.getInstance().unregister(controller);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    void setupCollection(CollectionAdapter adapter) {
        collectionListView.setAdapter(adapter);
    }

    void setVisibilityProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }

    public void updateList(CollectionAdapter adapter) {
        if (adapter.getCount() > 0) {
            adapter.notifyDataSetChanged();
        } else {
            noResultTextView.setVisibility(View.VISIBLE);
        }
    }
}
