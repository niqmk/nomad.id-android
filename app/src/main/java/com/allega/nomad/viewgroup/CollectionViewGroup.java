package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.activity.home.fragment.livechannel.LiveChannelFragment;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.activity.home.fragment.tvshow.TvShowFragment;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.DownloadFinishEvent;
import com.allega.nomad.bus.event.DownloadPauseEvent;
import com.allega.nomad.bus.event.DownloadProgressEvent;
import com.allega.nomad.dialog.DeleteConfirmationDialog;
import com.allega.nomad.entity.Collection;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.entity.Videos;
import com.allega.nomad.service.DownloadService;
import com.allega.nomad.storage.FileManager;
import com.allega.nomad.util.VideoUtil;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CollectionViewGroup extends CardView implements View.OnLongClickListener, View.OnClickListener {

    @InjectView(R.id.name_text_view)
    protected TextView nameTextView;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;
    @InjectView(R.id.play_button)
    protected ImageView playButton;
    @InjectView(R.id.download_progress_bar)
    protected DonutProgress downloadProgressBar;
    @InjectView(R.id.cancel_button)
    protected ImageView cancelButton;
    @InjectView(R.id.progress_layout)
    protected RelativeLayout progressLayout;
    @InjectView(R.id.root_layout)
    protected CardView rootLayout;

    private Collection collection;

    public CollectionViewGroup(Context context) {
        super(context);
        init();
    }

    public CollectionViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CollectionViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_collection, this, true);
        ButterKnife.inject(this);
        setOnLongClickListener(this);
        setOnClickListener(this);
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

    public void bind(Collection collection) {
        this.collection = collection;
        cancelButton.setVisibility(GONE);
        progressLayout.setVisibility(GONE);
        nameTextView.setText(collection.getDetail().getTitle());
        switch (collection.getVideoTypeId()) {
            case 1:
                descriptionTextView.setText(R.string.in_movies);
                break;
            case 2:
                descriptionTextView.setText(R.string.in_edutainments);
                break;
            case 3:
                descriptionTextView.setText(R.string.in_event);
                break;
            case 4:
                descriptionTextView.setText(R.string.in_tv_show);
                break;
            case 5:
                descriptionTextView.setText(R.string.in_live_channel);
                break;
        }
        File file = FileManager.getFile(getContext(), VideoUtil.getVideo(collection.getDetail().getVideos()));
        if (file != null && file.exists()) {
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
            nameTextView.setTextColor(getResources().getColor(R.color.black));
            descriptionTextView.setTextColor(getResources().getColor(R.color.black));
            playButton.setActivated(true);
        } else {
            rootLayout.setBackgroundColor(getResources().getColor(R.color.disable_white));
            nameTextView.setTextColor(getResources().getColor(R.color.gray));
            descriptionTextView.setTextColor(getResources().getColor(R.color.gray));
            playButton.setActivated(false);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        File file = FileManager.getFile(getContext(), VideoUtil.getVideo(collection.getDetail().getVideos()));
        if (file != null && file.exists()) {
            cancelButton.setVisibility(VISIBLE);
        } else {
            cancelButton.setVisibility(GONE);
        }
        return false;
    }

    @OnClick(R.id.play_button)
    protected void onPlay() {
        if (getContext() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getContext();
            switch (collection.getVideoTypeId()) {
                case 1:
                    Movie movie = new Movie();
                    movie.setId(collection.getDetail().getId());
                    homeActivity.goToFragment(MovieFragment.newInstance(movie));
                    break;
                case 2:
                    Event event = new Event();
                    event.setId(collection.getDetail().getId());
                    homeActivity.goToFragment(EventFragment.newInstance(event));
                    break;
                case 3:
                    Edutainment edutainment = new Edutainment();
                    edutainment.setId(collection.getDetail().getId());
                    homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainment));
                    break;
                case 4:
                    TvShowEpisode tvShowEpisode = new TvShowEpisode();
                    tvShowEpisode.setId(collection.getDetail().getId());
                    homeActivity.goToFragment(TvShowFragment.newInstance(tvShowEpisode));
                    break;
                case 5:
                    LiveChannel liveChannel = new LiveChannel();
                    liveChannel.setId(collection.getDetail().getId());
                    homeActivity.goToFragment(LiveChannelFragment.newInstance(liveChannel));
                    break;
            }
        }

    }

    protected void onPause() {
        DownloadService.stopDownload();
    }

    @OnClick(R.id.cancel_button)
    protected void onCancel() {
        if (getContext() instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) getContext();
            DeleteConfirmationDialog.show(baseActivity.getSupportFragmentManager(), collection.getId());
        }
    }

    public void onEventMainThread(DownloadProgressEvent downloadProgressEvent) {
        Videos videos = collection.getDetail().getVideos();
        if (videos != null && VideoUtil.getVideo(videos) != null && VideoUtil.getVideo(videos).equals(downloadProgressEvent.getUrl())) {
            float current = downloadProgressEvent.getCurrent() * 100.00f;
            int progress = Math.round(current / downloadProgressEvent.getMax());
            if (progress < 100) {
                downloadProgressBar.setProgress(progress);
                progressLayout.setVisibility(VISIBLE);
                cancelButton.setVisibility(GONE);
                playButton.setVisibility(GONE);
            } else {
                progressLayout.setVisibility(GONE);
                cancelButton.setVisibility(VISIBLE);
                playButton.setVisibility(VISIBLE);
            }
        }
    }

    public void onEventMainThread(DownloadPauseEvent downloadPauseEvent) {
        Videos videos = collection.getDetail().getVideos();
        if (videos != null) {
            String url = VideoUtil.getVideo(videos);
            if (url != null && url.equals(downloadPauseEvent.getUrl())) {
                progressLayout.setVisibility(GONE);
                playButton.setVisibility(VISIBLE);
            }
        }
    }

    public void onEventMainThread(DownloadFinishEvent downloadFinishEvent) {
        Videos videos = collection.getDetail().getVideos();
        if (videos != null) {
            File file = FileManager.getFile(getContext(), VideoUtil.getVideo(videos));
            if (file != null && file.exists() && file.equals(downloadFinishEvent.getFile())) {
                progressLayout.setVisibility(GONE);
                cancelButton.setVisibility(VISIBLE);
                playButton.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (DownloadService.isStillDownload(VideoUtil.getVideo(collection.getDetail().getVideos()))) {
            onPause();
        }
    }
}
