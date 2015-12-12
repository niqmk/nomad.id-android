package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 5/30/15.
 */
public class Movie extends RealmObject {

    public static final String FIELD_ID = "id";
    public static final String FIELD_VIDEO_ID = "videoId";

    @Expose
    @PrimaryKey
    private long id;
    @SerializedName("video_id")
    @Expose
    private long videoId;
    @Expose
    private String title;
    @Expose
    private String description;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("run_time_in_minute")
    @Expose
    private long runTimeInMinute;
    @Expose
    private String languages;
    @Expose
    private String subtitles;
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
    @Expose
    private RealmList<MovieGenre> movieGenres = new RealmList<MovieGenre>();
    @Expose
    private RealmList<MovieCast> movieCasts = new RealmList<MovieCast>();
    @Expose
    private RealmList<MovieDirector> movieDirectors = new RealmList<MovieDirector>();
    @Expose
    private RealmList<Price> prices = new RealmList<>();
    @Expose
    @SerializedName("total_likes")
    private long totalLikes;
    @Expose
    @SerializedName("overall_rating")
    private double overallRating;
    @Expose
    @SerializedName("log_on_member")
    private LogOnMember logOnMember;

    public Movie() {
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRunTimeInMinute() {
        return runTimeInMinute;
    }

    public void setRunTimeInMinute(long runTimeInMinute) {
        this.runTimeInMinute = runTimeInMinute;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
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

    public RealmList<MovieGenre> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(RealmList<MovieGenre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public RealmList<MovieCast> getMovieCasts() {
        return movieCasts;
    }

    public void setMovieCasts(RealmList<MovieCast> movieCasts) {
        this.movieCasts = movieCasts;
    }

    public RealmList<MovieDirector> getMovieDirectors() {
        return movieDirectors;
    }

    public void setMovieDirectors(RealmList<MovieDirector> movieDirectors) {
        this.movieDirectors = movieDirectors;
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

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public LogOnMember getLogOnMember() {
        return logOnMember;
    }

    public void setLogOnMember(LogOnMember logOnMember) {
        this.logOnMember = logOnMember;
    }
}