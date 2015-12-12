package com.allega.nomad.activity.home.fragment.movie.child.review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.ReviewMovieAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.storage.PreferenceProvider;
import com.allega.nomad.viewgroup.LoadMoreListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ReviewFragment extends BaseFragment {

    final static int REQUEST_CODE_REVIEW = 1;
    private static final String ARGUMENT_ID = "argument-id";

    @InjectView(R.id.review_list_view)
    protected LoadMoreListView reviewListView;
    @InjectView(R.id.review_button)
    protected Button reviewButton;
    @InjectView(R.id.no_result_text_view)
    protected TextView noResultTextView;

    long id;
    private ReviewFragmentController controller;

    public static ReviewFragment newInstance(long id) {
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_ID, id);
        reviewFragment.setArguments(bundle);
        return reviewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(ARGUMENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreferenceProvider preferenceProvider = new PreferenceProvider(getActivity());
        if (preferenceProvider.getToken() != null) {
            reviewButton.setVisibility(View.VISIBLE);
        }

        controller = new ReviewFragmentController(this, view);
        reviewListView.setOnLoadMoreListener(controller);
        Bus.getInstance().register(controller);
    }

    @Override
    public void onDestroyView() {
        Bus.getInstance().unregister(controller);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REVIEW && resultCode == Activity.RESULT_OK) {
            controller.checkData();
            scrollToBottom();
        }
    }

    void setupReviewList(ReviewMovieAdapter adapter) {
        reviewListView.setAdapter(adapter);
    }

    public void scrollToBottom() {
        reviewListView.setSelection(reviewListView.getAdapter().getCount() - 1);
    }

    void setLoadMore(boolean isLoadMore) {
        reviewListView.setLoadMore(isLoadMore);
    }

    public void updateList(ReviewMovieAdapter adapter) {
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0) {
            noResultTextView.setVisibility(View.GONE);
            reviewListView.setVisibility(View.VISIBLE);
        } else {
            noResultTextView.setVisibility(View.VISIBLE);
            reviewListView.setVisibility(View.GONE);
        }
    }
}
