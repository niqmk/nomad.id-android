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
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class AdCategoryViewGroup extends RelativeLayout {

    @InjectView(R.id.cover_image_view)
    protected ImageView coverImageView;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.point_text_view)
    protected TextView pointTextView;

    public AdCategoryViewGroup(Context context) {
        super(context);
        init();
    }

    public AdCategoryViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdCategoryViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_ad_category, this, true);
        ButterKnife.inject(this);
    }

    public void bind(Ad item) {
        titleTextView.setText(item.getTitle());
        Glide.with(getContext()).load(ImageController.getLandscape(item.getImages())).placeholder(R.drawable.image_placeholder).into(coverImageView);
        pointTextView.setText(getResources().getString(R.string.earn_point_list, String.valueOf(item.getPointReward())));
    }
}

