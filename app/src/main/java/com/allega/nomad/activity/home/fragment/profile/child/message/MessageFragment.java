package com.allega.nomad.activity.home.fragment.profile.child.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.MessageAdapter;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MessageFragment extends BaseFragment {

    @InjectView(R.id.message_list_view)
    protected ListView messageListView;
    @InjectView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @InjectView(R.id.no_result_text_view)
    protected TextView noResultTextView;
    private MessageFragmentController controller;

    public static MessageFragment newInstance() {
        MessageFragment messageFragment = new MessageFragment();
        return messageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new MessageFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupMessage(MessageAdapter adapter) {
        messageListView.setAdapter(adapter);
    }

    void setVisibilityProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }

    public void updateList(MessageAdapter adapter) {
        if (adapter.getCount() > 0) {
            adapter.notifyDataSetChanged();
        } else {
            noResultTextView.setVisibility(View.VISIBLE);
        }
    }
}
