package com.allega.nomad.service.rest.app;

import com.allega.nomad.entity.Ad;
import com.allega.nomad.entity.AdCategory;
import com.allega.nomad.entity.AdComment;
import com.allega.nomad.entity.Collection;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.EdutainmentCategory;
import com.allega.nomad.entity.EdutainmentComment;
import com.allega.nomad.entity.EdutainmentPreview;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.EventCategory;
import com.allega.nomad.entity.EventComment;
import com.allega.nomad.entity.EventPreview;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.LiveChannelCategory;
import com.allega.nomad.entity.LiveChannelComment;
import com.allega.nomad.entity.Member;
import com.allega.nomad.entity.Message;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.MovieCategory;
import com.allega.nomad.entity.MovieClip;
import com.allega.nomad.entity.MovieComment;
import com.allega.nomad.entity.MoviePreview;
import com.allega.nomad.entity.Serial;
import com.allega.nomad.entity.SerialCategory;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.SerialEpisodeComment;
import com.allega.nomad.entity.TvShow;
import com.allega.nomad.entity.TvShowCategory;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.entity.TvShowEpisodeComment;
import com.allega.nomad.entity.WatchListVideo;
import com.allega.nomad.model.CategoryPreview;
import com.allega.nomad.model.Point;
import com.allega.nomad.model.Slider;
import com.allega.nomad.service.rest.app.request.MemberRequest;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.service.rest.app.response.ResponsePaging;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by imnotpro on 5/4/15.
 */
public interface AppRestService {

    @Multipart
    @POST("/member")
    void postMember(@Part("email") String email, @Part("password") String password, @Part("password_confirmation") String passwordConfirmation, ResponseCallback<Response<Member>> callback);

    @Multipart
    @POST("/member/fb-log-in")
    void postFacebook(@Part("fb_id") String facebookId, @Part("email") String email, ResponseCallback<Response<Member>> callback);

    @Multipart
    @POST("/member/log-in")
    void postLogin(@Part("username") String username, @Part("password") String password, ResponseCallback<Response<Member>> callback);

    @POST("/member/log-out")
    void postLogout(ResponseCallback<Response<Member>> callback);

    @GET("/member/{id}")
    void getMember(@Path("id") long id, ResponseCallback<Response<Member>> callback);

    @PUT("/member/{id}")
    void putMember(@Path("id") long id, @Body MemberRequest member, ResponseCallback<Response<Member>> callback);

    @GET("/member/{id}/messages")
    void getMessage(@Path("id") long id, ResponseCallback<Response<Message>> callback);

    @GET("/member/{id}/watchlists")
    void getMemberWatchlist(@Path("id") long id, ResponseCallback<ResponsePaging<WatchListVideo>> callback);

    @GET("/member/{id}/purchases")
    void getMemberCollection(@Path("id") long id, ResponseCallback<Response<Collection>> callback);

    @Multipart
    @POST("/member/{id}/point")
    void postMemberPoint(@Path("id") long id, @Part("point_category_id") String idPoint, @Part("total") long value, ResponseCallback<Response> callback);

    @Multipart
    @POST("/member/forgot-password")
    void postForgotPassword(@Part("email") String email, ResponseCallback<Response> responseCallback);

    @GET("/video/category-previews")
    void getCategoryPreview(ResponseCallback<Response<CategoryPreview>> callback);

    @POST("/video/{id}/watchlist")
    void postWatchList(@Path("id") Long id, ResponseCallback<Response> callback);

    @POST("/video/{id}/unwatchlist")
    void postUnWatchList(@Path("id") Long id, ResponseCallback<Response> callback);

    @Multipart
    @POST("/video/{id}/purchase")
    void postVideoPurchase(@Path("id") long id, @Part("point") String point, @Part("expiry_days") String expiryDays, ResponseCallback<Response> callback);

    @POST("/video/{id}/watchlist")
    void postWatchlist(@Path("id") long id, ResponseCallback<Response> callback);

    @GET("/slider")
    void getSlider(ResponseCallback<ResponsePaging<Slider>> callback);

    @GET("/paid-point")
    void getPointPaid(ResponseCallback<Response<Point>> callback);

