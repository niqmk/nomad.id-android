package com.allega.nomad.activity.home.fragment.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.adapter.MenuAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.entity.Member;
import com.allega.nomad.viewgroup.FooterViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 7/20/15.
 */
public class NavigationFragment extends BaseFragment {

    @InjectView(R.id.home_button)
    protected ImageView homeButton;
    @InjectView(R.id.message_button)
    protected ImageView messageButton;
    @InjectView(R.id.currency_text_view)
    protected TextView currencyTextView;
    @InjectView(R.id.currency_button)
    protected LinearLayout currencyButton;
    @InjectView(R.id.profile_button)
    protected ImageView profileButton;
    @InjectView(R.id.favorite_button)
    protected ImageView favoriteButton;
    @InjectView(R.id.download_button)
    protected ImageView downloadButton;
    @InjectView(R.id.setting_button)
    protected ImageView settingButton;
    @InjectView(R.id.breadcrumb_text_view)
    protected TextView breadcrumbTextView;
    @InjectView(R.id.category_list_view)
    protected ListView categoryListView;
    @InjectView(R.id.breadcrumb_layout)
    protected LinearLayout breadcrumbLayout;
    @InjectView(R.id.ad_layout)
    protected LinearLayout adLayout;
    @InjectView(R.id.un_login_layout)
    protected LinearLayout unLoginLayout;
    @InjectView(R.id.login_layout)
    protected LinearLayout loginLayout;
    private NavigationFragmentController controller;

    public static Fragment newInstance() {
        NavigationFragment navigationFragment = new NavigationFragment();
        return navigationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new NavigationFragmentController(this, view);
        updateBreadcrumb(null);
        breadcrumbTextView.setMovementMethod(LinkMovementMethod.getInstance());
        Bus.getInstance().register(controller);
    }

    void updateBreadcrumb(String next) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString discover = new SpannableString(getString(R.string.discover));
        if (next != null) {
            discover.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(false);
                }

                @Override
                public void onClick(View widget) {
                    controller.removeCategory();
                }
            }, 0, discover.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(discover);
            spannableStringBuilder.append(" > ");
            SpannableString category = new SpannableString(next);
            spannableStringBuilder.append(category);
        } else {
            spannableStringBuilder.append(discover);
        }
        breadcrumbTextView.setText(spannableStringBuilder);
        breadcrumbTextView.setHighlightColor(Color.BLACK);
    }

    void setupMenu(boolean isLogin) {
        if (isLogin) {
            loginLayout.setVisibility(View.VISIBLE);
            unLoginLayout.setVisibility(View.GONE);
        } else {
            loginLayout.setVisibility(View.GONE);
            unLoginLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        Bus.getInstance().unregister(controller);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    public void setupMenu(MenuAdapter menuAdapter) {
        FooterViewGroup footerViewGroup = new FooterViewGroup(getActivity());
        footerViewGroup.setImageType(2);
        categoryListView.addFooterView(footerViewGroup);
        categoryListView.setAdapter(menuAdapter);
    }

    public void goToCategory(int currentMenu, int position, boolean isAd) {
        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            homeActivity.goToCategory(currentMenu, position, isAd);
        }
    }

    public void setupAd(boolean isAd) {
        if (isAd) {
            breadcrumbLayout.setVisibility(View.GONE);
            adLayout.setVisibility(View.VISIBLE);
        } else {
            breadcrumbLayout.setVisibility(View.VISIBLE);
            adLayout.setVisibility(View.GONE);
        }
    }

    public void updateMember(Member member) {
        currencyTextView.setText(String.valueOf(member.getTotalPoints()));
        setupMenu(member != null);
    }
}
