package com.allega.nomad.activity.edutainmentlist;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.adapter.EdutainmentListAdapter;
import com.allega.nomad.base.BaseController;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.EdutainmentPreview;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.viewgroup.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;
import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EdutainmentListController extends BaseController<EdutainmentListActivity> implements LoadMoreListView.OnLoadMoreListener {

    private final EdutainmentListAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final List<Edutainment> edutainmentList = new ArrayList<>();
    private final Realm realm;
    private final EdutainmentPreview edutainmentPreview;
    private String before = null;

    public EdutainmentListController(EdutainmentListActivity activity) {
        super(activity);
        realm = DatabaseManager.getInstance(context);
        edutainmentPreview = realm.where(EdutainmentPreview.class).equalTo(EdutainmentPreview.FIELD_ID, activity.id).findFirst();

        adapter = new EdutainmentListAdapter(context, edutainmentPreview.getEdutainments());
        activity.setupList(adapter);
        activity.setupHeader(edutainmentPreview);
        getEdutainmentCategory();
    }


    private void getEdutainmentCategory() {
//        appRestService.getEdutainmentCategory(edutainmentPreview.getId(), before, new ResponseCallback<Response<Edutainment>>(context) {
//            @Override
//            public void success(Response<Edutainment> movieResponse) {
//                if (movieResponse.getStatus() == 1) {
//                    edutainmentList.addAll(movieResponse.getResults().getData());
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(movieResponse.getResults().getData());
//                    realm.commitTransaction();
//                    adapter.notifyDataSetChanged();
//                    before = movieResponse.getResults().getLastToken();
//                    activity.setLoadMore(true);
//                } else {
//                    before = null;
//                    activity.setLoadMore(false);
//                }
//            }
//        });
    }

    @OnItemClick(R.id.edutainment_list_view)
    void onEdutainment(int position) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainmentList.get(position)));
        }
    }

    @Override
    public void onLoadMore() {
        getEdutainmentCategory();
    }
}
