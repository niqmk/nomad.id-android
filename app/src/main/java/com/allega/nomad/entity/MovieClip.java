package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 5/31/15.
 */
public class MovieClip extends RealmObject {

    public static final String FIELD_MOVIE_ID = "movieId";

    @PrimaryKey
    @Expose
    private long id;
    @SerializedName("movie_id")
    @Expose
    private long movieId;
    @SerializedName("member_id")
    @Expose
    private long memberId;
    @Expose
    private String title;
    @Expose
    private String description;
    @SerializedName("play_count")
    @Expose
    private long playCount;
    @SerializedName("view_count")
    @Expose
    private long viewCount;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("url_type_id")
    @Expose
    private long urlTypeId;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("last_update_by_member_id")
    @Expose
    private long lastUpdateByMemberId;
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
    private Images images;
    @Expose
    private Videos videos;

    public MovieClip() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
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

    public long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getUrlTypeId() {
        return urlTypeId;
    }

    public void setUrlTypeId(long urlTypeId) {
        this.urlTypeId = urlTypeId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getLastUpdateByMemberId() {
        return lastUpdateByMemberId;
    }

    public void setLastUpdateByMemberId(long lastUpdateByMemberId) {
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

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }
}
