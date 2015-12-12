package com.allega.nomad.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 6/10/15.
 */
public class PendingDownload extends RealmObject {

    public final static String FIELD_URL = "url";

    @PrimaryKey
    private String url;
    private Date lastTry;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastTry() {
        return lastTry;
    }

    public void setLastTry(Date lastTry) {
        this.lastTry = lastTry;
    }
}
