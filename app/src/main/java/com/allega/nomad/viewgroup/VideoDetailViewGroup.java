package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.dialog.SimpleDialog;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.LogOnMember;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.Price;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.model.Purchase;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.storage.PreferenceProvider;
import com.allega.nomad.util.TimeUtil;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by imnotpro on 7/20/15.
 */
public class VideoDetailViewGroup extends RelativeLayout {

    private final String ZERO = "0";
    private final AppRestService appRestService = AppRestController.getAppRestService();

    @InjectView(R.id.like_button)
    protected ImageView likeButton;
    @InjectView(R.id.price_text_view)
    protected TextView priceTextView;
    @InjectView(R.id.currency_image_view)
    protected ImageView currencyImageView;
    @InjectView(R.id.buy_button)
    protected LinearLayout buyButton;
    @InjectView(R.id.like_progress_bar)
    protected ProgressBar likeProgressBar;
    @InjectView(R.id.watch_list_layout)
    protected FrameLayout watchListLayout;
    @InjectView(R.id.rating_text_view)
    protected TextView ratingTextView;
    @InjectView(R.id.rating_layout)
    protected LinearLayout ratingLayout;
    private boolean isAd;

    private VideoDetailListener videoDetailListener;
    private LogOnMember logOnMember;
    private List<Price> priceList;
    private PreferenceProvider preferenceProvider;

    public VideoDetailViewGroup(Context context) {
        super(context);
        init();
    }

