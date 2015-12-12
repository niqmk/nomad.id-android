package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.RealmAdapter;
import com.allega.nomad.entity.Message;
import com.allega.nomad.viewgroup.MessageViewGroup;

import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MessageAdapter extends RealmAdapter<Message> {

    public MessageAdapter(Context context, RealmResults<Message> items) {
        super(context, items, false);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        MessageViewGroup messageViewGroup = (MessageViewGroup) convertView;
        messageViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new MessageViewGroup(context);
    }
}
