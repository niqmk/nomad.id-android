package com.allega.nomad.entity;

import com.allega.nomad.model.Purchase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by imnotpro on 6/20/15.
 */
public class LogOnMember extends RealmObject {

    @SerializedName("is_liked")
    @Expose
    private boolean isLiked;
    @SerializedName("is_watched")
    @Expose
    private boolean isWatched;
    @SerializedName("is_watchlisted")
    @Expose
    private boolean isWatchListed;
    @Expose
    private MovieComment comment;
    @Expose
    private Purchase purchase;

    public boolean isLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setIsWatched(boolean isWatched) {
        this.isWatched = isWatched;
    }

    public boolean isWatchListed() {
        return isWatchListed;
    }

    public void setIsWatchListed(boolean isWatchListed) {
        this.isWatchListed = isWatchListed;
    }

    public MovieComment getComment() {
        return comment;
    }

    public void setComment(MovieComment comment) {
        this.comment = comment;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
