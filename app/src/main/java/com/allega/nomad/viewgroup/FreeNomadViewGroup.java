package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by imnotpro on 6/7/15.
 */
public class FreeNomadViewGroup extends LinearLayout {

    @InjectView(R.id.avatar_image_view)
    protected CircleImageView avatarImageView;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.value_text_view)
    protected TextView valueTextView;

    public FreeNomadViewGroup(Context context) {
        super(context);
        init();
    }

    public FreeNomadViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FreeNomadViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_free_nomad, this, true);
        ButterKnife.inject(this);
    }
}