    @GET("/free-point")
    void getPointFree(ResponseCallback<Response<Point>> callback);

    @POST("/member/{id}/resend-email")
    void postResendEmail(@Path("id") Long id, ResponseCallback<Response> callback);

    /* AD */

    @GET("/ad-video-category")
    void getAdCategory(ResponseCallback<Response<AdCategory>> callback);

    @GET("/ad-video")
    void getAdAll(@Query("before") String before, ResponseCallback<Response<Ad>> callback);

    @GET("/ad-video/{id}")
    void getAd(@Path("id") long id, ResponseCallback<Response<Ad>> callback);

    @GET("/ad-video/category/{category}")
    void getAdCategory(@Path("category") long category,
                       @Query("before") String before, @Query("sort") String sort, @Query("filter") String filter, @Query("upcoming") Boolean upcoming,
                       ResponseCallback<Response<Ad>> callback);

    @GET("/ad-video/{id}/comments")
    void getAdComments(@Path("id") long id, @Query("before") String after, ResponseCallback<Response<AdComment>> callback);

    @GET("/ad-video/populars")
    void getAdPopular(@Query("before") String before, ResponseCallback<Response<Ad>> callback);

    @GET("/ad-video/latest")
    void getAdLatest(ResponseCallback<Response<Ad>> callback);

    @GET("/ad-video/{id}/relateds")
    void getAdRelateds(@Path("id") long id, ResponseCallback<Response<Ad>> callback);

    @PUT("/ad-video/{id}/play-count")
    void putAdPlayCount(@Path("id") long id, ResponseCallback<Response> callback);

    @GET("/ad-video/search/{query}")
    void searchAd(@Path("query") String query, @Query("before") String before, ResponseCallback<Response<Ad>> callback);

    @POST("/ad-video/{id}/like")
    void postAdLike(@Path("id") long id, ResponseCallback<Response> callback);

    @POST("/ad-video/{id}/unlike")
    void postAdUnlike(@Path("id") long id, ResponseCallback<Response> callback);

    @Multipart
    @POST("/ad-video/{id}/comment")
    void postAdComment(@Path("id") long id, @Part("description") String description, ResponseCallback<Response<AdComment>> callback);

    /* MOVIE */

    @GET("/movie-genre")
    void getMovieGenre(ResponseCallback<Response<MovieCategory>> callback);

    @GET("/movie")
    void getMovieAll(@Query("before") String before, ResponseCallback<Response<Movie>> callback);

    @GET("/movie/{id}")
    void getMovie(@Path("id") long id, ResponseCallback<Response<Movie>> callback);

    @GET("/movie/latests")
    void getMovieLatest(ResponseCallback<Response<Movie>> callback);

    @GET("/movie/populars")
    void getMoviePopular(@Query("before") String before, ResponseCallback<Response<Movie>> callback);

    @GET("/movie/upcomings")
    void getMovieUpcoming(@Query("before") String before, ResponseCallback<Response<Movie>> callback);

    @GET("/movie/paids")
    void getMoviePaid(@Query("before") String before, ResponseCallback<Response<Movie>> callback);

    @GET("/movie/frees")
    void getMovieFree(@Query("before") String before, ResponseCallback<Response<Movie>> callback);

    @GET("/movie/genre-previews")
    void getMoviePreview(ResponseCallback<Response<MoviePreview>> callback);

    @GET("/movie/genre/{genre_id}")
    void getMovieGenre(@Path("genre_id") long id,
                       @Query("before") String before, @Query("sort") String sort, @Query("filter") String filter, @Query("upcoming") Boolean upcoming,
                       ResponseCallback<Response<Movie>> callback);

    @GET("/movie/{id}/clips")
    void getMovieClip(@Path("id") long id, ResponseCallback<Response<MovieClip>> callback);

    @GET("/movie/{id}/comments")
    void getMovieComments(@Path("id") long id, @Query("before") String after, ResponseCallback<Response<MovieComment>> callback);

    @GET("/movie/{id}/relateds")
    void getMovieRelateds(@Path("id") long id, ResponseCallback<Response<Movie>> callback);

    @PUT("/movie/{id}/play-count")
    void putMoviePlayCount(@Path("id") long id, ResponseCallback<Response> callback);

