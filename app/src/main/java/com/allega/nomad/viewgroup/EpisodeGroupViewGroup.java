package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.entity.SerialSeason;
import com.allega.nomad.entity.TvShowSeason;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EpisodeGroupViewGroup extends LinearLayout {

    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;

    public EpisodeGroupViewGroup(Context context) {
        super(context);
        init();
    }

    public EpisodeGroupViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EpisodeGroupViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_episode_group, this, true);
        ButterKnife.inject(this);
    }

    public void bind(TvShowSeason tvShowSeason) {
        titleTextView.setText(tvShowSeason.getTitle());
    }

    public void bind(SerialSeason serialSeason) {
        titleTextView.setText(serialSeason.getTitle());
    }
}

