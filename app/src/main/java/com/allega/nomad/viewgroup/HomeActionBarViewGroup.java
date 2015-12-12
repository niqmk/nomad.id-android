package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.allega.nomad.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by imnotpro on 6/2/15.
 */
public class HomeActionBarViewGroup extends LinearLayout {

    private HomeActionBarListener listener;

    public HomeActionBarViewGroup(Context context) {
        super(context);
        init();
    }

    public HomeActionBarViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeActionBarViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_actionbar_home, this, true);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.drawer_button)
    protected void onDrawer() {
        if (listener != null)
            listener.onDrawer();
    }

    @OnClick(R.id.search_button)
    protected void onSearch() {
        if (listener != null)
            listener.onSearch();
    }

    public void setListener(HomeActionBarListener listener) {
        this.listener = listener;
    }

    public interface HomeActionBarListener {
        void onDrawer();

        void onSearch();
    }
}
