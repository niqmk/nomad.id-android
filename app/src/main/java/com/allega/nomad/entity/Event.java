package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 5/30/15.
 */
public class Event extends RealmObject {

    public static final String FIELD_ID = "id";
    public static final String FIELD_VIDEO_ID = "videoId";

    @PrimaryKey
    @Expose
    private long id;
    @SerializedName("video_id")
    @Expose
    private long videoId;
    @SerializedName("member_id")
    @Expose
    private long memberId;
    @SerializedName("url_type_id")
    @Expose
    private long urlTypeId;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
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
    @SerializedName("start_at")
    @Expose
    private long startAt;
    @SerializedName("end_at")
    @Expose
    private long endAt;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
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
    private RealmList<EventCategory> eventCategories = new RealmList<EventCategory>();
    @Expose
    private RealmList<Price> prices = new RealmList<Price>();
    @Expose
    private Images images;
    @SerializedName("total_likes")
    @Expose
    private long totalLikes;
    @Expose
    private Videos videos;
    @SerializedName("log_on_member")
    @Expose
    private LogOnMember logOnMember;

    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
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

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public long getEndAt() {
        return endAt;
    }

    public void setEndAt(long endAt) {
        this.endAt = endAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public RealmList<EventCategory> getEventCategories() {
        return eventCategories;
    }

    public void setEventCategories(RealmList<EventCategory> eventCategories) {
        this.eventCategories = eventCategories;
    }

    public RealmList<Price> getPrices() {
        return prices;
    }

    public void setPrices(RealmList<Price> prices) {
        this.prices = prices;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    public LogOnMember getLogOnMember() {
        return logOnMember;
    }

    public void setLogOnMember(LogOnMember logOnMember) {
        this.logOnMember = logOnMember;
    }
}