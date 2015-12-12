package com.allega.nomad.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by imnotpro on 6/15/15.
 */
public class Videos extends RealmObject {

    private String auto;
    @SerializedName("720")
    private String v720;

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getV720() {
        return v720;
    }

    public void setV720(String v720) {
        this.v720 = v720;
    }
}
