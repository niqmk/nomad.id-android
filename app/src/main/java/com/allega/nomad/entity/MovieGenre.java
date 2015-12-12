package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 5/30/15.
 */
public class MovieGenre extends RealmObject {

    @Expose
    @PrimaryKey
    private long id;
    @Expose
    private String name;

    public MovieGenre() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
