package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.AbstractAdapter;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.viewgroup.SearchViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SearchAdapter<T> extends AbstractAdapter<T> {

    public SearchAdapter(Context context, List<T> items) {
        super(context, items);
    }

    @Override
    protected void onBind(int position, View convertView, ViewGroup parent) {
        SearchViewGroup searchViewGroup = (SearchViewGroup) convertView;
        Object item = getItem(position);
        if (item instanceof Movie) {
            searchViewGroup.bind((Movie) item);
        } else if (item instanceof Event) {
            searchViewGroup.bind((Event) item);
        } else if (item instanceof Edutainment) {
            searchViewGroup.bind((Edutainment) item);
        } else if (item instanceof TvShowEpisode) {
            searchViewGroup.bind((TvShowEpisode) item);
        } else if (item instanceof LiveChannel) {
            searchViewGroup.bind((LiveChannel) item);
        } else if (item instanceof SerialEpisode) {
            searchViewGroup.bind((SerialEpisode) item);
        } else if (item instanceof Ad) {
            searchViewGroup.bind((Ad) item);
        } else if (item instanceof String) {
            searchViewGroup.bind((String) item);
        }
    }

    @Override
    protected View onCreate(int position, View convertView, ViewGroup parent) {
        return new SearchViewGroup(context);
    }
}
