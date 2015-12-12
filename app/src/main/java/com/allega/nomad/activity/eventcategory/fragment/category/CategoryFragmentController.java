package com.allega.nomad.activity.eventcategory.fragment.category;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.eventlist.EventListActivity;
import com.allega.nomad.adapter.CategoryAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.EventPreview;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;

import butterknife.OnItemClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CategoryFragmentController extends BaseFragmentController<CategoryFragment> {

    private final CategoryAdapter adapter;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final Realm realm;
    private final RealmResults<EventPreview> realmResults;

    public CategoryFragmentController(CategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        realmResults = realm.where(EventPreview.class).findAll();
        adapter = new CategoryAdapter(context, realmResults);
        fragment.setupCategory(adapter);
        getEventPreview();
    }

    private void getEventPreview() {
        appRestService.getEventPreview(new ResponseCallback<Response<EventPreview>>(context) {
            @Override
            public void success(Response<EventPreview> eventPreviewResponse) {
                if (eventPreviewResponse.getResults() != null) {
                    realm.beginTransaction();
                    realm.clear(EventPreview.class);
                    realm.copyToRealmOrUpdate(eventPreviewResponse.getResults().getData());
                    realm.commitTransaction();
                }
            }
        });
    }

    @OnItemClick(R.id.category_grid_view)
    void onCategory(int position) {
        EventListActivity.startActivity(context, realmResults.get(position).getId());
    }
}