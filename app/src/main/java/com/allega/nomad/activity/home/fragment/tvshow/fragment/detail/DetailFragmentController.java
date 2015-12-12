package com.allega.nomad.activity.home.fragment.tvshow.fragment.detail;

import android.view.View;
import android.widget.ExpandableListView;

import com.allega.nomad.adapter.TvShowEpisodeListAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.BuyVideoEvent;
import com.allega.nomad.bus.event.ChangesTvShowEpisodeEvent;
import com.allega.nomad.dialog.BuyDialog;
import com.allega.nomad.entity.Price;
import com.allega.nomad.entity.TvShow;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.entity.TvShowSeason;
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
    private final TvShowEpisodeListAdapter tvShowEpisodeListAdapter;
    private final List<TvShowSeason> tvShowSeasonList = new ArrayList<>();
    private Realm realm = DatabaseManager.getInstance(context);
    private TvShowEpisode tvShowEpisode;
    private boolean forcePlay;
    private boolean isWait = false;

    public DetailFragmentController(DetailFragment fragment, View view) {
        super(fragment, view);

        tvShowEpisode = realm.where(TvShowEpisode.class).equalTo(TvShow.FIELD_ID, fragment.id).findFirst();
        if (tvShowEpisode != null) {
            fragment.bind(tvShowEpisode);
        }
        tvShowEpisodeListAdapter = new TvShowEpisodeListAdapter(context, tvShowSeasonList);
        fragment.setupEpisodeList(tvShowEpisodeListAdapter);
        getDetail();
    }

    private void getDetail() {
        appRestService.getTvShowEpisode(fragment.id, new ResponseCallback<Response<TvShowEpisode>>(context) {
            @Override
            public void success(Response<TvShowEpisode> episodeResponse) {
                if (episodeResponse.getStatus() == 1) {
                    tvShowEpisode = episodeResponse.getResult();
                    tvShowSeasonList.clear();
                    tvShowSeasonList.addAll(tvShowEpisode.getEpisodes());
                    realm.beginTransaction();
                    TvShowEpisode tvShowEpisodeRealm = realm.copyToRealmOrUpdate(tvShowEpisode);
                    for (TvShowSeason tvShowSeason : tvShowSeasonList) {
                        realm.copyToRealmOrUpdate(tvShowSeason.getData());
                    }
                    realm.commitTransaction();
                    fragment.setupVideo(tvShowEpisodeRealm);
                    fragment.updateEpisodeList(tvShowEpisodeListAdapter);
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
        if (tvShowEpisode != null)
            appRestService.putTvShowPlayCount(tvShowEpisode.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {

                }

            });
    }

    @Override
    public void onWatchList() {
        if (tvShowEpisode != null)
            appRestService.postWatchlist(tvShowEpisode.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    tvShowEpisode.getLogOnMember().setIsWatchListed(true);
                    realm.commitTransaction();
                    fragment.setupVideo(tvShowEpisode);
                }
            });
    }

    @Override
    public synchronized void onLike() {
        if (isWait)
            return;
        isWait = true;
        if (tvShowEpisode != null)
            appRestService.postWatchList(tvShowEpisode.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    tvShowEpisode.getLogOnMember().setIsWatchListed(true);
                    tvShowEpisode.setTotalLikes(tvShowEpisode.getTotalLikes() + 1);
                    realm.commitTransaction();
                    fragment.updateLike(tvShowEpisode);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(tvShowEpisode);
                }
            });
    }

    @Override
    public synchronized void onUnLike() {
        if (isWait)
            return;
        isWait = true;
        if (tvShowEpisode != null)
            appRestService.postUnWatchList(tvShowEpisode.getVideoId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response2) {
                    realm.beginTransaction();
                    tvShowEpisode.getLogOnMember().setIsWatchListed(false);
                    tvShowEpisode.setTotalLikes(Math.max(0, tvShowEpisode.getTotalLikes() - 1));
                    realm.commitTransaction();
                    fragment.updateLike(tvShowEpisode);
                    isWait = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    isWait = false;
                    fragment.updateLike(tvShowEpisode);
                }
            });
    }

    @Override
    public void onBuy() {
        final Price price = tvShowEpisode.getPrices().get(0);
        final Long videoId = tvShowEpisode.getVideoId();
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
            BuyDialog.show(fragment.getChildFragmentManager(), tvShowEpisode.getVideoId(), Type.TVSHOW);
        }
    }


    public void onEventMainThread(BuyVideoEvent buyVideoEvent) {
        if (buyVideoEvent.getVideoId() == tvShowEpisode.getVideoId()) {
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
        TvShowEpisode tvShowEpisode = tvShowSeasonList.get(groupPosition).getData().get(childPosition);
        this.tvShowEpisode = realm.where(TvShowEpisode.class).equalTo(TvShow.FIELD_ID, tvShowEpisode.getId()).findFirst();
        fragment.goToTop();
        fragment.bind(tvShowEpisode);
        fragment.setupVideo(tvShowEpisode);
        Bus.getInstance().post(new ChangesTvShowEpisodeEvent(tvShowEpisode));
        return false;
    }
}