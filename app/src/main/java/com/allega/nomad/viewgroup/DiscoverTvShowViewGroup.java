package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.TvShow;
import com.allega.nomad.entity.TvShowEpisode;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverTvShowViewGroup extends RelativeLayout {

    @InjectView(R.id.cover_image_view)
    protected ImageView coverImageView;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.genre_text_view)
    protected TextView genreTextView;

    public DiscoverTvShowViewGroup(Context context) {
        super(context);
        init();
    }

    public DiscoverTvShowViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiscoverTvShowViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_discovery_tv_show, this, true);
        ButterKnife.inject(this);
    }

    public void bind(TvShow item) {
        titleTextView.setText(item.getTitle());
        if (item.getImages() != null)
            Glide.with(getContext()).load(ImageController.getLandscape(item.getImages())).placeholder(R.drawable.image_placeholder).into(coverImageView);
        else
            coverImageView.setImageResource(R.drawable.ic_no_image);
    }

    public void bind(TvShowEpisode item) {
        titleTextView.setText(item.getTitle());
        if (item.getImages() != null)
            Glide.with(getContext()).load(ImageController.getLandscape(item.getImages())).placeholder(R.drawable.image_placeholder).into(coverImageView);
        else
            coverImageView.setImageResource(R.drawable.ic_no_image);
    }
}

