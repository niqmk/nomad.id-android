package com.allega.nomad.activity.home.fragment.ad.fragment.recommended;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.ad.AdFragment;
import com.allega.nomad.adapter.RecommendedAdAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

/**
 * Created by imnotpro on 5/30/15.
 */
public class RecommendedFragmentController extends BaseFragmentController<RecommendedFragment> {

    private final RecommendedAdAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private List<Ad> adList = new ArrayList<>();

    public RecommendedFragmentController(RecommendedFragment fragment, View view) {
        super(fragment, view);
        adapter = new RecommendedAdAdapter(context, adList);
        fragment.setupRecommendedList(adapter);
        getRecommended();
    }

    private void getRecommended() {
        appRestService.getAdRelateds(fragment.id, new ResponseCallback<Response<Ad>>(context) {
            @Override
            public void success(Response<Ad> edutainmentResponse) {
                if (edutainmentResponse.getResults() != null) {
                    adList.addAll(edutainmentResponse.getResults().getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnItemClick(R.id.recommended_list_view)
    public void onRelated(int position) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(AdFragment.newInstance(adList.get(position)));
        }
    }
}