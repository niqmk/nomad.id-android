package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.Video;
import com.allega.nomad.model.CategoryPreview;
import com.allega.nomad.storage.DatabaseManager;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class HomeViewGroup extends RelativeLayout {

    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.label_image_view)
    protected TextView labelImageView;
    @InjectView(R.id.lock_image_view)
    protected ImageView lockImageView;
    @InjectView(R.id.play_image_view)
    protected ImageView playImageView;
    @InjectView(R.id.background_image_view_1)
    protected ImageView backgroundImageView1;
    @InjectView(R.id.background_image_view_2)
    protected ImageView backgroundImageView2;
    @InjectView(R.id.background_image_view_3)
    protected ImageView backgroundImageView3;
    @InjectView(R.id.background_view_flipper)
    protected ViewFlipper backgroundViewFlipper;
    private CategoryPreview categoryPreview;
    private Realm realm;

    public HomeViewGroup(Context context) {
        super(context);
        init();
    }

    public HomeViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_home, this, true);
        ButterKnife.inject(this);
        backgroundViewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        backgroundViewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        realm = DatabaseManager.getInstance(getContext());
    }

    public void bind(CategoryPreview categoryPreview) {
        this.categoryPreview = categoryPreview;
        titleTextView.setText(categoryPreview.getName());

        List<Video> preview = categoryPreview.getPreviews();

        if (preview == null || preview.size() == 0) {
            lockImageView.setVisibility(VISIBLE);
            playImageView.setVisibility(GONE);
            setEnabled(false);
        } else {
            playImageView.setVisibility(VISIBLE);
            lockImageView.setVisibility(GONE);
            setEnabled(true);
            if (preview.size() > 0) {
                Video video1 = preview.get(0);
                if (video1.getImages() != null && video1.getImages().getThumb() != null)
                    Glide.with(getContext()).load(ImageController.getThumb(video1.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView1);
                else
                    backgroundImageView1.setImageResource(R.drawable.ic_no_image);
            }

            if (preview.size() > 1) {
                Video video2 = preview.get(1);
                if (video2.getImages() != null && video2.getImages().getThumb() != null)
                    Glide.with(getContext()).load(ImageController.getThumb(video2.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView2);
                else
                    backgroundImageView2.setImageResource(R.drawable.ic_no_image);
            }

            if (preview.size() > 2) {
                Video video3 = preview.get(2);
                if (video3.getImages() != null && video3.getImages().getThumb() != null)
                    Glide.with(getContext()).load(ImageController.getThumb(video3.getImages())).placeholder(R.drawable.image_placeholder).error(R.drawable.ic_no_image).into(backgroundImageView3);
                else
                    backgroundImageView3.setImageResource(R.drawable.ic_no_image);
            }
        }

        labelImageView.setVisibility(GONE);
    }

    @OnClick(R.id.play_image_view)
    protected void onPlay() {
        if (lockImageView.getVisibility() == GONE)
            return;
        String name = categoryPreview.getName();
        int position = backgroundViewFlipper.getDisplayedChild();
        if (position > categoryPreview.getPreviews().size() || categoryPreview.getPreviews().size() == 0)
            return;
        Video video = categoryPreview.getPreviews().get(position);

        if (getContext() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getContext();
            switch (name) {
                case "Movie":
                    Movie movie = realm.where(Movie.class).equalTo(Movie.FIELD_ID, video.getId()).findFirst();
                    if (movie == null) {
                        movie = new Movie();
                        movie.setId(video.getId());
                    }
                    homeActivity.goToFragment(MovieFragment.newInstance(movie));
                    break;
                case "Edutainment":
                    Edutainment edutainment = realm.where(Edutainment.class).equalTo(Edutainment.FIELD_ID, video.getId()).findFirst();
                    if (edutainment == null) {
                        edutainment = new Edutainment();
                        edutainment.setId(video.getId());
                    }
                    homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainment));
                    break;
                case "Event":
                    Event event = realm.where(Event.class).equalTo(Event.FIELD_ID, video.getId()).findFirst();
                    if (event == null) {
                        event = new Event();
                        event.setId(video.getId());
                    }
                    homeActivity.goToFragment(EventFragment.newInstance(event));
                    break;
            }
        }

    }
}
