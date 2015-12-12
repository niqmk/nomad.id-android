package com.allega.nomad.activity.edutainmentcategory.fragment.watchlist;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.adapter.EdutainmentListAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;
import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class WatchlistFragmentController extends BaseFragmentController<WatchlistFragment> {

    private final EdutainmentListAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final List<Edutainment> edutainmentList = new ArrayList<>();
    private final Realm realm;

    public WatchlistFragmentController(WatchlistFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        adapter = new EdutainmentListAdapter(context, edutainmentList);
        fragment.setupCategory(adapter);
        getWatchList();
    }

    private void getWatchList() {
        appRestService.getEdutainmentWatchlist(new ResponseCallback<Response<Edutainment>>(context) {
            @Override
            public void success(Response<Edutainment> edutainmentResponse) {
                if (edutainmentResponse.getResults() != null) {
                    edutainmentList.clear();
                    edutainmentList.addAll(edutainmentResponse.getResults().getData());
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(edutainmentResponse.getResults().getData());
                    realm.commitTransaction();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnItemClick(R.id.movie_list_view)
    void onMovie(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainmentList.get(position)));
        }
    }

}