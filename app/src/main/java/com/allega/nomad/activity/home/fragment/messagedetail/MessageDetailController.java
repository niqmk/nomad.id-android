package com.allega.nomad.activity.home.fragment.messagedetail;

import android.view.View;

import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Message;
import com.allega.nomad.storage.DatabaseManager;

import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MessageDetailController extends BaseFragmentController<MessageDetailFragment> {

    private final Realm realm = DatabaseManager.getInstance(context);
    private final Message message;

    public MessageDetailController(MessageDetailFragment fragment, View view) {
        super(fragment, view);
        message = realm.where(Message.class).equalTo(Message.FIELD_ID, fragment.id).findFirst();
        fragment.bind(message);
    }
}
