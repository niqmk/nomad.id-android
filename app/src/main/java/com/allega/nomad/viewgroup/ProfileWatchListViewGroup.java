package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.entity.WatchListVideo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ProfileWatchListViewGroup extends CardView {

    @InjectView(R.id.name_text_view)
    protected TextView nameTextView;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;

    public ProfileWatchListViewGroup(Context context) {
        super(context);
        init();
    }

    public ProfileWatchListViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileWatchListViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_profile_watchlist, this, true);
        ButterKnife.inject(this);
    }

    public void bind(WatchListVideo watchListVideo) {
        nameTextView.setText(watchListVideo.getDetail().getTitle());
        switch (watchListVideo.getVideoTypeId()) {
            case 1:
                descriptionTextView.setText(R.string.in_movies);
                break;
            case 2:
                descriptionTextView.setText(R.string.in_edutainments);
                break;
            case 3:
                descriptionTextView.setText(R.string.in_event);
                break;
            case 4:
                descriptionTextView.setText(R.string.in_tv_show);
                break;
            case 5:
                descriptionTextView.setText(R.string.in_live_channel);
                break;
        }
    }
}
