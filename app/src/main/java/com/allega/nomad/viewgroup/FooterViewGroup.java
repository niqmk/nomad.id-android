package com.allega.nomad.viewgroup;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class FooterViewGroup extends LinearLayout {

    @InjectView(R.id.bottom_image_view)
    protected ImageView bottomImageView;
    @InjectView(R.id.version_text_view)
    protected TextView versionTextView;


    public FooterViewGroup(Context context) {
        super(context);
        init();
    }

    public FooterViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FooterViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_group_footer, this, true);
        ButterKnife.inject(this);
    }

    public void setImageType(int position) {
        switch (position) {
            case 2: {
                PackageInfo pInfo = null;
                try {
                    pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("v");
                    stringBuilder.append(pInfo.versionName);
                    versionTextView.setText(stringBuilder.toString());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                bottomImageView.setImageResource(R.drawable.image_footer_drawer);
                break;
            }
            default:
                bottomImageView.setImageResource(R.drawable.bottom_image);
                break;
        }
    }
}

