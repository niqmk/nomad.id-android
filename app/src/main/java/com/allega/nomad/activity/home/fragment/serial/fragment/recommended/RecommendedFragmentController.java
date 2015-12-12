package com.allega.nomad.activity.home.fragment.serial.fragment.recommended;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.serial.SerialFragment;
import com.allega.nomad.adapter.RecommendedSerialAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Serial;
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

    private final RecommendedSerialAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private List<Serial> serialList = new ArrayList<>();

    public RecommendedFragmentController(RecommendedFragment fragment, View view) {
        super(fragment, view);
        adapter = new RecommendedSerialAdapter(context, serialList);
        fragment.setupRecommendedList(adapter);
        getRecommended();
    }

    private void getRecommended() {
        appRestService.getSerialRecommended(fragment.id, new ResponseCallback<Response<Serial>>(context) {
            @Override
            public void success(Response<Serial> serialResponse) {
                if (serialResponse.getResults() != null) {
                    serialList.addAll(serialResponse.getResults().getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnItemClick(R.id.recommended_list_view)
    public void onRelated(int position) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(SerialFragment.newInstance(serialList.get(position)));
        }
    }
}