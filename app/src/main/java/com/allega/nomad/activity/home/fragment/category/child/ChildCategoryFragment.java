package com.allega.nomad.activity.home.fragment.category.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.base.AbstractRecycleAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.others.EndlessRecyclerOnScrollListener;
import com.allega.nomad.service.analytic.GAConstant;
import com.allega.nomad.viewgroup.FooterLoadMoreViewGroup;

import org.lucasr.twowayview.ItemClickSupport;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ChildCategoryFragment extends BaseFragment {

    private static final String ARGUMENT_MENU = "argument-menu";
    private static final String ARGUMENT_POSITION = "argument-position";
    private static final String ARGUMENT_AD = "argument-ad";
    private static final String ARGUMENT_CATEGORY = "argument-category";
    @InjectView(R.id.item_list_view)
    protected RecyclerView itemListView;
    @InjectView(R.id.no_result_text_view)
    protected TextView noResultTextView;
    @InjectView(R.id.load_more_view_group)
    protected FooterLoadMoreViewGroup loadMoreViewGroup;
    @InjectView(R.id.progress_bar)
    protected ProgressBar progressBar;
    int menu;
    int category;
    int position;
    private ChildCategoryFragmentController controller;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private boolean isAd;
    private String screenName;

    public static ChildCategoryFragment newInstance(int menu, int category, boolean isAd, int position) {
        ChildCategoryFragment childCategoryFragment = new ChildCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_MENU, menu);
        bundle.putInt(ARGUMENT_CATEGORY, category);
        bundle.putBoolean(ARGUMENT_AD, isAd);
        bundle.putInt(ARGUMENT_POSITION, position);
        childCategoryFragment.setArguments(bundle);
        return childCategoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menu = getArguments().getInt(ARGUMENT_MENU);
        position = getArguments().getInt(ARGUMENT_POSITION);
        category = getArguments().getInt(ARGUMENT_CATEGORY);
        isAd = getArguments().getBoolean(ARGUMENT_AD, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_category, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAd) {
            controller = new AdChildCategoryFragmentController(this, view);
            screenName = GAConstant.CATEGORY_AD_SCREEN;
        } else {
            switch (menu) {
                case 2:
                    controller = new EventChildCategoryFragmentController(this, view);
                    screenName = GAConstant.CATEGORY_EVENT_SCREEN;
                    break;
                case 3:
                    controller = new EdutainmentChildCategoryFragmentController(this, view);
                    screenName = GAConstant.CATEGORY_EDUTAINMENT_SCREEN;
                    break;
                case 4:
                    controller = new SerialChildCategoryFragmentController(this, view);
                    screenName = GAConstant.CATEGORY_SERIAL_SCREEN;
                    break;
                case 5:
                    controller = new TvShowChildCategoryFragmentController(this, view);
                    screenName = GAConstant.CATEGORY_TV_SHOW_SCREEN;
                    break;
                case 6:
                    controller = new LiveChannelChildCategoryFragmentController(this, view);
                    screenName = GAConstant.CATEGORY_LIVE_CHANNEL_SCREEN;
                    break;
                case 0:
                default:
                    controller = new MovieChildCategoryFragmentController(this, view);
                    screenName = GAConstant.CATEGORY_MOVIE_SCREEN;
                    break;
            }
        }
        controller.setupList(itemListView);
        controller.getItem();
        itemListView.setHasFixedSize(true);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(itemListView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                loadMoreViewGroup.setVisibility(View.VISIBLE);
                controller.getItem();
            }
        };
        itemListView.addOnScrollListener(endlessRecyclerOnScrollListener);
        ItemClickSupport itemClick = ItemClickSupport.addTo(itemListView);
        itemClick.setOnItemClickListener(controller);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (screenName != null)
            sendGAScreenTracker(screenName);
    }

    void prepareLoadMore() {
        loadMoreViewGroup.setVisibility(View.GONE);
        endlessRecyclerOnScrollListener.setLoading(false);
    }

    void disableLoad() {
        loadMoreViewGroup.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void showNoResult() {
        itemListView.setVisibility(View.GONE);
        noResultTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void updateList(AbstractRecycleAdapter abstractRecycleAdapter) {
        if (abstractRecycleAdapter.getItemCount() > 0) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        abstractRecycleAdapter.notifyDataSetChanged();
    }
}
