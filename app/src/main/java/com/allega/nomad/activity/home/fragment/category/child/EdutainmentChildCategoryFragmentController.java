package com.allega.nomad.activity.home.fragment.category.child;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.adapter.EdutainmentCategoryAdapter;
import com.allega.nomad.entity.Edutainment;
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
public class EdutainmentChildCategoryFragmentController extends ChildCategoryFragmentController {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    private final EdutainmentCategoryAdapter edutainmentCategoryAdapter;
    private final List<Edutainment> edutainmentList = new ArrayList<>();
    private final Realm realm;

    public EdutainmentChildCategoryFragmentController(ChildCategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        edutainmentCategoryAdapter = new EdutainmentCategoryAdapter(context, edutainmentList);
    }

    @Override
    protected void setupList(RecyclerView recyclerView) {
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(listLayoutManager);
        recyclerView.setAdapter(edutainmentCategoryAdapter);
        int size = (int) context.getResources().getDimension(R.dimen.margin_x_small);
        recyclerView.addItemDecoration(new DividerDecoration(context, size));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.setMargins(size, size, size, size);
    }

    @Override
    public void onItemClick(RecyclerView parent, View child, int position, long id) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainmentList.get(position)));
        }
    }

    @Override
    protected void getItem() {
        if (fragment.category > 0) {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getEdutainmentCategory(fragment.category, before, null, null, null, new EdutainmentCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getEdutainmentCategory(fragment.category, before, "popular", null, null, new EdutainmentCategoryCallback(context));
                    break;
            }
        } else {
            switch (fragment.position) {
                default:
                case 0:
                    appRestService.getEdutainmentAll(before, new EdutainmentCategoryCallback(context));
                    break;
                case 1:
                    appRestService.getEdutainmentPopular(before, new EdutainmentCategoryCallback(context));
                    break;
            }
        }
    }

    private class EdutainmentCategoryCallback extends ResponseCallback<Response<Edutainment>> {

        public EdutainmentCategoryCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Edutainment> edutainmentResponse) {
            if (edutainmentResponse.getStatus() == 1) {
                realm.beginTransaction();
                List<Edutainment> temp = realm.copyToRealmOrUpdate(edutainmentResponse.getResults().getData());
                realm.commitTransaction();
                before = edutainmentResponse.getResults().getLastToken();
                for (Edutainment edutainment : temp) {
                    if (!edutainmentList.contains(edutainment))
                        edutainmentList.add(edutainment);
                }
                fragment.updateList(edutainmentCategoryAdapter);
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
            if (edutainmentList.size() == 0) {
                fragment.showNoResult();
            }
        }

    }
}