    @GET("/movie/search/{query}")
    void searchMovie(@Path("query") String query, @Query("before") String before, ResponseCallback<Response<Movie>> callback);

    @POST("/movie/{id}/like")
    void postMovieLike(@Path("id") long id, ResponseCallback<Response> callback);

    @POST("/movie/{id}/unlike")
    void postMovieUnlike(@Path("id") long id, ResponseCallback<Response> callback);

    @Multipart
    @POST("/movie/{id}/comment")
    void postMovieComment(@Path("id") long id, @Part("rate") Integer rate, @Part("description") String description, ResponseCallback<Response<MovieComment>> callback);

    /* EVENT */

    @GET("/event-category")
    void getEventCategory(ResponseCallback<Response<EventCategory>> callback);

    @GET("/event")
    void getEventAll(@Query("before") String before, ResponseCallback<Response<Event>> callback);

    @GET("/event/{id}")
    void getEvent(@Path("id") long id, ResponseCallback<Response<Event>> callback);

    @GET("/event/category-previews")
    void getEventPreview(ResponseCallback<Response<EventPreview>> callback);

    @GET("/event/frees")
    void getEventFree(ResponseCallback<Response<Event>> callback);

    @GET("/event/populars")
    void getEventPopular(@Query("before") String before, ResponseCallback<Response<Event>> callback);

    @GET("/event/watchlist")
    void getEventWatchlist(ResponseCallback<Response<Event>> callback);

    @GET("/event/category/{id}")
    void getEventCategory(@Path("id") long id,
                          @Query("before") String before, @Query("sort") String sort, @Query("filter") String filter, @Query("upcoming") Boolean upcoming,
                          ResponseCallback<Response<Event>> callback);

    @GET("/event/{id}/comments")
    void getEventComments(@Path("id") long id, @Query("before") String after, ResponseCallback<Response<EventComment>> callback);

    @GET("/event/{id}/relateds")
    void getEventRecommended(@Path("id") long id, ResponseCallback<Response<Event>> callback);

    @GET("/event/search/{query}")
    void searchEvent(@Path("query") String query, @Query("before") String before, ResponseCallback<Response<Event>> callback);

    @POST("/event/{id}/like")
    void postEventLike(@Path("id") long id, ResponseCallback<Response> callback);

    @POST("/event/{id}/unlike")
    void postEventUnlike(@Path("id") long id, ResponseCallback<Response> callback);

    @Multipart
    @POST("/event/{id}/comment")
    void postEventComment(@Path("id") long id, @Part("description") String description, ResponseCallback<Response<EventComment>> callback);

    @PUT("/event/{id}/play-count")
    void putEventPlayCount(@Path("id") long id, ResponseCallback<Response> callback);

    /* EDUTAINMENT */

    @GET("/edutainment-category")
    void getEdutainmentCategory(ResponseCallback<Response<EdutainmentCategory>> callback);

    @GET("/edutainment")
    void getEdutainmentAll(@Query("before") String before, ResponseCallback<Response<Edutainment>> callback);

    @GET("/edutainment/{id}")
    void getEdutainment(@Path("id") long id, ResponseCallback<Response<Edutainment>> callback);

    @GET("/edutainment/category-previews")
    void getEdutainmentPreview(ResponseCallback<Response<EdutainmentPreview>> callback);

    @GET("/edutainment/frees")
    void getEdutainmentFree(ResponseCallback<Response<Edutainment>> callback);

    @GET("/edutainment/populars")
    void getEdutainmentPopular(@Query("before") String before, ResponseCallback<Response<Edutainment>> callback);

    @GET("/edutainment/watchlist")
    void getEdutainmentWatchlist(ResponseCallback<Response<Edutainment>> callback);

    @GET("/edutainment/category/{id}")
    void getEdutainmentCategory(@Path("id") long id,
                                @Query("before") String before, @Query("sort") String sort, @Query("filter") String filter, @Query("upcoming") Boolean upcoming,
                                ResponseCallback<Response<Edutainment>> callback);

    @GET("/edutainment/{id}/comments")
    void getEdutainmentComments(@Path("id") long id, @Query("before") String after, ResponseCallback<Response<EdutainmentComment>> callback);

