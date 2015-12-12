package com.allega.nomad.activity.home.fragment.tvshow.fragment.recommended;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.tvshow.TvShowFragment;
import com.allega.nomad.adapter.RecommendedTvShowAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.TvShow;
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

    private final RecommendedTvShowAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private List<TvShow> tvShowList = new ArrayList<>();

    public RecommendedFragmentController(RecommendedFragment fragment, View view) {
        super(fragment, view);
        adapter = new RecommendedTvShowAdapter(context, tvShowList);
        fragment.setupRecommendedList(adapter);
        getRecommended();
    }

    private void getRecommended() {
        appRestService.getTvShowRecommended(fragment.id, new ResponseCallback<Response<TvShow>>(context) {
            @Override
            public void success(Response<TvShow> tvShowResponse) {
                if (tvShowResponse.getResults() != null) {
                    tvShowList.addAll(tvShowResponse.getResults().getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnItemClick(R.id.recommended_list_view)
    public void onRelated(int position) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(TvShowFragment.newInstance(tvShowList.get(position)));
        }
    }
}