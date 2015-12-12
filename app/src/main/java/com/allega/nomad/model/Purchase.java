package com.allega.nomad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by imnotpro on 8/2/15.
 */
public class Purchase extends RealmObject {

    @SerializedName("is_purchased")
    @Expose
    private boolean isPurchased;
    @SerializedName("expired_at")
    @Expose
    private long expiredAt;

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }
}
