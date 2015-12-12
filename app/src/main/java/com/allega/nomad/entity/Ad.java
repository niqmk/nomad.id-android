package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 7/25/15.
 */
public class Ad extends RealmObject {

    public static final String FIELD_ID = "id";

    @PrimaryKey
    @Expose
    private long id;
    @SerializedName("ad_id")
    @Expose
    private long adId;
    @SerializedName("vendor_id")
    @Expose
    private long vendorId;
    @Expose
    private String title;
    @Expose
    private String description;
    @SerializedName("url_type_id")
    @Expose
    private long urlTypeId;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("view_count")
    @Expose
    private long viewCount;
    @SerializedName("play_count")
    @Expose
    private long playCount;
    @SerializedName("point_reward")
    @Expose
    private long pointReward;
    @SerializedName("created_at")
    @Expose
    private long createdAt;
    @SerializedName("updated_at")
    @Expose
    private long updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private long deletedAt;
    @Expose
    private Images images;
    @SerializedName("total_likes")
    @Expose
    private long totalLikes;
    @Expose
    private Videos videos;
    @Expose
    private RealmList<Price> prices = new RealmList<>();
    @SerializedName("log_on_member")
    @Expose
    private LogOnMember logOnMember;

    public Ad() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAdId() {
        return adId;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }

    public long getPointReward() {
        return pointReward;
    }

    public void setPointReward(long pointReward) {
        this.pointReward = pointReward;
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

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
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

    public RealmList<Price> getPrices() {
        return prices;
    }

    public void setPrices(RealmList<Price> prices) {
        this.prices = prices;
    }

    public LogOnMember getLogOnMember() {
        return logOnMember;
    }

    public void setLogOnMember(LogOnMember logOnMember) {
        this.logOnMember = logOnMember;
    }
}
