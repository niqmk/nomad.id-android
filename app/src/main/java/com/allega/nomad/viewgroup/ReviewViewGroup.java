package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.entity.AdComment;
import com.allega.nomad.entity.EdutainmentComment;
import com.allega.nomad.entity.EventComment;
import com.allega.nomad.entity.LiveChannelComment;
import com.allega.nomad.entity.MovieComment;
import com.allega.nomad.entity.SerialEpisodeComment;
import com.allega.nomad.entity.TvShowEpisodeComment;
import com.allega.nomad.storage.PreferenceProvider;
import com.allega.nomad.util.TimeUtil;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ReviewViewGroup extends RelativeLayout {
    @InjectView(R.id.avatar_image_view)
    protected CircleImageView avatarImageView;
    @InjectView(R.id.name_text_view)
    protected TextView nameTextView;
    @InjectView(R.id.time_text_view)
    protected TextView timeTextView;
    @InjectView(R.id.comment_text_view)
    protected TextView commentTextView;
    @InjectView(R.id.rating_bar)
    protected RatingBar ratingBar;

    private PreferenceProvider preferenceProvider;

    public ReviewViewGroup(Context context) {
        super(context);
        init();
    }

    public ReviewViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReviewViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_review, this, true);
        ButterKnife.inject(this);
        preferenceProvider = new PreferenceProvider(getContext());
    }

    public void bind(AdComment item, int position) {
        nameTextView.setText(item.getMember().getUsername());
        commentTextView.setText(item.getDescription());
        timeTextView.setText(TimeUtil.longToDate(item.getCreatedAt()));
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }

    public void bind(MovieComment item, int position) {
        ratingBar.setVisibility(VISIBLE);
        ratingBar.setRating(item.getRate());
        if (preferenceProvider.getMember() != null && item.getMemberId() == preferenceProvider.getMember().getId()) {
            nameTextView.setTextColor(getResources().getColor(R.color.red));
            nameTextView.setText(R.string.you_rate_it);
        } else {
            nameTextView.setTextColor(getResources().getColor(R.color.black));
            nameTextView.setText(item.getMember().getUsername());
        }
        commentTextView.setText(item.getDescription());
        timeTextView.setText(TimeUtil.longToDate(item.getCreatedAt()));
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }

    public void bind(EventComment item, int position) {
        nameTextView.setText(item.getMember().getUsername());
        commentTextView.setText(item.getDescription());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(item.getCreatedAt());
        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(item.getCreatedAt()));
        timeTextView.setText(TimeUtil.longToDate(item.getCreatedAt()));
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }

    public void bind(EdutainmentComment item, int position) {
        nameTextView.setText(item.getMember().getUsername());
        commentTextView.setText(item.getDescription());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(item.getCreatedAt());
        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(item.getCreatedAt()));
        timeTextView.setText(TimeUtil.longToDate(item.getCreatedAt()));
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }

    public void bind(TvShowEpisodeComment item, int position) {
        nameTextView.setText(item.getMember().getUsername());
        commentTextView.setText(item.getDescription());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(item.getCreatedAt());
        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(item.getCreatedAt()));
        timeTextView.setText(TimeUtil.longToDate(item.getCreatedAt()));
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }

    public void bind(SerialEpisodeComment item, int position) {
        nameTextView.setText(item.getMember().getUsername());
        commentTextView.setText(item.getDescription());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(item.getCreatedAt());
        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(item.getCreatedAt()));
        timeTextView.setText(TimeUtil.longToDate(item.getCreatedAt()));
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }

    public void bind(LiveChannelComment item, int position) {
        nameTextView.setText(item.getMember().getUsername());
        commentTextView.setText(item.getDescription());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(item.getCreatedAt());
        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(item.getCreatedAt()));
        timeTextView.setText(TimeUtil.longToDate(item.getCreatedAt()));
        if (position % 2 == 0) {
            setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }
}
