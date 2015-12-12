package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by imnotpro on 8/9/15.
 */
public class SerialSeason extends RealmObject {

    @Expose
    private String title;
    @Expose
    private RealmList<SerialEpisode> data = new RealmList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<SerialEpisode> getData() {
        return data;
    }

    public void setData(RealmList<SerialEpisode> data) {
        this.data = data;
    }
}
