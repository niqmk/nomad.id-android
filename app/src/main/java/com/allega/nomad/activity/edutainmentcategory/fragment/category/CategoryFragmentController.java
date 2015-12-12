package com.allega.nomad.activity.edutainmentcategory.fragment.category;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.edutainmentlist.EdutainmentListActivity;
import com.allega.nomad.adapter.CategoryAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.EdutainmentPreview;
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
    private final RealmResults<EdutainmentPreview> realmResults;

    public CategoryFragmentController(CategoryFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        realmResults = realm.where(EdutainmentPreview.class).findAll();
        adapter = new CategoryAdapter(context, realmResults);
        fragment.setupCategory(adapter);
        getEdutainmentCategory();
    }

    private void getEdutainmentCategory() {
        appRestService.getEdutainmentPreview(new ResponseCallback<Response<EdutainmentPreview>>(context) {
            @Override
            public void success(Response<EdutainmentPreview> edutainmentPreviewResponse) {
                if (edutainmentPreviewResponse.getResults() != null) {
                    realm.beginTransaction();
                    realm.clear(EdutainmentPreview.class);
                    realm.copyToRealmOrUpdate(edutainmentPreviewResponse.getResults().getData());
                    realm.commitTransaction();
                }
            }
        });
    }

    @OnItemClick(R.id.category_grid_view)
    void onCategory(int position) {
        EdutainmentListActivity.startActivity(context, realmResults.get(position).getId());
    }
}