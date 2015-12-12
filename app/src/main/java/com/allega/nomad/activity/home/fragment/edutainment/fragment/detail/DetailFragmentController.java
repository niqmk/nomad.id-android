package com.allega.nomad.activity.home.fragment.edutainment.fragment.detail;

import android.view.View;

import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.BuyVideoEvent;
import com.allega.nomad.dialog.BuyDialog;
import com.allega.nomad.entity.Edutainment;
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
    private Edutainment edutainment;
    private boolean forcePlay;
    private boolean isWait;

    public DetailFragmentController(DetailFragment fragment, View view) {
        super(fragment, view);

        edutainment = realm.where(Edutainment.class).equalTo(Edutainment.FIELD_ID, fragment.id).findFirst();
        if (edutainment != null)
            fragment.bind(edutainment);

        getEdutainmentDetail();
    }

    private void getEdutainmentDetail() {
        appRestService.getEdutainment(fragment.id, new ResponseCallback<Response<Edutainment>>(context) {

            @Override
            public void success(Response<Edutainment> edutainmentResponse) {
                if (edutainmentResponse.getStatus() == 1) {
                    edutainment = edutainmentResponse.getResult();
                    realm.beginTransaction();
                    Edutainment edutainmentRealm = realm.copyToRealmOrUpdate(edutainment);
                    realm.commitTransaction();
                    fragment.setupVideo(edutainmentRealm);
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
        if (edutainment != null)
            appRestService.putEdutainmentPlayCount(edutainment.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response) {

                }

            });
    }

    @Override
    public void onWatchList() {
        if (edutainment != null)
            appRestService.postWatchlist(edutainment.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response) {
                    realm.beginTransaction();
                    edutainment.getLogOnMember().setIsWatchListed(true);
                    realm.commitTransaction();
                    fragment.setupVideo(edutainment);
                }
            });
    }

    @Override
    public synchronized void onLike() {
        if (isWait)
            return;
        isWait = true;
        if (edutainment != null)
            appRestService.postWatchList(edutainment.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response) {
                    realm.beginTransaction();
                    edutainment.getLogOnMember().setIsWatchListed(true);
                    edutainment.setTotalLikes(edutainment.getTotalLikes() + 1);
                    realm.commitTransaction();
                    fragment.updateLike(edutainment);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(edutainment);
                }
            });
    }

    @Override
    public synchronized void onUnLike() {
        if (isWait)
            return;
        isWait = true;
        if (edutainment != null)
            appRestService.postUnWatchList(edutainment.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response) {
                    realm.beginTransaction();
                    edutainment.getLogOnMember().setIsWatchListed(false);
                    edutainment.setTotalLikes(Math.max(0, edutainment.getTotalLikes() - 1));
                    realm.commitTransaction();
                    fragment.updateLike(edutainment);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(edutainment);
                }
            });
    }

    @Override
    public void onBuy() {
        final Price price = edutainment.getPrices().get(0);
        final Long videoId = edutainment.getVideoId();
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
            BuyDialog.show(fragment.getChildFragmentManager(), edutainment.getVideoId(), Type.EDUTAINMENT);
        }
    }


    public void onEventMainThread(BuyVideoEvent buyVideoEvent) {
        if (buyVideoEvent.getVideoId() == edutainment.getVideoId()) {
            forcePlay = true;
            getEdutainmentDetail();
        }
    }
}