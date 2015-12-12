package com.allega.nomad.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 6/2/15.
 */
public class EdutainmentPreview extends RealmObject {

    public static final String FIELD_ID = "id";

    @PrimaryKey
    private long id;
    private String name;
    @SerializedName("previews")
    private RealmList<Edutainment> edutainments = new RealmList<>();

    public EdutainmentPreview() {
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

    public RealmList<Edutainment> getEdutainments() {
        return edutainments;
    }

    public void setEdutainments(RealmList<Edutainment> edutainments) {
        this.edutainments = edutainments;
    }
}