    @GET("/edutainment/{id}/relateds")
    void getEdutainmentRecommended(@Path("id") long id, ResponseCallback<Response<Edutainment>> callback);

    @GET("/edutainment/search/{query}")
    void searchEdutainment(@Path("query") String query, @Query("before") String before, ResponseCallback<Response<Edutainment>> callback);

    @POST("/edutainment/{id}/like")
    void postEdutainmentLike(@Path("id") long id, ResponseCallback<Response> callback);

    @POST("/edutainment/{id}/unlike")
    void postEdutainmentUnlike(@Path("id") long id, ResponseCallback<Response> callback);

    @Multipart
    @POST("/edutainment/{id}/comment")
    void postEdutainmentComment(@Path("id") long id, @Part("description") String description, ResponseCallback<Response<EdutainmentComment>> callback);

    @PUT("/edutainment/{id}/play-count")
    void putEdutainmentPlayCount(@Path("id") long id, ResponseCallback<Response> callback);

    /* TV-Show */

    @GET("/tv-show-category")
    void getTvShowCategory(ResponseCallback<Response<TvShowCategory>> callback);

    @GET("/tv-show")
    void getTvShowAll(@Query("before") String before, ResponseCallback<Response<TvShow>> callback);

    @GET("/tv-show/episode/{id}")
    void getTvShowEpisode(@Path("id") long id, ResponseCallback<Response<TvShowEpisode>> callback);

    @GET("/tv-show/category-previews")
    void getTvShowPreview(ResponseCallback<Response<EdutainmentPreview>> callback);

    @GET("/tv-show/episode/frees")
    void getTvShowEpisodeFree(ResponseCallback<Response<TvShowEpisode>> callback);

    @GET("/tv-show/episode/populars")
    void getTvShowEpisodePopular(@Query("before") String before, ResponseCallback<Response<TvShowEpisode>> callback);

    @GET("/tv-show/watchlist")
    void getTvShowWatchlist(ResponseCallback<Response<TvShow>> callback);

    @GET("/tv-show/category/{id}")
    void getTvShowCategory(@Path("id") long id,
                           @Query("before") String before, @Query("sort") String sort, @Query("filter") String filter, @Query("upcoming") Boolean upcoming,
                           ResponseCallback<Response<TvShow>> callback);

    @GET("/tv-show/episode/{id}/comments")
    void getTvShowEpisodeComments(@Path("id") long id, @Query("before") String after, ResponseCallback<Response<TvShowEpisodeComment>> callback);

    @GET("/tv-show/{id}/relateds")
    void getTvShowRecommended(@Path("id") long id, ResponseCallback<Response<TvShow>> callback);

    @GET("/tv-show/episode/search/{query}")
    void searchTvShow(@Path("query") String query, @Query("before") String before, ResponseCallback<Response<TvShowEpisode>> callback);

    @POST("/tv-show/episode/{id}/like")
    void postTvShowEpisodeLike(@Path("id") long id, ResponseCallback<Response> callback);

    @POST("/tv-show/episode/{id}/unlike")
    void postTvShowEpisodeUnlike(@Path("id") long id, ResponseCallback<Response> callback);

    @Multipart
    @POST("/tv-show/episode/{id}/comment")
    void postTvShowComment(@Path("id") long id, @Part("description") String description, ResponseCallback<Response<TvShowEpisodeComment>> callback);

    @PUT("/tv-show/{id}/play-count")
    void putTvShowPlayCount(@Path("id") long id, ResponseCallback<Response> callback);

    /* LIVE CHANNEL */

    @GET("/live-channel-category")
    void getLiveChannelCategory(ResponseCallback<Response<LiveChannelCategory>> callback);

    @GET("/live-channel")
    void getLiveChannelAll(@Query("before") String before, ResponseCallback<Response<LiveChannel>> callback);

    @GET("/live-channel/{id}")
    void getLiveChannel(@Path("id") long id, ResponseCallback<Response<LiveChannel>> callback);

    @GET("/live-channel/category-previews")
    void getLiveChannelPreview(ResponseCallback<Response<EdutainmentPreview>> callback);

    @GET("/live-channel/frees")
    void getLiveChannelFree(ResponseCallback<Response<LiveChannel>> callback);

