package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.TvShowEpisode;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SearchViewGroup extends RelativeLayout {

    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.genre_text_view)
    protected TextView genreTextView;

    public SearchViewGroup(Context context) {
        super(context);
        init();
    }

    public SearchViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_search, this, true);
        ButterKnife.inject(this);
    }

    public void bind(Movie movie) {
        titleTextView.setText(movie.getTitle());
    }

    public void bind(Event event) {
        titleTextView.setText(event.getTitle());
    }

    public void bind(Edutainment edutainment) {
        titleTextView.setText(edutainment.getTitle());
    }

    public void bind(TvShowEpisode tvShowEpisode) {
        titleTextView.setText(tvShowEpisode.getTitle());
    }

    public void bind(LiveChannel liveChannel) {
        titleTextView.setText(liveChannel.getTitle());
    }

    public void bind(SerialEpisode serialEpisode) {
        titleTextView.setText(serialEpisode.getTitle());
    }

    public void bind(Ad ad) {
        titleTextView.setText(ad.getTitle());
    }

    public void bind(String query) {
        titleTextView.setVisibility(GONE);
        genreTextView.setText(query);
    }
}

