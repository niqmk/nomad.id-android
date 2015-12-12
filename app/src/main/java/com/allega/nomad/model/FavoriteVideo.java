package com.allega.nomad.model;

import com.allega.nomad.entity.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by imnotpro on 6/27/15.
 */
public class FavoriteVideo {

    @Expose
    private long id;
    @SerializedName("video_type_id")
    @Expose
    private int videoTypeId;
    @Expose
    private Video detail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(int videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public Video getDetail() {
        return detail;
    }

    public void setDetail(Video detail) {
        this.detail = detail;
    }
}
