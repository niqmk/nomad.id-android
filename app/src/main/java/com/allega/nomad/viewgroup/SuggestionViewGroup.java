package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.Price;
import com.allega.nomad.entity.Serial;
import com.allega.nomad.entity.TvShow;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SuggestionViewGroup extends RelativeLayout {

    @InjectView(R.id.background_image_view)
    protected ImageView backgroundImageView;
    @InjectView(R.id.genre_text_view)
    protected TextView genreTextView;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;

    public SuggestionViewGroup(Context context) {
        super(context);
        init();
    }

    public SuggestionViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuggestionViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_suggestion, this, true);
        ButterKnife.inject(this);
    }

    public void bind() {
        String url = "http://i1.wp.com/bitcast-a-sm.bitgravity.com/slashfilm/wp/wp-content/images/International-Avengers-Age-of-Ultron-Poster-700x989.jpg";
        Glide.with(getContext()).load(url).into(backgroundImageView);
    }

    public void bind(Ad ad) {
        Glide.with(getContext()).load(ImageController.getLandscape(ad.getImages())).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        titleTextView.setText(ad.getTitle());
        genreTextView.setText(getResources().getString(R.string.earn_point_list, String.valueOf(ad.getPointReward())));
        genreTextView.setTextColor(getResources().getColor(R.color.currency_yellow));
    }

    public void bind(Event event) {
        Glide.with(getContext()).load(ImageController.getLandscape(event.getImages())).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        titleTextView.setText(event.getTitle());
        genreTextView.setTextColor(getResources().getColor(R.color.currency_yellow));
        if (event.getPrices() != null && event.getPrices().size() > 0) {
            Price price = event.getPrices().get(0);
            if (price.getPrice().equals("0"))
                genreTextView.setText(getResources().getString(R.string.free_caps));
            else
                genreTextView.setText(getResources().getString(R.string.x_points, String.valueOf(price.getPrice())));
        }
    }

    public void bind(Edutainment edutainment) {
        Glide.with(getContext()).load(ImageController.getLandscape(edutainment.getImages())).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        titleTextView.setText(edutainment.getTitle());
        genreTextView.setTextColor(getResources().getColor(R.color.currency_yellow));
        if (edutainment.getPrices() != null && edutainment.getPrices().size() > 0) {
            Price price = edutainment.getPrices().get(0);
            if (price.getPrice().equals("0"))
                genreTextView.setText(getResources().getString(R.string.free_caps));
            else
                genreTextView.setText(getResources().getString(R.string.x_points, String.valueOf(price.getPrice())));
        }
    }

    public void bind(TvShow tvShow) {
        if (tvShow.getImages() != null)
            Glide.with(getContext()).load(ImageController.getLandscape(tvShow.getImages())).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        titleTextView.setText(tvShow.getTitle());
    }

    public void bind(Serial serial) {
        if (serial.getImages() != null)
            Glide.with(getContext()).load(ImageController.getLandscape(serial.getImages())).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        titleTextView.setText(serial.getTitle());
    }

    public void bind(LiveChannel liveChannel) {
        if (liveChannel.getImages() != null)
            Glide.with(getContext()).load(ImageController.getLandscape(liveChannel.getImages())).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        titleTextView.setText(liveChannel.getTitle());
        genreTextView.setTextColor(getResources().getColor(R.color.currency_yellow));
        if (liveChannel.getPrices() != null && liveChannel.getPrices().size() > 0) {
            Price price = liveChannel.getPrices().get(0);
            if (price.getPrice().equals("0"))
                genreTextView.setText(getResources().getString(R.string.free_caps));
            else
                genreTextView.setText(getResources().getString(R.string.x_points, String.valueOf(price.getPrice())));
        }
    }
}

