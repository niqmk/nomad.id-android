package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.entity.Message;
import com.allega.nomad.util.TimeUtil;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MessageViewGroup extends CardView {

    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;
    @InjectView(R.id.time_text_view)
    protected TextView timeTextView;

    public MessageViewGroup(Context context) {
        super(context);
        init();
    }

    public MessageViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessageViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_message, this, true);
        ButterKnife.inject(this);
    }

    public void bind(Message message) {
        titleTextView.setText(message.getTitle());
        descriptionTextView.setText(Html.fromHtml(message.getDescription()));
        timeTextView.setText(DateUtils.getRelativeTimeSpanString(TimeUtil.addCurrentTimeZone(message.getCreatedAt()), new Date().getTime(), DateUtils.SECOND_IN_MILLIS));
    }
}
