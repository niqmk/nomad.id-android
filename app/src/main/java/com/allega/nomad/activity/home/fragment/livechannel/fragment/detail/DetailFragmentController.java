package com.allega.nomad.activity.home.fragment.livechannel.fragment.detail;

import android.view.View;

import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.BuyVideoEvent;
import com.allega.nomad.dialog.BuyDialog;
import com.allega.nomad.entity.LiveChannel;
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
    private LiveChannel liveChannel;
    private boolean forcePlay;
    private boolean isWait;

    public DetailFragmentController(DetailFragment fragment, View view) {
        super(fragment, view);

        liveChannel = realm.where(LiveChannel.class).equalTo(LiveChannel.FIELD_ID, fragment.id).findFirst();
        if (liveChannel != null)
            fragment.bind(liveChannel);

        getLiveChannelDetail();
    }

    private void getLiveChannelDetail() {
        appRestService.getLiveChannel(fragment.id, new ResponseCallback<Response<LiveChannel>>(context) {

            @Override
            public void success(Response<LiveChannel> liveChannelResponse) {
                if (liveChannelResponse.getStatus() == 1) {
                    liveChannel = liveChannelResponse.getResult();
                    realm.beginTransaction();
                    LiveChannel liveChannelRealm = realm.copyToRealmOrUpdate(liveChannel);
                    realm.commitTransaction();
                    fragment.setupVideo(liveChannelRealm);
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
        if (liveChannel != null)
            appRestService.putLiveChannelPlayCount(liveChannel.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response liveChannel) {

                }
            });
    }

    @Override
    public void onWatchList() {
        if (liveChannel != null)
            appRestService.postWatchlist(liveChannel.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    liveChannel.getLogOnMember().setIsWatchListed(true);
                    realm.commitTransaction();
                    fragment.setupVideo(liveChannel);
                }
            });
    }

    @Override
    public synchronized void onLike() {
        if (isWait)
            return;
        isWait = true;
        if (liveChannel != null)
            appRestService.postWatchList(liveChannel.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    liveChannel.getLogOnMember().setIsWatchListed(true);
                    liveChannel.setTotalLikes(liveChannel.getTotalLikes() + 1);
                    realm.commitTransaction();
                    fragment.updateLike(liveChannel);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(liveChannel);
                }
            });
    }

    @Override
    public synchronized void onUnLike() {
        if (isWait)
            return;
        isWait = true;
        if (liveChannel != null)
            appRestService.postUnWatchList(liveChannel.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    liveChannel.getLogOnMember().setIsWatchListed(false);
                    liveChannel.setTotalLikes(Math.max(0, liveChannel.getTotalLikes() - 1));
                    realm.commitTransaction();
                    fragment.updateLike(liveChannel);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(liveChannel);
                }
            });
    }

    @Override
    public void onBuy() {
        final Price price = liveChannel.getPrices().get(0);
        final Long videoId = liveChannel.getVideoId();
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
            BuyDialog.show(fragment.getChildFragmentManager(), liveChannel.getVideoId(), Type.LIVECHANNEL);
        }
    }

    public void onEventMainThread(BuyVideoEvent buyVideoEvent) {
        if (buyVideoEvent.getVideoId() == liveChannel.getVideoId()) {
            forcePlay = true;
            getLiveChannelDetail();
        }
    }
}