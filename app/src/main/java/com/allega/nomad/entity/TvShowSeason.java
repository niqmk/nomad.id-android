package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by imnotpro on 8/9/15.
 */
public class TvShowSeason extends RealmObject {

    @Expose
    private String title;
    @Expose
    private RealmList<TvShowEpisode> data = new RealmList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<TvShowEpisode> getData() {
        return data;
    }

    public void setData(RealmList<TvShowEpisode> data) {
        this.data = data;
    }
}
