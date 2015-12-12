package com.allega.nomad.service.rest.app.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imnotpro on 6/27/15.
 */
public class Paging<T> {

    @Expose
    private long total;
    @SerializedName("per_page")
    @Expose
    private long perPage;
    @SerializedName("last_page")
    @Expose
    private long lastPage;
    @Expose
    private List<T> data = new ArrayList<T>();

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }

    public long getLastPage() {
        return lastPage;
    }

    public void setLastPage(long lastPage) {
        this.lastPage = lastPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