    @GET("/live-channel/populars")
    void getLiveChannelPopular(@Query("before") String before, ResponseCallback<Response<LiveChannel>> callback);

    @GET("/live-channel/watchlist")
    void getLiveChannelWatchlist(ResponseCallback<Response<LiveChannel>> callback);

    @GET("/live-channel/category/{id}")
    void getLiveChannelCategory(@Path("id") long id,
                                @Query("before") String before, @Query("sort") String sort, @Query("filter") String filter, @Query("upcoming") Boolean upcoming,
                                ResponseCallback<Response<LiveChannel>> callback);

    @GET("/live-channel/{id}/comments")
    void getLiveChannelComments(@Path("id") long id, @Query("before") String after, ResponseCallback<Response<LiveChannelComment>> callback);

    @GET("/live-channel/{id}/relateds")
    void getLiveChannelRecommended(@Path("id") long id, ResponseCallback<Response<LiveChannel>> callback);

    @GET("/live-channel/search/{query}")
    void searchLiveChannel(@Path("query") String query, @Query("before") String before, ResponseCallback<Response<LiveChannel>> callback);

    @POST("/live-channel/{id}/like")
    void postLiveChannelLike(@Path("id") long id, ResponseCallback<Response> callback);

    @POST("/live-channel/{id}/unlike")
    void postLiveChannelUnlike(@Path("id") long id, ResponseCallback<Response> callback);

    @Multipart
    @POST("/live-channel/{id}/comment")
    void postLiveChannelComment(@Path("id") long id, @Part("description") String description, ResponseCallback<Response<LiveChannelComment>> callback);

    @PUT("/live-channel/{id}/play-count")
    void putLiveChannelPlayCount(@Path("id") long id, ResponseCallback<Response> callback);

    /* Series */

    @GET("/serial-category")
    void getSerialCategory(ResponseCallback<Response<SerialCategory>> callback);

    @GET("/serial")
    void getSerialAll(@Query("before") String before, ResponseCallback<Response<Serial>> callback);

    @GET("/serial/episode/{id}")
    void getSerialEpisode(@Path("id") long id, ResponseCallback<Response<SerialEpisode>> callback);

    @GET("/serial/category-previews")
    void getSerialPreview(ResponseCallback<Response<EdutainmentPreview>> callback);

    @GET("/serial/episode/frees")
    void getSerialEpisodeFree(ResponseCallback<Response<SerialEpisode>> callback);

    @GET("/serial/episode/populars")
    void getSerialEpisodePopular(@Query("before") String before, ResponseCallback<Response<SerialEpisode>> callback);

    @GET("/serial/watchlist")
    void getSerialWatchlist(ResponseCallback<Response<Serial>> callback);

    @GET("/serial/category/{id}")
    void getSerialCategory(@Path("id") long id,
                           @Query("before") String before, @Query("sort") String sort, @Query("filter") String filter, @Query("upcoming") Boolean upcoming,
                           ResponseCallback<Response<Serial>> callback);

    @GET("/serial/episode/{id}/comments")
    void getSerialEpisodeComments(@Path("id") long id, @Query("before") String after, ResponseCallback<Response<SerialEpisodeComment>> callback);

    @GET("/serial/{id}/relateds")
    void getSerialRecommended(@Path("id") long id, ResponseCallback<Response<Serial>> callback);

    @GET("/serial/episode/search/{query}")
    void searchSerial(@Path("query") String query, @Query("before") String before, ResponseCallback<Response<SerialEpisode>> callback);

    @POST("/serial/episode/{id}/like")
    void postSerialEpisodeLike(@Path("id") long id, ResponseCallback<Response> callback);

    @POST("/serial/episode/{id}/unlike")
    void postSerialEpisodeUnlike(@Path("id") long id, ResponseCallback<Response> callback);

    @Multipart
    @POST("/serial/episode/{id}/comment")
    void postSerialComment(@Path("id") long id, @Part("description") String description, ResponseCallback<Response<SerialEpisodeComment>> callback);

    @PUT("/serial/{id}/play-count")
    void putSerialPlayCount(@Path("id") long id, ResponseCallback<Response> callback);

}
