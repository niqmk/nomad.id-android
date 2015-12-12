package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 6/27/15.
 */
public class WatchListVideo extends RealmObject {

    @PrimaryKey
    @Expose
    private long id;
    @SerializedName("video_id")
    @Expose
    private int videoId;
    @SerializedName("video_type_id")
    @Expose
    private int videoTypeId;
    @SerializedName("member_id")
    @Expose
    private long memberId;
    @Expose
    private long price;
    @SerializedName("expiry_days")
    @Expose
    private long expiryDays;
    @SerializedName("expired_at")
    @Expose
    private String expiredAt;
    @SerializedName("created_at")
    @Expose
    private long createdAt;
    @SerializedName("updated_at")
    @Expose
    private long updatedAt;
    @Expose
    private Video detail;

    public WatchListVideo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(int videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(long expiryDays) {
        this.expiryDays = expiryDays;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Video getDetail() {
        return detail;
    }

    public void setDetail(Video detail) {
        this.detail = detail;
    }
}
