package com.allega.nomad.activity.home.fragment.category.child;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.tvshow.TvShowFragment;
import com.allega.nomad.adapter.TvShowCategoryAdapter;
import com.allega.nomad.adapter.TvShowEpisodeCategoryAdapter;
import com.allega.nomad.entity.TvShow;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.viewgroup.decorator.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class TvShowChildCategoryFragmentController extends ChildCategoryFragmentController {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    private final TvShowCategoryAdapter tvShowCategoryAdapter;
    private final TvShowEpisodeCategoryAdapter tvShowEpisodeCategoryAdapter;
    private final List<TvShow> tvShowList = new ArrayList<>();
    private final List<TvShowEpisode> tvShowEpisodeList = new ArrayList<>();
    private final Realm realm;
    private boolean isEpisode = false;

    public TvShowChildCategoryFragmentController(ChildCategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        tvShowCategoryAdapter = new TvShowCategoryAdapter(context, tvShowList);
        tvShowEpisodeCategoryAdapter = new TvShowEpisodeCategoryAdapter(context, tvShowEpisodeList);
    }

    @Override
    protected void setupList(RecyclerView recyclerView) {
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(listLayoutManager);
        if (fragment.category == 0 && fragment.position == 1) {
            recyclerView.setAdapter(tvShowEpisodeCategoryAdapter);
        } else {
            recyclerView.setAdapter(tvShowCategoryAdapter);
        }
        int size = (int) context.getResources().getDimension(R.dimen.margin_x_small);
        recyclerView.addItemDecoration(new DividerDecoration(context, size));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.setMargins(size, size, size, size);
    }

    @Override
    public void onItemClick(RecyclerView parent, View child, int position, long id) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            if (!isEpisode)
                homeActivity.goToFragment(TvShowFragment.newInstance(tvShowCategoryAdapter.getItem(position)));
            else
                homeActivity.goToFragment(TvShowFragment.newInstance(tvShowEpisodeCategoryAdapter.getItem(position)));
        }
    }

    @Override
    protected void getItem() {
        if (fragment.category > 0) {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getTvShowCategory(fragment.category, before, null, null, null, new TvShowCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getTvShowCategory(fragment.category, before, "popular", null, null, new TvShowCategoryCallback(context));
                    break;
            }
        } else {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getTvShowAll(before, new TvShowCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getTvShowEpisodePopular(before, new TvShowEpisodeCategoryCallback(context));
                    isEpisode = true;
                    break;
            }
        }
    }

    private class TvShowCategoryCallback extends ResponseCallback<Response<TvShow>> {

        public TvShowCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<TvShow> tvShowResponse) {
            if (tvShowResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<TvShow> temp = realm.copyToRealmOrUpdate(tvShowResponse.getResults().getData());
                realm.commitTransaction();
                before = tvShowResponse.getResults().getLastToken();
                for (TvShow tvShow : temp) {
                    if (!tvShowList.contains(tvShow))
                        tvShowList.add(tvShow);
                }
                fragment.updateList(tvShowCategoryAdapter);
                String pos = before.split("-")[0];
                if (Integer.valueOf(pos) > 1) {
                    fragment.prepareLoadMore();
                } else {
                    fragment.disableLoad();
                }
            } else {
                before = null;
                fragment.disableLoad();
            }
            if (tvShowList.size() == 0) {
                fragment.showNoResult();
            }
        }
    }

    private class TvShowEpisodeCategoryCallback extends ResponseCallback<Response<TvShowEpisode>> {

        public TvShowEpisodeCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<TvShowEpisode> tvShowResponse) {
            if (tvShowResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<TvShowEpisode> temp = realm.copyToRealmOrUpdate(tvShowResponse.getResults().getData());
                realm.commitTransaction();
                before = tvShowResponse.getResults().getLastToken();
                for (TvShowEpisode tvShowEpisode : temp) {
                    if (!tvShowEpisodeList.contains(tvShowEpisode))
                        tvShowEpisodeList.add(tvShowEpisode);
                }
                fragment.updateList(tvShowEpisodeCategoryAdapter);
                String pos = before.split("-")[0];
                if (Integer.valueOf(pos) > 1) {
                    fragment.prepareLoadMore();
                } else {
                    fragment.disableLoad();
                }
            } else {
                before = null;
                fragment.disableLoad();
            }
            if (tvShowEpisodeList.size() == 0) {
                fragment.showNoResult();
            }
        }
    }
}