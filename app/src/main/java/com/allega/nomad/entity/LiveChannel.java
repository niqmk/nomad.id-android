package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 8/8/15.
 */
public class LiveChannel extends RealmObject {

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
    @Expose
    private RealmList<LiveChannelCategory> liveChannelCategories = new RealmList<LiveChannelCategory>();
    @Expose
    private Images images;
    @Expose
    private RealmList<Price> prices = new RealmList<Price>();
    @SerializedName("total_likes")
    @Expose
    private long totalLikes;
    @Expose
    private Videos videos;
    @SerializedName("log_on_member")
    @Expose
    private LogOnMember logOnMember;

    public LiveChannel() {
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

    public RealmList<LiveChannelCategory> getLiveChannelCategories() {
        return liveChannelCategories;
    }

    public void setLiveChannelCategories(RealmList<LiveChannelCategory> liveChannelCategories) {
        this.liveChannelCategories = liveChannelCategories;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public RealmList<Price> getPrices() {
        return prices;
    }

    public void setPrices(RealmList<Price> prices) {
        this.prices = prices;
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