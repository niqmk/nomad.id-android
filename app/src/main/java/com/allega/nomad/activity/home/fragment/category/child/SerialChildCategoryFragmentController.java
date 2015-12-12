package com.allega.nomad.activity.home.fragment.category.child;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.serial.SerialFragment;
import com.allega.nomad.adapter.SerialCategoryAdapter;
import com.allega.nomad.adapter.SerialEpisodeCategoryAdapter;
import com.allega.nomad.entity.Serial;
import com.allega.nomad.entity.SerialEpisode;
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
public class SerialChildCategoryFragmentController extends ChildCategoryFragmentController {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    private final SerialCategoryAdapter serialCategoryAdapter;
    private final SerialEpisodeCategoryAdapter serialEpisodeCategoryAdapter;
    private final List<Serial> serialList = new ArrayList<>();
    private final List<SerialEpisode> serialEpisodeList = new ArrayList<>();
    private final Realm realm;
    private boolean isEpisode = false;

    public SerialChildCategoryFragmentController(ChildCategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        serialCategoryAdapter = new SerialCategoryAdapter(context, serialList);
        serialEpisodeCategoryAdapter = new SerialEpisodeCategoryAdapter(context, serialEpisodeList);
    }

    @Override
    protected void setupList(RecyclerView recyclerView) {
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(listLayoutManager);
        if (fragment.category == 0 && fragment.position == 1) {
            recyclerView.setAdapter(serialEpisodeCategoryAdapter);
        } else {
            recyclerView.setAdapter(serialCategoryAdapter);
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
                homeActivity.goToFragment(SerialFragment.newInstance(serialCategoryAdapter.getItem(position)));
            else
                homeActivity.goToFragment(SerialFragment.newInstance(serialEpisodeCategoryAdapter.getItem(position)));
        }
    }

    @Override
    protected void getItem() {
        if (fragment.category > 0) {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getSerialCategory(fragment.category, before, null, null, null, new SeriesCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getSerialCategory(fragment.category, before, "popular", null, null, new SeriesCategoryCallback(context));
                    break;
            }
        } else {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getSerialAll(before, new SeriesCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getSerialEpisodePopular(before, new SeriesEpisodeCategoryCallback(context));
                    isEpisode = true;
                    break;
            }
        }
    }

    private class SeriesCategoryCallback extends ResponseCallback<Response<Serial>> {

        public SeriesCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Serial> serialResponse) {
            if (serialResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<Serial> temp = realm.copyToRealmOrUpdate(serialResponse.getResults().getData());
                realm.commitTransaction();
                before = serialResponse.getResults().getLastToken();
                for (Serial serial : temp) {
                    if (!serialList.contains(serial))
                        serialList.add(serial);
                }
                fragment.updateList(serialCategoryAdapter);
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
            if (serialList.size() == 0) {
                fragment.showNoResult();
            }
        }
    }

    private class SeriesEpisodeCategoryCallback extends ResponseCallback<Response<SerialEpisode>> {

        public SeriesEpisodeCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<SerialEpisode> serialEpisodeResponse) {
            if (serialEpisodeResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<SerialEpisode> temp = realm.copyToRealmOrUpdate(serialEpisodeResponse.getResults().getData());
                realm.commitTransaction();
                before = serialEpisodeResponse.getResults().getLastToken();
                for (SerialEpisode serialEpisode : temp) {
                    if (!serialEpisodeList.contains(serialEpisode))
                        serialEpisodeList.add(serialEpisode);
                }
                fragment.updateList(serialEpisodeCategoryAdapter);
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
            if (serialEpisodeList.size() == 0) {
                fragment.showNoResult();
            }
        }
    }
}