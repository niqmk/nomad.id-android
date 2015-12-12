package com.allega.nomad.activity.home.fragment.edutainment.fragment.recommended;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.adapter.RecommendedEdutainmentAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Edutainment;
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

    private final RecommendedEdutainmentAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private List<Edutainment> edutainmentList = new ArrayList<>();

    public RecommendedFragmentController(RecommendedFragment fragment, View view) {
        super(fragment, view);
        adapter = new RecommendedEdutainmentAdapter(context, edutainmentList);
        fragment.setupRecommendedList(adapter);
        getRecommended();
    }

    private void getRecommended() {
        appRestService.getEdutainmentRecommended(fragment.id, new ResponseCallback<Response<Edutainment>>(context) {
            @Override
            public void success(Response<Edutainment> edutainmentResponse) {
                if (edutainmentResponse.getResults() != null) {
                    edutainmentList.addAll(edutainmentResponse.getResults().getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnItemClick(R.id.recommended_list_view)
    public void onRelated(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainmentList.get(position)));
        }
    }
}