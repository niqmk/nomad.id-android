package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 6/20/15.
 */
public class SerialEpisodeComment extends RealmObject {

    public static final String FIELD_TV_SHOW_EPISODE_ID = "serialEpisodeId";
    public static final String FIELD_DATE = "createdAt";

    @PrimaryKey
    private long id;
    @SerializedName("serial_episode_id")
    @Expose
    private long serialEpisodeId;
    @SerializedName("member_id")
    @Expose
    private long memberId;
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private long createdAt;
    @SerializedName("updated_at")
    @Expose
    private long updatedAt;
    @SerializedName("status_id")
    @Expose
    private String statusId;
    @Expose
    private Member member;

    public SerialEpisodeComment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSerialEpisodeId() {
        return serialEpisodeId;
    }

    public void setSerialEpisodeId(long serialEpisodeId) {
        this.serialEpisodeId = serialEpisodeId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
