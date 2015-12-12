package com.allega.nomad.activity.home.fragment.discover.child;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.ad.AdFragment;
import com.allega.nomad.activity.home.fragment.edutainment.EdutainmentFragment;
import com.allega.nomad.activity.home.fragment.event.EventFragment;
import com.allega.nomad.activity.home.fragment.livechannel.LiveChannelFragment;
import com.allega.nomad.activity.home.fragment.movie.MovieFragment;
import com.allega.nomad.activity.home.fragment.serial.SerialFragment;
import com.allega.nomad.activity.home.fragment.tvshow.TvShowFragment;
import com.allega.nomad.adapter.DiscoverAdAdapter;
import com.allega.nomad.adapter.DiscoverEdutainmentAdapter;
import com.allega.nomad.adapter.DiscoverEventAdapter;
import com.allega.nomad.adapter.DiscoverLiveChannelAdapter;
import com.allega.nomad.adapter.DiscoverMovieAdapter;
import com.allega.nomad.adapter.DiscoverSerialAdapter;
import com.allega.nomad.adapter.DiscoverSerialEpisodeAdapter;
import com.allega.nomad.adapter.DiscoverTvShowAdapter;
import com.allega.nomad.adapter.DiscoverTvShowEpisodeAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.Serial;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.TvShow;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.model.Slider;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.service.rest.app.response.ResponsePaging;
import com.allega.nomad.storage.DatabaseManager;

