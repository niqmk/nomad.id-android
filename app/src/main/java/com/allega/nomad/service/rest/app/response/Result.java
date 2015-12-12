package com.allega.nomad.service.rest.app.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class Result<T> {
    @Expose
    private String latest;
    @Expose
    private String oldest;
    @Expose
    @SerializedName("first_token")
    private String firstToken;
    @Expose
    @SerializedName("last_token")
    private String lastToken;
    @Expose
    private List<T> data = new ArrayList<T>();

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getOldest() {
        return oldest;
    }

    public void setOldest(String oldest) {
        this.oldest = oldest;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getFirstToken() {
        return firstToken;
    }

    public void setFirstToken(String firstToken) {
        this.firstToken = firstToken;
    }

    public String getLastToken() {
        return lastToken;
    }

    public void setLastToken(String lastToken) {
        this.lastToken = lastToken;
    }
}