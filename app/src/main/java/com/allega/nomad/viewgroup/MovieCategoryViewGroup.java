package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.Price;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieCategoryViewGroup extends LinearLayout {

    @InjectView(R.id.cover_image_view)
    protected ImageView coverImageView;
    @InjectView(R.id.genre_text_view)
    protected TextView genreTextView;

    public MovieCategoryViewGroup(Context context) {
        super(context);
        init();
    }

    public MovieCategoryViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovieCategoryViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_movie_category, this, true);
        ButterKnife.inject(this);
    }

    public void bind(Movie item) {
        Glide.with(getContext()).load(ImageController.getPortait(item.getImages())).into(coverImageView);
        if (item.getPrices() != null && item.getPrices().size() > 0) {
            Price price = item.getPrices().get(0);
            if (price.getPrice().equals("0"))
                genreTextView.setText(getResources().getString(R.string.free_caps));
            else
                genreTextView.setText(getResources().getString(R.string.x_points, String.valueOf(price.getPrice())));
        }
    }
}

