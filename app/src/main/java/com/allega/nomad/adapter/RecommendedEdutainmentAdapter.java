package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.viewgroup.SuggestionViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class RecommendedEdutainmentAdapter extends AbstractAdapter<Edutainment> {

    public RecommendedEdutainmentAdapter(Context context, List<Edutainment> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        SuggestionViewGroup suggestionViewGroup = (SuggestionViewGroup) convertView;
        suggestionViewGroup.bind(getItem(position));
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new SuggestionViewGroup(context);
    }
}
