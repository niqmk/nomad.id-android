package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.TvShowEpisode;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EpisodeChildViewGroup extends LinearLayout {

    @InjectView(R.id.cover_image_view)
    protected ImageView coverImageView;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;

    public EpisodeChildViewGroup(Context context) {
        super(context);
        init();
    }

    public EpisodeChildViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EpisodeChildViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_episode_child, this, true);
        ButterKnife.inject(this);
    }

    public void bind(TvShowEpisode tvShowEpisode) {
        if (tvShowEpisode.getImages() != null)
            Glide.with(getContext()).load(ImageController.getLandscape(tvShowEpisode.getImages())).into(coverImageView);
        else {
            coverImageView.setImageResource(R.drawable.ic_no_image);
        }
        if (tvShowEpisode.getEpisode() > 0) {
            titleTextView.setText(getResources().getString(R.string.episode_no, tvShowEpisode.getEpisode()));
            titleTextView.setVisibility(VISIBLE);
        } else {
            titleTextView.setVisibility(GONE);
        }
        if (tvShowEpisode.getTitle() != null) {
            descriptionTextView.setText(tvShowEpisode.getTitle());
            descriptionTextView.setVisibility(VISIBLE);
        } else {
            descriptionTextView.setVisibility(GONE);
        }
    }

    public void bind(SerialEpisode serialEpisode) {
        if (serialEpisode.getImages() != null)
            Glide.with(getContext()).load(ImageController.getLandscape(serialEpisode.getImages())).into(coverImageView);
        else {
            coverImageView.setImageResource(R.drawable.ic_no_image);
        }
        if (serialEpisode.getEpisode() > 0) {
            titleTextView.setText(getResources().getString(R.string.episode_no, serialEpisode.getEpisode()));
            titleTextView.setVisibility(VISIBLE);
        } else {
            titleTextView.setVisibility(GONE);
        }
        if (serialEpisode.getTitle() != null) {
            descriptionTextView.setText(serialEpisode.getTitle());
            descriptionTextView.setVisibility(VISIBLE);
        } else {
            descriptionTextView.setVisibility(GONE);
        }
    }
}

