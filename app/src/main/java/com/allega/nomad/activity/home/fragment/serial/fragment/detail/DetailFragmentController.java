package com.allega.nomad.activity.home.fragment.serial.fragment.detail;

import android.view.View;
import android.widget.ExpandableListView;

import com.allega.nomad.adapter.SerialEpisodeListAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.BuyVideoEvent;
import com.allega.nomad.bus.event.ChangesSerialEpisodeEvent;
import com.allega.nomad.dialog.BuyDialog;
import com.allega.nomad.entity.Price;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.SerialSeason;
import com.allega.nomad.model.Type;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.viewgroup.PlayerViewGroup;
import com.allega.nomad.viewgroup.VideoDetailViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DetailFragmentController extends BaseFragmentController<DetailFragment> implements VideoDetailViewGroup.VideoDetailListener, PlayerViewGroup.PlayerListener, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final SerialEpisodeListAdapter serialEpisodeListAdapter;
    private final List<SerialSeason> serialSeasonList = new ArrayList<>();
    private Realm realm = DatabaseManager.getInstance(context);
    private SerialEpisode serialEpisode;
    private boolean forcePlay;
    private boolean isWait = false;

    public DetailFragmentController(DetailFragment fragment, View view) {
        super(fragment, view);

        serialEpisode = realm.where(SerialEpisode.class).equalTo(SerialEpisode.FIELD_ID, fragment.id).findFirst();
        if (serialEpisode != null) {
            fragment.bind(serialEpisode);
        }
        serialEpisodeListAdapter = new SerialEpisodeListAdapter(context, serialSeasonList);
        fragment.setupEpisodeList(serialEpisodeListAdapter);
        getDetail();
    }

    private void getDetail() {
        appRestService.getSerialEpisode(fragment.id, new ResponseCallback<Response<SerialEpisode>>(context) {
            @Override
            public void success(Response<SerialEpisode> episodeResponse) {
                if (episodeResponse.getStatus() == 1) {
                    serialEpisode = episodeResponse.getResult();
                    serialSeasonList.clear();
                    serialSeasonList.addAll(serialEpisode.getEpisodes());
                    realm.beginTransaction();
                    SerialEpisode serialEpisodeRealm = realm.copyToRealmOrUpdate(serialEpisode);
                    for (SerialSeason serialSeason : serialSeasonList) {
                        realm.copyToRealmOrUpdate(serialSeason.getData());
                    }
                    realm.commitTransaction();
                    fragment.setupVideo(serialEpisodeRealm);
                    fragment.updateEpisodeList(serialEpisodeListAdapter);
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
        if (serialEpisode != null)
            appRestService.putSerialPlayCount(serialEpisode.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {

                }

            });
    }

    @Override
    public void onWatchList() {
        if (serialEpisode != null)
            appRestService.postWatchlist(serialEpisode.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    serialEpisode.getLogOnMember().setIsWatchListed(true);
                    realm.commitTransaction();
                    fragment.setupVideo(serialEpisode);
                }
            });
    }

    @Override
    public synchronized void onLike() {
        if (isWait)
            return;
        isWait = true;
        if (serialEpisode != null)
            appRestService.postWatchList(serialEpisode.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    serialEpisode.getLogOnMember().setIsWatchListed(true);
                    serialEpisode.setTotalLikes(serialEpisode.getTotalLikes() + 1);
                    realm.commitTransaction();
                    fragment.updateLike(serialEpisode);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(serialEpisode);
                }
            });
    }

    @Override
    public synchronized void onUnLike() {
        if (isWait)
            return;
        isWait = true;
        if (serialEpisode != null)
            appRestService.postUnWatchList(serialEpisode.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    serialEpisode.getLogOnMember().setIsWatchListed(false);
                    serialEpisode.setTotalLikes(Math.max(0, serialEpisode.getTotalLikes() - 1));
                    realm.commitTransaction();
                    fragment.updateLike(serialEpisode);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(serialEpisode);
                }
            });
    }

    @Override
    public void onBuy() {
        final Price price = serialEpisode.getPrices().get(0);
        final Long videoId = serialEpisode.getVideoId();
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
            BuyDialog.show(fragment.getChildFragmentManager(), serialEpisode.getVideoId(), Type.SERIAL);
        }
    }

    public void onEventMainThread(BuyVideoEvent buyVideoEvent) {
        if (buyVideoEvent.getVideoId() == serialEpisode.getVideoId()) {
            forcePlay = true;
            getDetail();
        }
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        fragment.setListViewHeight(parent, groupPosition);
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        SerialEpisode serialEpisode = serialSeasonList.get(groupPosition).getData().get(childPosition);
        this.serialEpisode = realm.where(SerialEpisode.class).equalTo(SerialEpisode.FIELD_ID, serialEpisode.getId()).findFirst();
        fragment.goToTop();
        fragment.bind(serialEpisode);
        fragment.setupVideo(serialEpisode);
        Bus.getInstance().post(new ChangesSerialEpisodeEvent(serialEpisode));
        return false;
    }
}