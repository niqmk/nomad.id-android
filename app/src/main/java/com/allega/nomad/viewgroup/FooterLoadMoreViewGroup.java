package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.allega.nomad.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 7/5/15.
 */
public class FooterLoadMoreViewGroup extends FrameLayout {
    @InjectView(R.id.download_progress_bar)
    protected ProgressBar progressBar;

    public FooterLoadMoreViewGroup(Context context) {
        super(context);
        init();
    }

    public FooterLoadMoreViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FooterLoadMoreViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_footer_load_more, this, true);
        ButterKnife.inject(this);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        progressBar.setVisibility(visibility);
    }
}
