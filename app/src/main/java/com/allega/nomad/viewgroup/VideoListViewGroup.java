package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.DownloadProgressEvent;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.Images;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.Videos;
import com.allega.nomad.entity.WatchListVideo;
import com.allega.nomad.util.VideoUtil;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class VideoListViewGroup extends RelativeLayout {

    @InjectView(R.id.background_image_view)
    protected ImageView backgroundImageView;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.watch_list_text_view)
    protected TextView watchListTextView;
    @InjectView(R.id.watch_text_view)
    protected TextView watchTextView;
    @InjectView(R.id.progress_text_view)
    protected TextView progressTextView;
    @InjectView(R.id.download_progress_bar)
    protected ProgressBar downloadProgressBar;
    private Videos videos;

    public VideoListViewGroup(Context context) {
        super(context);
        init();
    }

    public VideoListViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoListViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_video_list, this, true);
        ButterKnife.inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Bus.getInstance().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        Bus.getInstance().unregister(this);
        super.onDetachedFromWindow();
    }

    public void bind(WatchListVideo watchListVideo) {
        this.videos = watchListVideo.getDetail().getVideos();
        titleTextView.setText(watchListVideo.getDetail().getTitle());
        Images images = watchListVideo.getDetail().getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(images)).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        downloadProgressBar.setVisibility(GONE);
        progressTextView.setVisibility(GONE);
    }

    public void bind(Movie movie) {
        this.videos = movie.getVideos();
        titleTextView.setText(movie.getTitle());
        Images images = movie.getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(images)).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        downloadProgressBar.setVisibility(GONE);
        progressTextView.setVisibility(GONE);
    }

    public void bind(Event event) {
        titleTextView.setText(event.getTitle());
    }

    public void bind(Edutainment edutainment) {
        this.videos = edutainment.getVideos();
        titleTextView.setText(edutainment.getTitle());
        Images images = edutainment.getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(images)).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        downloadProgressBar.setVisibility(GONE);
        progressTextView.setVisibility(GONE);
    }


    public void updateProgress(DownloadProgressEvent downloadProgressEvent) {
        if (videos != null && VideoUtil.getVideo(videos) != null && VideoUtil.getVideo(videos).equals(downloadProgressEvent.getUrl())) {
            float current = downloadProgressEvent.getCurrent() * 100.00f;
            int progress = Math.round(current / downloadProgressEvent.getMax());
            if (progress < 100) {
                downloadProgressBar.setProgress(progress);
                progressTextView.setText(String.valueOf(progress) + "%");
                downloadProgressBar.setVisibility(VISIBLE);
                progressTextView.setVisibility(VISIBLE);
            } else {
                downloadProgressBar.setVisibility(GONE);
                progressTextView.setVisibility(GONE);
            }
        } else {
            downloadProgressBar.setVisibility(GONE);
            progressTextView.setVisibility(GONE);
        }
    }
}
