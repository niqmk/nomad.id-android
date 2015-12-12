package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.allega.nomad.R;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.Movie;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverMovieViewGroup extends RelativeLayout {

    @InjectView(R.id.cover_image_view)
    protected ImageView coverImageView;

    public DiscoverMovieViewGroup(Context context) {
        super(context);
        init();
    }

    public DiscoverMovieViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiscoverMovieViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_discovery_movie, this, true);
        ButterKnife.inject(this);
    }

    public void bind(Movie item) {
        Glide.with(getContext()).load(ImageController.getPortait(item.getImages())).into(coverImageView);
    }
}

