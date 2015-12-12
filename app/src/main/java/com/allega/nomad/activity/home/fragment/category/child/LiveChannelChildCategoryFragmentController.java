package com.allega.nomad.activity.home.fragment.category.child;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.livechannel.LiveChannelFragment;
import com.allega.nomad.adapter.LiveChannelCategoryAdapter;
import com.allega.nomad.entity.LiveChannel;
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
public class LiveChannelChildCategoryFragmentController extends ChildCategoryFragmentController {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    private final LiveChannelCategoryAdapter liveChannelCategoryAdapter;
    private final List<LiveChannel> liveChannelList = new ArrayList<>();
    private final Realm realm;

    public LiveChannelChildCategoryFragmentController(ChildCategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        liveChannelCategoryAdapter = new LiveChannelCategoryAdapter(context, liveChannelList);
    }

    @Override
    protected void setupList(RecyclerView recyclerView) {
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(listLayoutManager);
        recyclerView.setAdapter(liveChannelCategoryAdapter);
        int size = (int) context.getResources().getDimension(R.dimen.margin_x_small);
        recyclerView.addItemDecoration(new DividerDecoration(context, size));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.setMargins(size, size, size, size);
    }

    @Override
    public void onItemClick(RecyclerView parent, View child, int position, long id) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(LiveChannelFragment.newInstance(liveChannelList.get(position)));
        }
    }

    @Override
    protected void getItem() {
        if (fragment.category > 0) {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getLiveChannelCategory(fragment.category, before, null, null, null, new LiveChannelCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getLiveChannelCategory(fragment.category, before, "popular", null, null, new LiveChannelCategoryCallback(context));
                    break;
            }
        } else {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getLiveChannelAll(before, new LiveChannelCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getLiveChannelPopular(before, new LiveChannelCategoryCallback(context));
                    break;
            }
        }
    }

    private class LiveChannelCategoryCallback extends ResponseCallback<Response<LiveChannel>> {

        public LiveChannelCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<LiveChannel> liveChannelResponse) {
            if (liveChannelResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<LiveChannel> temp = realm.copyToRealmOrUpdate(liveChannelResponse.getResults().getData());
                realm.commitTransaction();
                before = liveChannelResponse.getResults().getLastToken();
                for (LiveChannel liveChannel : temp) {
                    if (!liveChannelList.contains(liveChannel))
                        liveChannelList.add(liveChannel);
                }
                fragment.updateList(liveChannelCategoryAdapter);
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
            if (liveChannelList.size() == 0) {
                fragment.showNoResult();
            }
        }
    }
}