import org.lucasr.twowayview.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DiscoverChildFragmentController extends BaseFragmentController<DiscoverChildFragment> implements ItemClickSupport.OnItemClickListener {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final Realm realm;

    private final List<Slider> sliderList = new ArrayList<>();

    private final DiscoverMovieAdapter discoverMovieAdapter;
    private final List<Movie> movieList = new ArrayList<>();

    private final DiscoverEventAdapter discoverEventAdapter;
    private final List<Event> eventList = new ArrayList<>();

    private final DiscoverEdutainmentAdapter discoverEdutainmentAdapter;
    private final List<Edutainment> edutainmentList = new ArrayList<>();

    private final DiscoverTvShowAdapter discoverTvShowAdapter;
    private final List<TvShow> tvShowList = new ArrayList<>();
    private final DiscoverTvShowEpisodeAdapter discoverTvShowEpisodeAdapter;
    private final List<TvShowEpisode> tvShowEpisodeList = new ArrayList<>();

    private final DiscoverLiveChannelAdapter discoverLiveChannelAdapter;
    private final List<LiveChannel> liveChannelList = new ArrayList<>();

    private final DiscoverSerialAdapter discoverSerialAdapter;
    private final List<Serial> serialList = new ArrayList<>();
    private final DiscoverSerialEpisodeAdapter discoverSerialEpisodeAdapter;
    private final List<SerialEpisode> serialEpisodeList = new ArrayList<>();

    private final DiscoverAdAdapter discoverAdAdapter;
    private final List<Ad> adList = new ArrayList<>();

    public DiscoverChildFragmentController(DiscoverChildFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        discoverMovieAdapter = new DiscoverMovieAdapter(context, movieList);
        discoverEventAdapter = new DiscoverEventAdapter(context, eventList);
        discoverEdutainmentAdapter = new DiscoverEdutainmentAdapter(context, edutainmentList);
        discoverTvShowAdapter = new DiscoverTvShowAdapter(context, tvShowList);
        discoverTvShowEpisodeAdapter = new DiscoverTvShowEpisodeAdapter(context, tvShowEpisodeList);
        discoverLiveChannelAdapter = new DiscoverLiveChannelAdapter(context, liveChannelList);
        discoverSerialAdapter = new DiscoverSerialAdapter(context, serialList);
        discoverSerialEpisodeAdapter = new DiscoverSerialEpisodeAdapter(context, serialEpisodeList);
        discoverAdAdapter = new DiscoverAdAdapter(context, adList);
    }

    public void setupList() {
        fragment.setupMovieList(discoverMovieAdapter);
        fragment.setupEventList(discoverEventAdapter);
        fragment.setupEdutainmentList(discoverEdutainmentAdapter);
        fragment.setupLiveChannelList(discoverLiveChannelAdapter);
//        fragment.setupTvShowList(discoverTvShowAdapter);
        fragment.setupAdList(discoverAdAdapter);
        getDiscover();
    }

    private void getDiscover() {
        if (fragment.position == 0) {
            getSlider();
        }
        getMovie();
        getEvent();
        getEdutainment();
        getTvShow();
        getLiveChannel();
        getAd();
        getSerial();
    }

    private void getSlider() {
        appRestService.getSlider(new ResponseCallback<ResponsePaging<Slider>>(context) {
            @Override
            public void success(ResponsePaging<Slider> sliderResponsePaging) {
                if (sliderResponsePaging.getStatus() == 1) {
                    sliderList.addAll(sliderResponsePaging.getResults().getData());
                    fragment.setupSlider(sliderList);
                }
            }
        });
    }

    private void getMovie() {
        switch (fragment.position) {
            default:
            case 0:
                appRestService.getMovieAll(null, new MovieResponseCallback(context));
                break;
            case 1:
                appRestService.getMoviePopular(null, new MovieResponseCallback(context));
                break;
            case 2:
                appRestService.getMovieFree(null, new MovieResponseCallback(context));
                break;
            case 3:
                appRestService.getMovieUpcoming(null, new MovieResponseCallback(context));
                break;
        }
    }

    private void getEvent() {
        switch (fragment.position) {
            default:
            case 0:
                appRestService.getEventAll(null, new EventResponseCallback(context));
                break;
            case 1:
                appRestService.getEventPopular(null, new EventResponseCallback(context));
                break;
            case 2:
                appRestService.getEventFree(new EventResponseCallback(context));
                break;
            case 3:
                break;
        }
    }

    private void getEdutainment() {
        switch (fragment.position) {
            default:
            case 0:
                appRestService.getEdutainmentAll(null, new EdutainmentResponseCallback(context));
                break;
            case 1:
                appRestService.getEdutainmentPopular(null, new EdutainmentResponseCallback(context));
                break;
            case 2:
                appRestService.getEdutainmentFree(new EdutainmentResponseCallback(context));
                break;
            case 3:
                break;
        }
    }

    private void getTvShow() {
        switch (fragment.position) {
            default:
            case 0:
                appRestService.getTvShowAll(null, new TvShowResponseCallback(context));
                break;
            case 1:
                appRestService.getTvShowEpisodePopular(null, new TvShowEpisodeResponseCallback(context));
                break;
            case 2:
                appRestService.getTvShowEpisodeFree(new TvShowEpisodeResponseCallback(context));
                break;
            case 3:
                break;
        }
    }

    private void getLiveChannel() {
        switch (fragment.position) {
            default:
            case 0:
                appRestService.getLiveChannelAll(null, new LiveChannelResponseCallback(context));
                break;
            case 1:
                appRestService.getLiveChannelPopular(null, new LiveChannelResponseCallback(context));
                break;
            case 2:
                appRestService.getLiveChannelFree(new LiveChannelResponseCallback(context));
                break;
            case 3:
                break;
        }
    }

    private void getSerial() {
        switch (fragment.position) {
            default:
            case 0:
                appRestService.getSerialAll(null, new SerialResponseCallback(context));
                break;
            case 1:
                appRestService.getSerialEpisodePopular(null, new SerialEpisodeResponseCallback(context));
                break;
            case 2:
                appRestService.getSerialEpisodeFree(new SerialEpisodeResponseCallback(context));
                break;
            case 3:
                break;
        }
    }

    private void getAd() {
        switch (fragment.position) {
            default:
            case 0:
                appRestService.getAdAll(null, new AdResponseCallback(context));
                break;
            case 1:
                appRestService.getAdPopular(null, new AdResponseCallback(context));
                break;
            case 2:
                appRestService.getAdLatest(new AdResponseCallback(context));
                break;
            case 3:
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View child, int position, long id) {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            switch (parent.getId()) {
                case R.id.movie_list_view:
                    Movie movie = new Movie();
                    movie.setId(movieList.get(position).getId());
                    homeActivity.goToFragment(MovieFragment.newInstance(movie));
                    break;
                case R.id.event_list_view:
                    Event event = new Event();
                    event.setId(eventList.get(position).getId());
                    homeActivity.goToFragment(EventFragment.newInstance(event));
                    break;
                case R.id.edutainment_list_view:
                    Edutainment edutainment = new Edutainment();
                    edutainment.setId(edutainmentList.get(position).getId());
                    homeActivity.goToFragment(EdutainmentFragment.newInstance(edutainment));
                    break;
                case R.id.tv_show_list_view:
                    switch (fragment.position) {
                        default:
                        case 1:
                        case 2:
                        case 3:
                            homeActivity.goToFragment(TvShowFragment.newInstance(tvShowEpisodeList.get(position)));
                            break;
                        case 0:
                            homeActivity.goToFragment(TvShowFragment.newInstance(tvShowList.get(position)));
                            break;
                    }
                    break;
                case R.id.live_channel_list_view:
                    LiveChannel liveChannel = new LiveChannel();
                    liveChannel.setId(liveChannelList.get(position).getId());
                    homeActivity.goToFragment(LiveChannelFragment.newInstance(liveChannel));
                    break;
                case R.id.serial_list_view:
                    switch (fragment.position) {
                        default:
                        case 1:
                        case 2:
                        case 3:
                            homeActivity.goToFragment(SerialFragment.newInstance(serialEpisodeList.get(position)));
                            break;
                        case 0:
                            homeActivity.goToFragment(SerialFragment.newInstance(serialList.get(position)));
                            break;
                    }
                    break;
                case R.id.ad_list_view:
                    Ad ad = new Ad();
                    ad.setId(adList.get(position).getId());
                    homeActivity.goToFragment(AdFragment.newInstance(ad));
                    break;
            }
        }
    }

    private class MovieResponseCallback extends ResponseCallback<Response<Movie>> {

        public MovieResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Movie> movieResponse) {
            if (movieResponse.getStatus() == 1) {
                List<Movie> resultList = movieResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                movieList.clear();
                movieList.addAll(resultList);
                fragment.updateMovieList(discoverMovieAdapter);
            }
        }
    }

    private class EventResponseCallback extends ResponseCallback<Response<Event>> {

        public EventResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Event> eventResponse) {
            if (eventResponse.getStatus() == 1) {
                List<Event> resultList = eventResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                eventList.clear();
                eventList.addAll(resultList);
                fragment.updateEventList(discoverEventAdapter);
            }
        }
    }

    private class EdutainmentResponseCallback extends ResponseCallback<Response<Edutainment>> {

        public EdutainmentResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Edutainment> edutainmentResponse) {
            if (edutainmentResponse.getStatus() == 1) {
                List<Edutainment> resultList = edutainmentResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                edutainmentList.clear();
                edutainmentList.addAll(resultList);
                fragment.updateEdutainmentList(discoverEdutainmentAdapter);
            }
        }
    }

    private class TvShowResponseCallback extends ResponseCallback<Response<TvShow>> {

        public TvShowResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<TvShow> tvShowResponse) {
            if (tvShowResponse.getStatus() == 1) {
                List<TvShow> resultList = tvShowResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                tvShowList.clear();
                tvShowList.addAll(resultList);
                fragment.setupTvShowList(discoverTvShowAdapter);
                fragment.updateTvShowList(discoverTvShowAdapter);
            }
        }
    }

    private class TvShowEpisodeResponseCallback extends ResponseCallback<Response<TvShowEpisode>> {

        public TvShowEpisodeResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<TvShowEpisode> tvShowResponse) {
            if (tvShowResponse.getStatus() == 1) {
                List<TvShowEpisode> resultList = tvShowResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                tvShowEpisodeList.clear();
                tvShowEpisodeList.addAll(resultList);
                fragment.setupTvShowEpisodeList(discoverTvShowEpisodeAdapter);
                fragment.updateTvShowEpisodeList(discoverTvShowEpisodeAdapter);
            }
        }
    }

    private class LiveChannelResponseCallback extends ResponseCallback<Response<LiveChannel>> {

        public LiveChannelResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<LiveChannel> liveChannelResponse) {
            if (liveChannelResponse.getStatus() == 1) {
                List<LiveChannel> resultList = liveChannelResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                liveChannelList.clear();
                liveChannelList.addAll(resultList);
                fragment.updateLiveChannelList(discoverLiveChannelAdapter);
            }
        }
    }

    private class SerialResponseCallback extends ResponseCallback<Response<Serial>> {

        public SerialResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Serial> serialResponse) {
            if (serialResponse.getStatus() == 1) {
                List<Serial> resultList = serialResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                serialList.clear();
                serialList.addAll(resultList);
                fragment.setupSerialList(discoverSerialAdapter);
                fragment.updateSerialList(discoverSerialAdapter);
            }
        }
    }

    private class SerialEpisodeResponseCallback extends ResponseCallback<Response<SerialEpisode>> {

        public SerialEpisodeResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<SerialEpisode> serialEpisodeResponse) {
            if (serialEpisodeResponse.getStatus() == 1) {
                List<SerialEpisode> resultList = serialEpisodeResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                serialEpisodeList.clear();
                serialEpisodeList.addAll(resultList);
                fragment.setupSerialEpisodeList(discoverSerialEpisodeAdapter);
                fragment.updateSerialEpisodeList(discoverSerialEpisodeAdapter);
            }
        }
    }


    private class AdResponseCallback extends ResponseCallback<Response<Ad>> {

        public AdResponseCallback(Context context) {
            super(context);
        }

        @Override
        public void success(Response<Ad> adResponse) {
            if (adResponse.getStatus() == 1) {
                List<Ad> resultList = adResponse.getResults().getData();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resultList);
                realm.commitTransaction();
                adList.clear();
                adList.addAll(resultList);
                fragment.updateAdList(discoverAdAdapter);
            }
        }
    }
}