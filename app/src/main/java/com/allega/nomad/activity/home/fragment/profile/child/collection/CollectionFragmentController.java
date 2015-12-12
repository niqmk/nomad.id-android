package com.allega.nomad.activity.home.fragment.profile.child.collection;

import android.view.View;

import com.allega.nomad.adapter.CollectionAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.UpdateCollectionEvent;
import com.allega.nomad.entity.Collection;
import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CollectionFragmentController extends BaseFragmentController<CollectionFragment> {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final CollectionAdapter adapter;
    private final PreferenceProvider preferenceProvider;
    private final Realm realm;
    private final List<Collection> collectionList = new ArrayList<>();

    public CollectionFragmentController(CollectionFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
//        collectionList = realm.where(Collection.class).findAll();
        adapter = new CollectionAdapter(context, collectionList);
        if (collectionList.size() == 0) {
            fragment.setVisibilityProgressBar(View.VISIBLE);
        }
        fragment.setupCollection(adapter);
        preferenceProvider = new PreferenceProvider(context);
        getCollection();
    }

    private void getCollection() {
        Member member = preferenceProvider.getMember();
        if (member != null)
            appRestService.getMemberCollection(member.getId(), new ResponseCallback<Response<Collection>>(context) {
                @Override
                public void success(Response<Collection> collectionResponse) {
                    if (collectionResponse.getStatus() == 1) {
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(collectionResponse.getResults().getData());
                        realm.commitTransaction();
                        collectionList.addAll(collectionResponse.getResults().getData());
                    }
                    fragment.updateList(adapter);
                    fragment.setVisibilityProgressBar(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    fragment.setVisibilityProgressBar(View.GONE);
                }
            });
    }


    public void onEventMainThread(UpdateCollectionEvent updateCollectionEvent) {
        fragment.updateList(adapter);
    }
}