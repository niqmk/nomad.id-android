package com.allega.nomad.activity.home.fragment.profile.child.message;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.messagedetail.MessageDetailFragment;
import com.allega.nomad.adapter.MessageAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Message;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;

import butterknife.OnItemClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MessageFragmentController extends BaseFragmentController<MessageFragment> {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final MessageAdapter adapter;
    private final Realm realm;
    private final RealmResults<Message> messageList;
    private final PreferenceProvider preferenceProvider;

    public MessageFragmentController(MessageFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        messageList = realm.where(Message.class).findAll();
        adapter = new MessageAdapter(context, messageList);
        if (messageList.size() == 0) {
            fragment.setVisibilityProgressBar(View.VISIBLE);
        }
        fragment.setupMessage(adapter);
        preferenceProvider = new PreferenceProvider(context);
        getMessage();
    }

    private void getMessage() {
        appRestService.getMessage(preferenceProvider.getMember().getId(), new ResponseCallback<Response<Message>>(context) {
            @Override
            public void success(Response<Message> messageResponse) {
                if (messageResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(messageResponse.getResults().getData());
                    realm.commitTransaction();
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

    @OnItemClick(R.id.message_list_view)
    protected void onMessage(int position) {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(MessageDetailFragment.newInstance(messageList.get(position)));
        }
    }
}