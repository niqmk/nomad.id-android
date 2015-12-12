package com.allega.nomad.activity.home.fragment.messagedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.entity.Message;
import com.allega.nomad.service.analytic.GAConstant;
import com.allega.nomad.util.TimeUtil;
import com.ctrlplusz.anytextview.AnyTextView;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MessageDetailFragment extends BaseFragment {

    private static final String EXTRA_ID = "extra-id";
    @InjectView(R.id.title_text_view)
    protected AnyTextView titleTextView;
    @InjectView(R.id.date_text_view)
    protected AnyTextView dateTextView;
    @InjectView(R.id.description_text_view)
    protected AnyTextView descriptionTextView;
    long id;
    private MessageDetailController controller;

    public static MessageDetailFragment newInstance(Message message) {
        MessageDetailFragment messageDetailFragment = new MessageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, message.getId());
        messageDetailFragment.setArguments(bundle);
        return messageDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(EXTRA_ID, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_detail, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new MessageDetailController(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.MESSAGE_DETAIL_SCREEN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void bind(Message message) {
        titleTextView.setText(message.getTitle());
        descriptionTextView.setText(Html.fromHtml(message.getDescription()));
        descriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());
        dateTextView.setText(DateUtils.getRelativeTimeSpanString(TimeUtil.addCurrentTimeZone(message.getCreatedAt()), new Date().getTime(), DateUtils.SECOND_IN_MILLIS));
    }
}
