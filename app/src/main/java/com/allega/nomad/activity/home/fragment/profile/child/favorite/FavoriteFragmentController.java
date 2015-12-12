package com.allega.nomad.activity.home.fragment.profile.child.favorite;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.activity.home.fragment.livechannel.LiveChannelFragment;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.activity.home.fragment.tvshow.TvShowFragment;
import com.allega.nomad.adapter.ProfileWatchListAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.Member;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.entity.WatchListVideo;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.ResponsePaging;
import com.allega.nomad.storage.PreferenceProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class FavoriteFragmentController extends BaseFragmentController<FavoriteFragment> {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final ProfileWatchListAdapter adapter;
    private final List<WatchListVideo> movieList = new ArrayList<>();
    private final PreferenceProvider preferenceProvider;

    public FavoriteFragmentController(FavoriteFragment fragment, View view) {
        super(fragment, view);
        adapter = new ProfileWatchListAdapter(context, movieList);
        if (movieList.size() == 0) {
            fragment.setVisibilityProgressBar(View.VISIBLE);
        }
        preferenceProvider = new PreferenceProvider(context);
        fragment.setupLikeList(adapter);
        getWatchList();
    }

    private void getWatchList() {
        Member member = preferenceProvider.getMember();
        if (member != null)
            appRestService.getMemberWatchlist(member.getId(), new ResponseCallback<ResponsePaging<WatchListVideo>>(context) {
                @Override
                public void success(ResponsePaging<WatchListVideo> responsePaging) {
                    if (responsePaging.getStatus() == 1) {
                        movieList.addAll(responsePaging.getResults().getData());
                    }
                    fragment.updateList(adapter);
                    fragment.setVisibilityProgressBar(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    fragment.setVisibilityProgressBar(View.GONE);
                }
            });
    }

    @OnItemClick(R.id.movie_list_view)
    void onMovie(int position) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            WatchListVideo watchListVideo = adapter.getItem(position);
            switch (watchListVideo.getVideoTypeId()) {
                case 1:
                    Movie movie = new Movie();
                    movie.setId(watchListVideo.getDetail().getId());
                    homeActivity.goToFragment(MovieFragment.newInstance(movie));
                    break;
                case 2:
                    Event event = new Event();
                    event.setId(watchListVideo.getDetail().getId());
                    homeActivity.goToFragment(EventFragment.newInstance(event));
                    break;
                case 3:
                    Edutainment edutainment = new Edutainment();
                    edutainment.setId(watchListVideo.getDetail().getId());
                    homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainment));
                    break;
                case 4:
                    TvShowEpisode tvShowEpisode = new TvShowEpisode();
                    tvShowEpisode.setId(watchListVideo.getDetail().getId());
                    homeActivity.goToFragment(TvShowFragment.newInstance(tvShowEpisode));
                    break;
                case 5:
                    LiveChannel liveChannel = new LiveChannel();
                    liveChannel.setId(watchListVideo.getDetail().getId());
                    homeActivity.goToFragment(LiveChannelFragment.newInstance(liveChannel));
                    break;
            }
        }
    }
}