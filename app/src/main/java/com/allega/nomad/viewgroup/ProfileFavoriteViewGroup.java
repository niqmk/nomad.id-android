package com.allega.nomad.viewgroup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.model.FavoriteVideo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ProfileFavoriteViewGroup extends CardView {

    @InjectView(R.id.name_text_view)
    protected TextView nameTextView;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;

    public ProfileFavoriteViewGroup(Context context) {
        super(context);
        init();
    }

    public ProfileFavoriteViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileFavoriteViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_profile_favorite, this, true);
        ButterKnife.inject(this);
    }

    public void bind(FavoriteVideo favoriteVideo) {
        nameTextView.setText(favoriteVideo.getDetail().getTitle());
        switch (favoriteVideo.getVideoTypeId()) {
            case 1:
                descriptionTextView.setText(R.string.in_movies);
                break;
            case 2:
                descriptionTextView.setText(R.string.in_edutainments);
                break;
        }
    }
}
