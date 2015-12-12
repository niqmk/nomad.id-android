package com.allega.nomad.activity.home.fragment.event.fragment.detail;

import android.view.View;

import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.BuyVideoEvent;
import com.allega.nomad.dialog.BuyDialog;
import com.allega.nomad.entity.Event;
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
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DetailFragmentController extends BaseFragmentController<DetailFragment> implements VideoDetailViewGroup.VideoDetailListener, PlayerViewGroup.PlayerListener {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private Realm realm = DatabaseManager.getInstance(context);
    private Event event;
    private boolean forcePlay;
    private boolean isWait;

    public DetailFragmentController(DetailFragment fragment, View view) {
        super(fragment, view);

        event = realm.where(Event.class).equalTo(Event.FIELD_ID, fragment.id).findFirst();
        if (event != null)
            fragment.bind(event);

        getEventDetail();
    }

    private void getEventDetail() {
        appRestService.getEvent(fragment.id, new ResponseCallback<Response<Event>>(context) {

            @Override
            public void success(Response<Event> eventResponse) {
                if (eventResponse.getStatus() == 1) {
                    event = eventResponse.getResult();
                    realm.beginTransaction();
                    Event eventRealm = realm.copyToRealmOrUpdate(event);
                    realm.commitTransaction();
                    fragment.setupVideo(eventRealm);
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

    @Override
    public void onFinish() {

    }

    @Override
    public void onPlay() {
        if (event != null)
            appRestService.putEventPlayCount(event.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {

                }

            });
    }

    @Override
    public void onWatchList() {
        if (event != null)
            appRestService.postWatchlist(event.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    event.getLogOnMember().setIsWatchListed(true);
                    realm.commitTransaction();
                    fragment.setupVideo(event);
                }
            });
    }

    @Override
    public synchronized void onLike() {
        if (isWait)
            return;
        isWait = true;
        if (event != null)
            appRestService.postWatchList(event.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    event.getLogOnMember().setIsWatchListed(true);
                    event.setTotalLikes(event.getTotalLikes() + 1);
                    realm.commitTransaction();
                    fragment.updateLike(event);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(event);
                }
            });
    }

    @Override
    public synchronized void onUnLike() {
        if (isWait)
            return;
        isWait = true;
        if (event != null)
            appRestService.postUnWatchList(event.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    event.getLogOnMember().setIsWatchListed(false);
                    event.setTotalLikes(Math.max(0, event.getTotalLikes() - 1));
                    realm.commitTransaction();
                    fragment.updateLike(event);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(event);
                }
            });
    }

    @Override
    public void onBuy() {
        final Price price = event.getPrices().get(0);
        final Long videoId = event.getVideoId();
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
            BuyDialog.show(fragment.getChildFragmentManager(), event.getVideoId(), Type.EVENT);
        }
    }


    public void onEventMainThread(BuyVideoEvent buyVideoEvent) {
        if (buyVideoEvent.getVideoId() == event.getVideoId()) {
            forcePlay = true;
            getEventDetail();
        }
    }
}