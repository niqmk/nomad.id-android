package com.allega.nomad.viewgroup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.activity.home.fragment.livechannel.LiveChannelFragment;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.activity.home.fragment.tvshow.TvShowFragment;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.TvShow;
import com.allega.nomad.model.Slider;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverSliderViewGroup extends RelativeLayout implements View.OnClickListener {

    @InjectView(R.id.cover_image_view)
    protected ImageView coverImageView;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.genre_text_view)
    protected TextView genreTextView;
    private Slider slider;

    public DiscoverSliderViewGroup(Context context) {
        super(context);
        init();
    }

    public DiscoverSliderViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiscoverSliderViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_discovery_slider, this, true);
        ButterKnife.inject(this);
        setOnClickListener(this);
        setOnTouchListener(new OnTouchListener() {
            float x1;
            float x2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {

                    case MotionEvent.ACTION_DOWN: {
                        x1 = event.getX();
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        x2 = event.getX();

                        if (Math.abs(x1 - x2) < 10) {
                            onClick(v);
                        } else {
                            ViewFlipper viewFlipper = (ViewFlipper) v.getParent();
                            if (x1 < x2) {
                                viewFlipper.setInAnimation(getContext(), R.anim.left_in);
                                viewFlipper.setOutAnimation(getContext(), R.anim.right_out);
                                viewFlipper.showPrevious();
                                viewFlipper.setInAnimation(getContext(), R.anim.right_in);
                                viewFlipper.setOutAnimation(getContext(), R.anim.left_out);
                            }
                            if (x1 > x2) {
                                viewFlipper.setInAnimation(getContext(), R.anim.right_in);
                                viewFlipper.setOutAnimation(getContext(), R.anim.left_out);
                                viewFlipper.showNext();
                            }
                        }
                        break;
                    }
                }
                return true;
            }
        });
    }

    public void bind(Slider item) {
        slider = item;
        titleTextView.setText(item.getTitle());
        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
            genreTextView.setText(item.getDescription());
            genreTextView.setVisibility(VISIBLE);
        } else {
            genreTextView.setVisibility(GONE);
        }
        Glide.with(getContext()).load(ImageController.getThumb(item.getImages())).into(coverImageView);
    }

    @Override
    public void onClick(View v) {
        if (slider.getLinkUrl() != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getLinkUrl()));
            getContext().startActivity(browserIntent);
        } else if (getContext() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getContext();
            Long id = slider.getVideoTypeId();
            if (id == null) {

            } else if (id == 1) {
                Movie movie = new Movie();
                movie.setId(slider.getTargetId());
                homeActivity.goToFragment(MovieFragment.newInstance(movie));
            } else if (id == 2) {
                Edutainment edutainment = new Edutainment();
                edutainment.setId(slider.getTargetId());
                homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainment));
            } else if (id == 3) {
                Event event = new Event();
                event.setId(slider.getTargetId());
                homeActivity.goToFragment(EventFragment.newInstance(event));
            } else if (id == 4) {
                TvShow tvShow = new TvShow();
                tvShow.setId(slider.getTargetId());
                homeActivity.goToFragment(TvShowFragment.newInstance(tvShow));
            } else if (id == 5) {
                LiveChannel liveChannel = new LiveChannel();
                liveChannel.setId(slider.getTargetId());
                homeActivity.goToFragment(LiveChannelFragment.newInstance(liveChannel));
            }
        }
    }
}

