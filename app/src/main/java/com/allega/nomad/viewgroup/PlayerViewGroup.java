package com.allega.nomad.viewgroup;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.DownloadFinishEvent;
import com.allega.nomad.bus.event.DownloadPauseEvent;
import com.allega.nomad.bus.event.DownloadProgressEvent;
import com.allega.nomad.bus.event.PlayCountYoutubeEvent;
import com.allega.nomad.controller.ImageController;
import com.allega.nomad.controller.encrypt.aes.ADZAESException;
import com.allega.nomad.controller.encrypt.aes.ADZAESGenerator;
import com.allega.nomad.dialog.SimpleDialog;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.Images;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.LogOnMember;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.PendingDownload;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.entity.Videos;
import com.allega.nomad.other.youtube.YoutubeFragment;
import com.allega.nomad.service.DownloadService;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.FileManager;
import com.allega.nomad.storage.PreferenceProvider;
import com.allega.nomad.util.VideoUtil;
import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;
import io.realm.Realm;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by imnotpro on 5/30/15.
 */
public class PlayerViewGroup extends RelativeLayout implements SeekBar.OnSeekBarChangeListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private static final int INTERVAL = 150;
    private final String ZERO = "0";
    private final Object lock = new Object();
    @InjectView(R.id.video_view)
    protected CustomVideoView videoView;
    @InjectView(R.id.background_image_view)
    protected ImageView backgroundImageView;
    @InjectView(R.id.portrait_image_view)
    protected ImageView portraitImageView;
    @InjectView(R.id.play_button)
    protected ImageView playButton;
    @InjectView(R.id.progress_seek_bar)
    protected SeekBar progressSeekBar;
    @InjectView(R.id.remaining_text_view)
    protected TextView remainingTextView;
    @InjectView(R.id.media_control_container)
    protected LinearLayout mediaControlContainer;
    @InjectView(R.id.fragment_container)
    protected FrameLayout fragmentContainer;
    @InjectView(R.id.watch_button)
    protected ImageView watchButton;
    @InjectView(R.id.progress_bar)
    protected DonutProgress progressBar;
    @InjectView(R.id.progress_text_view)
    protected TextView progressTextView;
    @InjectView(R.id.progress_layout)
    protected LinearLayout progressLayout;
    @InjectView(R.id.rotate_button)
    protected ImageView rotateButton;
    @State
    int currentPosition;
    private DecryptFileAsyncTask decryptFileAsyncTask;
    private PlayerListener listener;
    private Videos videos;
    private Realm realm;
    private Boolean isPlay;
    private long pointReward = 0;
    private long urlTypeId;
    private boolean isEncripted = false;
    private FragmentManager fragmentManager;

    private PreferenceProvider preferenceProvider;
    private String download;
    private String processing;
    private LogOnMember logOnMember;
    private String title;
    private boolean isStop;
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (isStop)
                return;
            try {
                currentPosition = videoView.getCurrentPosition();
                int total = videoView.getDuration();

                progressSeekBar.setProgress(currentPosition);
                progressSeekBar.setMax(total);
                long remaining = total - currentPosition;
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

    public PlayerViewGroup(Context context) {
        super(context);
        init();
    }

    public PlayerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_player, this, true);
        ButterKnife.inject(this);
        progressSeekBar.setOnSeekBarChangeListener(this);
        realm = DatabaseManager.getInstance(getContext());
        download = getContext().getString(R.string.downloading);
        processing = getContext().getString(R.string.processing);
        preferenceProvider = new PreferenceProvider(getContext());
        videoView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onPlay();
                return false;
            }
        });
        videoView.setOnErrorListener(this);
        videoView.setOnCompletionListener(this);
        fragmentContainer.setVisibility(GONE);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            setLayoutParams(params);
            rotateButton.setImageResource(R.drawable.ic_fullscreen_exit);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.main_video_height));
            setLayoutParams(params);
            rotateButton.setImageResource(R.drawable.ic_fullscreen);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Bus.getInstance().register(this);
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return Icepick.saveInstanceState(this, super.onSaveInstanceState());
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(Icepick.restoreInstanceState(this, state));
        videoView.seekTo(currentPosition);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        Bus.getInstance().unregister(this);
        super.onDetachedFromWindow();
    }

    public void bind(Ad ad) {
        urlTypeId = ad.getUrlTypeId();
        pointReward = ad.getPointReward();
        title = ad.getTitle();
        Images images = ad.getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(ad.getImages())).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        isPlay = true;
    }

    public void bind(Movie movie) {
        isEncripted = true;
        title = movie.getTitle();
        Images images = movie.getImages();
        if (images != null) {
            Glide.with(getContext()).load(ImageController.getLandscape(images)).bitmapTransform(new BlurTransformation(getContext(), Glide.get(getContext()).getBitmapPool())).into(backgroundImageView);
            portraitImageView.setVisibility(VISIBLE);
            Glide.with(getContext()).load(ImageController.getPortait(images)).into(portraitImageView);
        } else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);

        logOnMember = movie.getLogOnMember();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(movie.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null && date.compareTo(new Date()) > 0) {
            watchButton.setVisibility(GONE);
        }
        isPlay = logOnMember != null && logOnMember.getPurchase() != null && logOnMember.getPurchase().isPurchased();
    }

    public void bind(Event event) {
        urlTypeId = event.getUrlTypeId();
        mediaControlContainer.setVisibility(GONE);
        title = event.getTitle();
        Images images = event.getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(images)).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        logOnMember = event.getLogOnMember();
        isPlay = logOnMember != null && logOnMember.getPurchase() != null && logOnMember.getPurchase().isPurchased();
    }

    public void bind(Edutainment edutainment) {
        urlTypeId = edutainment.getUrlTypeId();
        mediaControlContainer.setVisibility(GONE);
        title = edutainment.getTitle();
        Images images = edutainment.getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(images)).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        logOnMember = edutainment.getLogOnMember();
        isPlay = logOnMember != null && logOnMember.getPurchase() != null && logOnMember.getPurchase().isPurchased();
    }

    public void bind(TvShowEpisode tvShowEpisode) {
        urlTypeId = tvShowEpisode.getUrlTypeId();
        mediaControlContainer.setVisibility(GONE);
        title = tvShowEpisode.getTitle();
        Images images = tvShowEpisode.getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(images)).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        logOnMember = tvShowEpisode.getLogOnMember();
        isPlay = logOnMember != null && logOnMember.getPurchase() != null && logOnMember.getPurchase().isPurchased();
    }

    public void bind(SerialEpisode serialEpisode) {
        urlTypeId = serialEpisode.getUrlTypeId();
        mediaControlContainer.setVisibility(GONE);
        title = serialEpisode.getTitle();
        Images images = serialEpisode.getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(images)).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        logOnMember = serialEpisode.getLogOnMember();
        isPlay = logOnMember != null && logOnMember.getPurchase() != null && logOnMember.getPurchase().isPurchased();
    }

    public void bind(LiveChannel liveChannel) {
        urlTypeId = liveChannel.getUrlTypeId();
        mediaControlContainer.setVisibility(GONE);
        title = liveChannel.getTitle();
        Images images = liveChannel.getImages();
        if (images != null)
            Glide.with(getContext()).load(ImageController.getLandscape(images)).into(backgroundImageView);
        else
            backgroundImageView.setImageResource(R.drawable.ic_no_image);
        logOnMember = liveChannel.getLogOnMember();
        isPlay = logOnMember != null && logOnMember.getPurchase() != null && logOnMember.getPurchase().isPurchased();
    }

    public void bind(Videos videos) {
        this.videos = videos;
        fragmentContainer.removeAllViews();
        videoView.setVisibility(VISIBLE);
        if (!videoView.isPlaying()) {
            backgroundImageView.setVisibility(VISIBLE);
        }
        if (isEncripted) {
            File file = FileManager.getFile(getContext(), VideoUtil.getVideo(videos));
            if (file != null && file.exists()) {
                watchButton.setImageResource(R.drawable.ic_media_pause);
            }
        }
    }

    public void setupVideo(File videoPath) {
        if (decryptFileAsyncTask != null) {
            decryptFileAsyncTask.cancel(true);
        }
        decryptFileAsyncTask = new DecryptFileAsyncTask(getContext());
        decryptFileAsyncTask.execute(videoPath);
    }

    @OnClick(R.id.progress_layout)
    protected void pause() {
        DownloadService.stopDownload();
        progressLayout.setVisibility(GONE);
    }

    @OnClick(R.id.watch_button)
    public void onWatch() {
        if (preferenceProvider.getToken() == null) {
            SimpleDialog.showNeedLogin(getContext());
            return;
        }
        if (isPlay == null)
            return;
        if (!isPlay) {
            if (listener != null)
                listener.onBuy();
            return;
        }
        if (videos != null) {
            watchButton.setVisibility(GONE);
            if (urlTypeId == 2) {
                videoView.setVisibility(GONE);
                backgroundImageView.setVisibility(GONE);
                portraitImageView.setVisibility(GONE);
                Context context = getContext();
                if (context instanceof BaseActivity) {
                    fragmentContainer.setVisibility(VISIBLE);
                    BaseActivity baseActivity = (BaseActivity) context;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_container, YoutubeFragment.newInstance(VideoUtil.getVideo(videos), pointReward));
                    fragmentTransaction.commit();
                }
            } else if (isEncripted) {
                File file = FileManager.getFile(getContext(), VideoUtil.getVideo(videos));
                if (file == null)
                    return;
                if (DownloadService.isStillDownload(VideoUtil.getVideo(videos)))
                    return;
//                if (!file.exists()) {
                PendingDownload pendingDownload = realm.where(PendingDownload.class).equalTo(PendingDownload.FIELD_URL, VideoUtil.getVideo(videos)).findFirst();
                if (pendingDownload == null) {
                    pendingDownload = new PendingDownload();
                    pendingDownload.setUrl(VideoUtil.getVideo(videos));
                    pendingDownload.setLastTry(new Date());
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(pendingDownload);
                    realm.commitTransaction();
                }
                DownloadService.startService(getContext(), pendingDownload);
//                } else {
//                    setupVideo(file);
//                }
                progressLayout.setVisibility(VISIBLE);
                if (DownloadService.isStillDownload()) {
                    progressTextView.setText(R.string.still_download);
                    progressBar.setVisibility(GONE);
                }
            } else {
                videoView.setVideoPath(VideoUtil.getVideo(videos));
                if (listener != null)
                    listener.onPlay();
                backgroundImageView.setVisibility(GONE);
                portraitImageView.setVisibility(GONE);
                mediaControlContainer.setVisibility(VISIBLE);
                startTracking();
            }
        }
    }

    @OnClick(R.id.play_button)
    protected void onPlay() {
        if (isStop) {
            startTracking();
        } else {
            stopTracking();
        }
    }

    @OnClick(R.id.rotate_button)
    protected void onRotate() {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            final int orientation = activity.getResources().getConfiguration().orientation;
            switch (orientation) {
                case Configuration.ORIENTATION_PORTRAIT:
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case Configuration.ORIENTATION_LANDSCAPE:
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
            }
        }
    }

    public void setListener(PlayerListener listener, FragmentManager fragmentManager) {
        this.listener = listener;
        this.fragmentManager = fragmentManager;
    }

    public void onEventMainThread(PlayCountYoutubeEvent playCountYoutubeEvent) {
        if (videos != null && playCountYoutubeEvent.getVideoCode().equals(VideoUtil.getVideo(videos)) && listener != null) {
            listener.onPlay();
        }
    }

    public void onEventMainThread(DownloadProgressEvent downloadProgressEvent) {
        if (videos != null && VideoUtil.getVideo(videos) != null && downloadProgressEvent.getUrl().equals(VideoUtil.getVideo(videos))) {
            float current = downloadProgressEvent.getCurrent() * 100.00f;
            int progress = Math.round(current / downloadProgressEvent.getMax());
            progressTextView.setText(R.string.downloading);
            progressBar.setProgress(progress);
            watchButton.setVisibility(GONE);
            progressBar.setVisibility(VISIBLE);
            progressLayout.setVisibility(VISIBLE);
        }
    }

    public void onEventMainThread(DownloadPauseEvent downloadPauseEvent) {
        if (videos != null) {
            String url = VideoUtil.getVideo(videos);
            if (url != null && url.equals(downloadPauseEvent.getUrl())) {
                progressLayout.setVisibility(GONE);
                watchButton.setImageResource(R.drawable.ic_media_pause);
                watchButton.setVisibility(VISIBLE);
            }
        }
    }

    public void onEventMainThread(DownloadFinishEvent downloadFinishEvent) {
        if (videos != null) {
            File file = FileManager.getFile(getContext(), VideoUtil.getVideo(videos));
            if (file != null && file.exists() && file.equals(downloadFinishEvent.getFile())) {
                setupVideo(file);
            }
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (isStop) {
            currentPosition = progress;
            videoView.seekTo(currentPosition);
        }
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
        videoView.seekTo(currentPosition);
        videoView.start();
        playButton.setImageResource(R.drawable.ic_media_pause);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, INTERVAL);
    }

    public void stopTracking() {
        playButton.setImageResource(R.drawable.ic_media_play);
        videoView.pause();
        currentPosition = videoView.getCurrentPosition();
        isStop = true;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        watchButton.setVisibility(VISIBLE);
        videoView.setVisibility(GONE);
        backgroundImageView.setVisibility(VISIBLE);
        portraitImageView.setVisibility(VISIBLE);
        mediaControlContainer.setVisibility(GONE);
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        watchButton.setVisibility(VISIBLE);
        videoView.setVisibility(GONE);
        backgroundImageView.setVisibility(VISIBLE);
        portraitImageView.setVisibility(VISIBLE);
        mediaControlContainer.setVisibility(GONE);
        if (listener != null)
            listener.onFinish();
    }

    public interface PlayerListener {
        void onFinish();

        void onPlay();

        void onBuy();
    }

    private class DecryptFileAsyncTask extends AsyncTask<File, Integer, File> {

        private final int SIZE = 1024 * 10;
        private final String key = "qwertyuiopasdfghjklzxcvbnm";
        private Context context;

        public DecryptFileAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(File... params) {
            if (params.length > 0) {
                File file = params[0];
                File decryptFile = new File(FileManager.getCacheFolder(context), file.getName());
                if (decryptFile.exists() && decryptFile.length() == file.length())
                    return decryptFile;
                FileInputStream fileInputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    fileInputStream = new FileInputStream(file);
                    fileOutputStream = new FileOutputStream(decryptFile);

                    byte[] bufferData = new byte[SIZE];
                    int length;
                    long total = 0;
                    while ((length = fileInputStream.read(bufferData)) > 0) {
                        total += length;
                        byte[] decryptByte = ADZAESGenerator.decrypt(bufferData, key, SIZE);
                        fileOutputStream.write(decryptByte, 0, length);
                        long progress = Math.round(total * 100.00d / file.length());
                        publishProgress((int) progress);
                    }
                    fileOutputStream.flush();
                    return decryptFile;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ADZAESException e) {
                    e.printStackTrace();
                } finally {
                    if (fileInputStream != null)
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (fileOutputStream != null)
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            progressTextView.setText(R.string.processing);
            progressBar.setVisibility(VISIBLE);
            progressLayout.setVisibility(VISIBLE);
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            progressLayout.setVisibility(GONE);
            if (file != null && file.exists()) {
                videoView.setVideoPath(file.getAbsolutePath());
//                goFullScreen();
                if (listener != null)
                    listener.onPlay();
                backgroundImageView.setVisibility(GONE);
                portraitImageView.setVisibility(GONE);
                mediaControlContainer.setVisibility(VISIBLE);
                startTracking();
            }
        }
    }
}
