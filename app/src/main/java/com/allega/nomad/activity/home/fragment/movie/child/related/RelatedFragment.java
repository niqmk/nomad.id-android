package com.allega.nomad.activity.home.fragment.movie.child.related;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.SuggestionMovieAdapter;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class RelatedFragment extends BaseFragment {

    private static final String ARGUMENT_ID = "argument-id";

    @InjectView(R.id.item_list_view)
    protected GridView itemListView;
    long id;
    private RelatedFragmentController controller;

    public static RelatedFragment newInstance(long id) {
        RelatedFragment relatedFragment = new RelatedFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_ID, id);
        relatedFragment.setArguments(bundle);
        return relatedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(ARGUMENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new RelatedFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupSuggestionGridView(SuggestionMovieAdapter adapter) {
        itemListView.setAdapter(adapter);
    }
}
