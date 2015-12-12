package com.allega.nomad.viewgroup;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.allega.nomad.R;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.entity.MovieClip;
import com.allega.nomad.util.VideoUtil;
import com.bumptech.glide.Glide;
import com.ctrlplusz.anytextview.AnyTextView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ClipViewGroup extends RelativeLayout implements SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private static final int INTERVAL = 150;
    private final String ZERO = "0";

    @InjectView(R.id.background_image_view)
    protected ImageView backgroundImageView;
    @InjectView(R.id.video_view)
    protected VideoView videoView;
    @InjectView(R.id.play_button)
    protected ImageView playButton;
    @InjectView(R.id.root_layout)
    protected RelativeLayout rootLayout;
    @InjectView(R.id.play_small_button)
    protected ImageView playSmallButton;
    @InjectView(R.id.progress_seek_bar)
    protected SeekBar progressSeekBar;
    @InjectView(R.id.remaining_text_view)
    protected AnyTextView remainingTextView;
    @InjectView(R.id.media_control_container)
    protected LinearLayout mediaControlContainer;
    private boolean isStop;
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (isStop)
                return;
            try {
                int current = videoView.getCurrentPosition();
                int total = videoView.getDuration();

                progressSeekBar.setProgress(current);
                progressSeekBar.setMax(total);
                long remaining = total - current;
                long hour = TimeUnit.MILLISECONDS.toHours(remaining);
                long minute = TimeUnit.MILLISECONDS.toMinutes(remaining) % TimeUnit.HOURS.toMinutes(1);
                long second = TimeUnit.MILLISECONDS.toSeconds(remaining) % TimeUnit.MINUTES.toSeconds(1);
                StringBuffer time = new StringBuffer();
                time.append("-");
                if (hour > 0) {
                    time.append(String.format("%02d", hour));
                    time.append(":");
                }
                if (minute > 0 || hour > 0) {
                    time.append(String.format("%02d", minute));
                    time.append(":");
                }
                if (second > 0 || minute > 0 || hour > 0) {
                    time.append(String.format("%02d", second));
                }
                remainingTextView.setText(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(runnable, INTERVAL);
        }

    };

    public ClipViewGroup(Context context) {
        super(context);
        init();
    }

    public ClipViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_clip, this, true);
        ButterKnife.inject(this);
        progressSeekBar.setOnSeekBarChangeListener(this);
    }

    public void bind(MovieClip movieClip) {
        Glide.with(getContext()).load(ImageController.getLandscape(movieClip.getImages(), 150)).skipMemoryCache(true).placeholder(R.drawable.image_placeholder).into(backgroundImageView);
        videoView.stopPlayback();
        hideVideo();

        videoView.setVideoURI(Uri.parse(VideoUtil.getVideo(movieClip.getVideos())));
        videoView.setOnCompletionListener(this);
        videoView.setOnErrorListener(this);
    }

    public void setupSingle() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        rootLayout.setLayoutParams(layoutParams);
        mediaControlContainer.setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.padding_small));
    }

    private void showVideo() {
        playButton.setVisibility(GONE);
        backgroundImageView.setVisibility(GONE);
        videoView.setVisibility(VISIBLE);
    }

    private void hideVideo() {
        stopTracking();
        playButton.setVisibility(VISIBLE);
        backgroundImageView.setVisibility(VISIBLE);
        videoView.setVisibility(INVISIBLE);
        mediaControlContainer.setVisibility(INVISIBLE);
    }

    @OnClick(R.id.play_small_button)
    protected void onPlaySmall() {
        if (isStop) {
            startTracking();
        } else {
            stopTracking();
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (isStop)
            videoView.seekTo(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        stopTracking();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        startTracking();
    }

    private void startTracking() {
        isStop = false;
        videoView.start();
        playSmallButton.setImageResource(R.drawable.ic_media_pause);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, INTERVAL);
    }

    public void stopTracking() {
        playSmallButton.setImageResource(R.drawable.ic_media_play);
        videoView.pause();
        isStop = true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        hideVideo();
    }

    @OnClick(R.id.play_button)
    protected void onPlay() {
        showVideo();
        videoView.start();
        mediaControlContainer.setVisibility(VISIBLE);
        startTracking();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        hideVideo();
        return false;
    }
}

