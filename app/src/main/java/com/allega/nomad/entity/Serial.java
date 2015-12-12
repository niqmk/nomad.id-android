package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 8/8/15.
 */
public class Serial extends RealmObject {

    public static final String FIELD_ID = "id";
    public static final String FIELD_VIDEO_ID = "videoId";

    @PrimaryKey
    @Expose
    private long id;
    @SerializedName("member_id")
    @Expose
    private long memberId;
    @Expose
    private String title;
    @Expose
    private String description;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("last_update_by_member_id")
    @Expose
    private String lastUpdateByMemberId;
    @SerializedName("created_at")
    @Expose
    private long createdAt;
    @SerializedName("updated_at")
    @Expose
    private long updatedAt;
    @SerializedName("status_id")
    @Expose
    private String statusId;
    @SerializedName("first_episode_id")
    @Expose
    private long firstEpisodeId;
    @Expose
    private Images images;

    public Serial() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLastUpdateByMemberId() {
        return lastUpdateByMemberId;
    }

    public void setLastUpdateByMemberId(String lastUpdateByMemberId) {
        this.lastUpdateByMemberId = lastUpdateByMemberId;
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public long getFirstEpisodeId() {
        return firstEpisodeId;
    }

    public void setFirstEpisodeId(long firstEpisodeId) {
        this.firstEpisodeId = firstEpisodeId;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}
