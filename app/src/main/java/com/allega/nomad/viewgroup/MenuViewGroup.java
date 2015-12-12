package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.model.Menu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 7/21/15.
 */
public class MenuViewGroup extends LinearLayout {

    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.icon_image_view)
    protected ImageView iconImageView;

    public MenuViewGroup(Context context) {
        super(context);
        init();
    }

    public MenuViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_menu, this, true);
        ButterKnife.inject(this);
    }

    public void bind(Menu menu) {
        titleTextView.setText(menu.getName());
        iconImageView.setImageDrawable(menu.getDrawable());
    }

    @Override
    public void setActivated(boolean selected) {
        super.setActivated(selected);
        iconImageView.setActivated(selected);
    }
}