    public VideoDetailViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoDetailViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_video_detail, this, true);
        ButterKnife.inject(this);
        preferenceProvider = new PreferenceProvider(getContext());
    }

    public void bind(Ad ad) {
        priceTextView.setText(getResources().getString(R.string.plus_point, ad.getPointReward()));
        likeButton.setVisibility(GONE);
        isAd = true;
    }

    public void bind(Movie movie) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(movie.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null && date.compareTo(new Date()) > 0) {
            buyButton.setBackgroundResource(R.drawable.background_round_border_light_gray);
            priceTextView.setText(getResources().getString(R.string.release_date, movie.getReleaseDate()));
            priceTextView.setTextColor(getResources().getColor(R.color.light_gray));
            currencyImageView.setVisibility(GONE);

        } else {
            priceList = movie.getPrices();
            if (priceList != null && priceList.size() > 0) {
                String price = priceList.get(0).getPrice();
                if (price.equals(ZERO)) {
                    buyButton.setBackgroundResource(R.drawable.background_round_border_light_gray);
                    priceTextView.setText(R.string.free);
                    priceTextView.setTextColor(getResources().getColor(R.color.light_gray));
                    currencyImageView.setVisibility(GONE);
                } else {
                    buyButton.setBackgroundResource(R.drawable.background_round_border_price);
                    priceTextView.setText(price);
                    priceTextView.setTextColor(getResources().getColor(R.color.white));
                    currencyImageView.setVisibility(VISIBLE);
                }
            }
        }
        ratingLayout.setVisibility(VISIBLE);
        ratingTextView.setText(String.valueOf(movie.getOverallRating()));
        if (movie.getLogOnMember() != null) {
            logOnMember = movie.getLogOnMember();
            bind(logOnMember);
        }
    }

    public void bind(Event event) {
        priceList = event.getPrices();
        if (priceList != null && priceList.size() > 0) {
            String price = priceList.get(0).getPrice();
            if (price.equals(ZERO)) {
                buyButton.setBackgroundResource(R.drawable.background_round_border_light_gray);
                priceTextView.setText(R.string.free);
                priceTextView.setTextColor(getResources().getColor(R.color.light_gray));
                currencyImageView.setVisibility(GONE);
            } else {
                buyButton.setBackgroundResource(R.drawable.background_round_border_price);
                priceTextView.setText(price);
                priceTextView.setTextColor(getResources().getColor(R.color.white));
                currencyImageView.setVisibility(VISIBLE);
            }
        }
        if (event.getLogOnMember() != null) {
            logOnMember = event.getLogOnMember();
            bind(logOnMember);
        }
    }

    public void bind(Edutainment edutainment) {
        priceList = edutainment.getPrices();
        if (priceList != null && priceList.size() > 0) {
            String price = priceList.get(0).getPrice();
            if (price.equals(ZERO)) {
                buyButton.setBackgroundResource(R.drawable.background_round_border_light_gray);
                priceTextView.setText(R.string.free);
                priceTextView.setTextColor(getResources().getColor(R.color.light_gray));
                currencyImageView.setVisibility(GONE);
            } else {
                buyButton.setBackgroundResource(R.drawable.background_round_border_price);
                priceTextView.setText(price);
                priceTextView.setTextColor(getResources().getColor(R.color.white));
                currencyImageView.setVisibility(VISIBLE);
            }
        }
        if (edutainment.getLogOnMember() != null) {
            logOnMember = edutainment.getLogOnMember();
            bind(logOnMember);
        }
    }

    public void bind(LiveChannel liveChannel) {
        priceList = liveChannel.getPrices();
        if (priceList != null && priceList.size() > 0) {
            String price = priceList.get(0).getPrice();
            if (price.equals(ZERO)) {
                buyButton.setBackgroundResource(R.drawable.background_round_border_light_gray);
                priceTextView.setText(R.string.free);
                priceTextView.setTextColor(getResources().getColor(R.color.light_gray));
                currencyImageView.setVisibility(GONE);
            } else {
                buyButton.setBackgroundResource(R.drawable.background_round_border_price);
                priceTextView.setText(price);
                priceTextView.setTextColor(getResources().getColor(R.color.white));
                currencyImageView.setVisibility(VISIBLE);
            }
        }
        if (liveChannel.getLogOnMember() != null) {
            logOnMember = liveChannel.getLogOnMember();
            bind(logOnMember);
        }
    }

    public void bind(TvShowEpisode tvShowEpisode) {
        priceList = tvShowEpisode.getPrices();
        if (priceList != null && priceList.size() > 0) {
            String price = priceList.get(0).getPrice();
            if (price.equals(ZERO)) {
                buyButton.setBackgroundResource(R.drawable.background_round_border_light_gray);
                priceTextView.setText(R.string.free);
                priceTextView.setTextColor(getResources().getColor(R.color.light_gray));
                currencyImageView.setVisibility(GONE);
            } else {
                buyButton.setBackgroundResource(R.drawable.background_round_border_price);
                priceTextView.setText(price);
                priceTextView.setTextColor(getResources().getColor(R.color.white));
                currencyImageView.setVisibility(VISIBLE);
            }
        }
        if (tvShowEpisode.getLogOnMember() != null) {
            logOnMember = tvShowEpisode.getLogOnMember();
            bind(logOnMember);
        }
    }

    public void bind(SerialEpisode serialEpisode) {
        priceList = serialEpisode.getPrices();
        if (priceList != null && priceList.size() > 0) {
            String price = priceList.get(0).getPrice();
            if (price.equals(ZERO)) {
                buyButton.setBackgroundResource(R.drawable.background_round_border_light_gray);
                priceTextView.setText(R.string.free);
                priceTextView.setTextColor(getResources().getColor(R.color.light_gray));
                currencyImageView.setVisibility(GONE);
            } else {
                buyButton.setBackgroundResource(R.drawable.background_round_border_price);
                priceTextView.setText(price);
                priceTextView.setTextColor(getResources().getColor(R.color.white));
                currencyImageView.setVisibility(VISIBLE);
            }
        }
        if (serialEpisode.getLogOnMember() != null) {
            logOnMember = serialEpisode.getLogOnMember();
            bind(logOnMember);
        }
    }

    private void bind(LogOnMember logOnMember) {
        this.logOnMember = logOnMember;
        likeButton.setVisibility(VISIBLE);
        likeProgressBar.setVisibility(GONE);
        likeButton.setSelected(logOnMember.isWatchListed());
        Purchase purchase = logOnMember.getPurchase();
        if (purchase != null && purchase.isPurchased()) {
            String status = getResources().getString(R.string.rented, Days.daysBetween(new LocalDate(new Date().getTime()), new LocalDate(TimeUtil.addCurrentTimeZone(purchase.getExpiredAt()))).getDays());
            priceTextView.setText(status);
            buyButton.setBackgroundResource(R.drawable.background_round_border_light_gray);
            priceTextView.setTextColor(getResources().getColor(R.color.light_gray));
            currencyImageView.setVisibility(GONE);
        }
    }

    @OnClick(R.id.like_button)
    protected void onLike() {
        if (preferenceProvider.getToken() == null) {
            SimpleDialog.showNeedLogin(getContext());
            return;
        }
        likeButton.setVisibility(GONE);
        likeProgressBar.setVisibility(VISIBLE);
        if (logOnMember.isWatchListed()) {
            if (videoDetailListener != null)
                videoDetailListener.onUnLike();
        } else {
            if (videoDetailListener != null)
                videoDetailListener.onLike();
        }
    }

    public void showLike() {
        likeButton.setVisibility(VISIBLE);
        likeProgressBar.setVisibility(GONE);
    }

    @OnClick(R.id.buy_button)
    protected void onBuy() {
        if (isAd) {
            if (videoDetailListener != null) {
                videoDetailListener.onBuy();
            }
            return;
        }
        Purchase purchase = logOnMember.getPurchase();
        if (preferenceProvider.getToken() == null) {
            SimpleDialog.showNeedLogin(getContext());
        } else if (logOnMember.getPurchase() != null && purchase.isPurchased()) {

        } else if (priceList != null && priceList.size() > 0 && videoDetailListener != null) {
            videoDetailListener.onBuy();
        }
    }

    public void setVideoDetailListener(VideoDetailListener videoDetailListener) {
        this.videoDetailListener = videoDetailListener;
    }

    public interface VideoDetailListener {

        void onWatchList();

        void onLike();

        void onUnLike();

        void onBuy();
    }
}
