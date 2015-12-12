package com.allega.nomad.activity.home.fragment.livechannel.fragment.comment;

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
import com.allega.nomad.adapter.CommentLiveChannelAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.storage.PreferenceProvider;
import com.allega.nomad.viewgroup.LoadMoreListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CommentFragment extends BaseFragment {

    final static int REQUEST_CODE_COMMENT = 1;
    private static final String ARG_ID = "arg-id";

    @InjectView(R.id.comment_list_view)
    protected LoadMoreListView commentListView;
    @InjectView(R.id.comment_button)
    protected Button commentButton;
    @InjectView(R.id.no_result_text_view)
    protected TextView noResultTextView;

    long id;
    private CommentFragmentController controller;

    public static CommentFragment newInstance(long id) {
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_ID, id);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(ARG_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreferenceProvider preferenceProvider = new PreferenceProvider(getActivity());
        if (preferenceProvider.getToken() != null) {
            commentButton.setVisibility(View.VISIBLE);
        }
        controller = new CommentFragmentController(this, view);
        commentListView.setOnLoadMoreListener(controller);
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
        if (requestCode == REQUEST_CODE_COMMENT && resultCode == Activity.RESULT_OK) {
            controller.checkData();
            scrollToBottom();
        }
    }


    public void setupCommentList(CommentLiveChannelAdapter adapter) {
        commentListView.setAdapter(adapter);
    }

    public void scrollToBottom() {
        commentListView.setSelection(commentListView.getAdapter().getCount() - 1);
    }

    void setLoadMore(boolean isLoadMore) {
        commentListView.setLoadMore(isLoadMore);
    }

    public void updateList(CommentLiveChannelAdapter adapter) {
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0) {
            noResultTextView.setVisibility(View.GONE);
            commentListView.setVisibility(View.VISIBLE);
        } else {
            noResultTextView.setVisibility(View.VISIBLE);
            commentListView.setVisibility(View.GONE);
        }
    }
}
