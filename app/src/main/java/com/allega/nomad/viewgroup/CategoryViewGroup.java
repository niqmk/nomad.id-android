package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.allega.nomad.R;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.EdutainmentPreview;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.EventPreview;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.MoviePreview;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CategoryViewGroup extends RelativeLayout {

    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.background_image_view_1)
    protected ImageView backgroundImageView1;
    @InjectView(R.id.background_image_view_2)
    protected ImageView backgroundImageView2;
    @InjectView(R.id.background_image_view_3)
    protected ImageView backgroundImageView3;
    @InjectView(R.id.background_view_flipper)
    protected ViewFlipper backgroundViewFlipper;

    public CategoryViewGroup(Context context) {
        super(context);
        init();
    }

    public CategoryViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_category, this, true);
        ButterKnife.inject(this);
        backgroundViewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        backgroundViewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    public void bind(MoviePreview moviePreview) {
        List<Movie> preview = moviePreview.getMovies();

        if (preview.size() > 0) {
            Movie video1 = preview.get(0);
            if (video1.getImages() != null && video1.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video1.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView1);
            else
                backgroundImageView1.setImageResource(R.drawable.ic_no_image);
        }

        if (preview.size() > 1) {
            Movie video2 = preview.get(1);
            if (video2.getImages() != null && video2.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video2.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView2);
            else
                backgroundImageView2.setImageResource(R.drawable.ic_no_image);
        }

        if (preview.size() > 2) {
            Movie video3 = preview.get(2);
            if (video3.getImages() != null && video3.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video3.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView3);
            else
                backgroundImageView3.setImageResource(R.drawable.ic_no_image);
        }

        titleTextView.setText(moviePreview.getName());
    }

    public void bind(EventPreview eventPreview) {
        List<Event> preview = eventPreview.getEvents();

        if (preview.size() > 0) {
            Event video1 = preview.get(0);
            if (video1.getImages() != null && video1.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video1.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView1);
            else
                backgroundImageView1.setImageResource(R.drawable.ic_no_image);
        }

        if (preview.size() > 1) {
            Event video2 = preview.get(1);
            if (video2.getImages() != null && video2.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video2.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView2);
            else
                backgroundImageView2.setImageResource(R.drawable.ic_no_image);
        }

        if (preview.size() > 2) {
            Event video3 = preview.get(2);
            if (video3.getImages() != null && video3.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video3.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView3);
            else
                backgroundImageView3.setImageResource(R.drawable.ic_no_image);
        }

        titleTextView.setText(eventPreview.getName());
    }

    public void bind(EdutainmentPreview edutainmentPreview) {
        List<Edutainment> preview = edutainmentPreview.getEdutainments();

        if (preview.size() > 0) {
            Edutainment video1 = preview.get(0);
            if (video1.getImages() != null && video1.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video1.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView1);
            else
                backgroundImageView1.setImageResource(R.drawable.ic_no_image);
        }

        if (preview.size() > 1) {
            Edutainment video2 = preview.get(1);
            if (video2.getImages() != null && video2.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video2.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView2);
            else
                backgroundImageView2.setImageResource(R.drawable.ic_no_image);
        }

        if (preview.size() > 2) {
            Edutainment video3 = preview.get(2);
            if (video3.getImages() != null && video3.getImages().getThumb() != null)
                Glide.with(getContext()).load(ImageController.getThumb(video3.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView3);
            else
                backgroundImageView3.setImageResource(R.drawable.ic_no_image);
        }
        titleTextView.setText(edutainmentPreview.getName());
    }
}
