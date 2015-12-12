package com.allega.nomad.activity.home.fragment.movie.child.detail;

import android.view.View;

import com.allega.nomad.adapter.ClipAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.BuyVideoEvent;
import com.allega.nomad.dialog.BuyDialog;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.MovieClip;
import com.allega.nomad.entity.Price;
import com.allega.nomad.model.Type;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.viewgroup.PlayerViewGroup;
import com.allega.nomad.viewgroup.VideoDetailViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DetailFragmentController extends BaseFragmentController<DetailFragment> implements VideoDetailViewGroup.VideoDetailListener, PlayerViewGroup.PlayerListener {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final RealmResults<MovieClip> movieClipList;
    private final ClipAdapter adapter;
    private Realm realm = DatabaseManager.getInstance(context);
    private Movie movie;
    private boolean forcePlay;
    private boolean isWait;

    public DetailFragmentController(DetailFragment fragment, View view) {
        super(fragment, view);

        movie = realm.where(Movie.class).equalTo(Movie.FIELD_ID, fragment.id).findFirst();
        if (movie != null)
            fragment.bind(movie);

        movieClipList = realm.where(MovieClip.class).equalTo(MovieClip.FIELD_MOVIE_ID, fragment.id).findAll();
        adapter = new ClipAdapter(context, movieClipList);
        fragment.setupTrailer(adapter);

        getMovieDetail();
        getClips();
    }

    private void getMovieDetail() {
        appRestService.getMovie(fragment.id, new ResponseCallback<Response<Movie>>(context) {
            @Override
            public void success(Response<Movie> movieResponse) {
                if (movieResponse.getStatus() == 1) {
                    movie = movieResponse.getResult();
                    realm.beginTransaction();
                    Movie movieRealm = realm.copyToRealmOrUpdate(movie);
                    realm.commitTransaction();
                    fragment.bind(movieRealm);
                    if (forcePlay) {
                        forcePlay = false;
                        fragment.forcePlay();
                    }
                    activity.hideProgress();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                activity.hideProgress();
            }
        });
    }

    private void getClips() {
        appRestService.getMovieClip(fragment.id, new ResponseCallback<Response<MovieClip>>(context) {
            @Override
            public void success(Response<MovieClip> movieClipResponse) {
                if (movieClipResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(movieClipResponse.getResults().getData());
                    realm.commitTransaction();
                    if (movieClipResponse.getResults().getData().size() > 0) {
                        if (movieClipResponse.getResults().getData().size() == 1)
                            fragment.showTrailer(movieClipResponse.getResults().getData().get(0));
                        else {
                            fragment.showTrailer();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onPlay() {
        if (movie != null)
            appRestService.putMoviePlayCount(movie.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {

                }

            });
    }

    @Override
    public void onWatchList() {
        if (movie != null)
            appRestService.postWatchlist(movie.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    movie.getLogOnMember().setIsWatchListed(true);
                    realm.commitTransaction();
                    fragment.setupVideo(movie);
                }
            });
    }

    @Override
    public synchronized void onLike() {
        if (isWait)
            return;
        isWait = true;
        if (movie != null)
            appRestService.postWatchList(movie.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    movie.getLogOnMember().setIsWatchListed(true);
                    movie.setTotalLikes(movie.getTotalLikes() + 1);
                    realm.commitTransaction();
                    fragment.updateLike(movie);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(movie);
                }
            });
    }

    @Override
    public synchronized void onUnLike() {
        if (isWait)
            return;
        isWait = true;
        if (movie != null)
            appRestService.postUnWatchList(movie.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    movie.getLogOnMember().setIsWatchListed(false);
                    movie.setTotalLikes(Math.max(0, movie.getTotalLikes() - 1));
                    realm.commitTransaction();
                    fragment.updateLike(movie);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(movie);
                }
            });
    }

    @Override
    public void onBuy() {
        final Price price = movie.getPrices().get(0);
        final Long videoId = movie.getVideoId();
        String priceValue = price.getPrice();
        if (priceValue.equals("0")) {
            activity.showProgress();
            appRestService.postVideoPurchase(videoId, price.getPrice(), price.getExpiryDays(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response responseServer) {
                    if (responseServer.getStatus() == 1) {
                        Bus.getInstance().post(new BuyVideoEvent(videoId));
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    activity.hideProgress();
                }
            });
        } else {
            BuyDialog.show(fragment.getChildFragmentManager(), movie.getVideoId(), Type.MOVIE);
        }
    }

    public void onEventMainThread(BuyVideoEvent buyVideoEvent) {
        if (buyVideoEvent.getVideoId() == movie.getVideoId()) {
            forcePlay = true;
            getMovieDetail();
        }
    }
}