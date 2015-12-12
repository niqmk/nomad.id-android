package com.allega.nomad.activity.home.fragment.profile.child.profile;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.entity.Member;

import org.apache.commons.lang3.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ProfileFragment extends BaseFragment {

    @InjectView(R.id.change_password_button)
    protected LinearLayout changePasswordButton;
    @InjectView(R.id.facebook_status)
    protected TextView facebookStatus;
    @InjectView(R.id.facebook_button)
    protected LinearLayout facebookButton;
    @InjectView(R.id.avatar_image_view)
    protected ImageView avatarImageView;
    @InjectView(R.id.username_text_view)
    protected TextView usernameTextView;
    @InjectView(R.id.email_text_view)
    protected TextView emailTextView;
    @InjectView(R.id.change_email_button)
    protected LinearLayout changeEmailButton;
    @InjectView(R.id.change_username_button)
    protected LinearLayout changeUsernameButton;
    @InjectView(R.id.change_email_text_view)
    protected TextView changeEmailTextView;
    @InjectView(R.id.change_username_text_view)
    protected TextView changeUsernameTextView;
    @InjectView(R.id.logout_button)
    protected LinearLayout logoutButton;
    @InjectView(R.id.change_password_text_view)
    protected TextView changePasswordTextView;
    private ProfileFragmentController controller;

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new ProfileFragmentController(this, view);
        Bus.getInstance().register(controller);
    }

    @Override
    public void onDestroyView() {
        Bus.getInstance().unregister(controller);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    void bind(Member member) {
        usernameTextView.setText(member.getUsername());
        emailTextView.setText(member.getEmail());
        if (StringUtils.isEmpty(member.getFbId())) {
            facebookStatus.setCompoundDrawables(null, null, null, null);
            facebookStatus.setText(R.string.linked);
        }
        if (member.isPasswordNull()) {
            changeEmailTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            changeUsernameTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            changePasswordTextView.setText(R.string.set_password);
        } else {
            changeEmailTextView.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
            changeUsernameTextView.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
            changePasswordTextView.setText(R.string.change_password);
        }
        changeEmailTextView.setText(R.string.change_email);
        changeUsernameTextView.setText(R.string.username);
    }
}
