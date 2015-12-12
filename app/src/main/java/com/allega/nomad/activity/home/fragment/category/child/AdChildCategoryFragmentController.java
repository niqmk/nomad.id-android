package com.allega.nomad.activity.home.fragment.category.child;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.ad.AdFragment;
import com.allega.nomad.adapter.AdCategoryAdapter;
import com.allega.nomad.entity.Ad;
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
public class AdChildCategoryFragmentController extends ChildCategoryFragmentController {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    private final AdCategoryAdapter adCategoryAdapter;
    private final List<Ad> adList = new ArrayList<>();
    private final Realm realm;

    public AdChildCategoryFragmentController(ChildCategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        adCategoryAdapter = new AdCategoryAdapter(context, adList);
    }

    @Override
    protected void setupList(RecyclerView recyclerView) {
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(listLayoutManager);
        recyclerView.setAdapter(adCategoryAdapter);
        int size = (int) context.getResources().getDimension(R.dimen.margin_x_small);
        recyclerView.addItemDecoration(new DividerDecoration(context, size));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.setMargins(size, size, size, size);
    }

    @Override
    public void onItemClick(RecyclerView parent, View child, int position, long id) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(AdFragment.newInstance(adList.get(position)));
        }
    }

    @Override
    protected void getItem() {
        if (fragment.category > 0) {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getAdCategory(fragment.category, before, null, null, null, new AdCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getAdCategory(fragment.category, before, "popular", null, null, new AdCategoryCallback(context));
                    break;
            }
        } else {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getAdAll(before, new AdCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getAdPopular(before, new AdCategoryCallback(context));
                    break;
            }
        }
    }

    private class AdCategoryCallback extends ResponseCallback<Response<Ad>> {

        public AdCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Ad> adResponse) {
            if (adResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<Ad> temp = realm.copyToRealmOrUpdate(adResponse.getResults().getData());
                realm.commitTransaction();
                before = adResponse.getResults().getLastToken();
                for (Ad ad : temp) {
                    if (!adList.contains(ad))
                        adList.add(ad);
                }
                fragment.updateList(adCategoryAdapter);
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
            if (adList.size() == 0) {
                fragment.showNoResult();
            }
        }
    }

}