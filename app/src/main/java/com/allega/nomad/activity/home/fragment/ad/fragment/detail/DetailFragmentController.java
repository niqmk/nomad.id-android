package com.allega.nomad.activity.home.fragment.ad.fragment.detail;

import android.view.View;

import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.UpdateMemberEvent;
import com.allega.nomad.entity.Ad;
import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;
import com.allega.nomad.viewgroup.PlayerViewGroup;
import com.allega.nomad.viewgroup.VideoDetailViewGroup;

import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DetailFragmentController extends BaseFragmentController<DetailFragment> implements PlayerViewGroup.PlayerListener, VideoDetailViewGroup.VideoDetailListener {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final PreferenceProvider preferenceProvider = new PreferenceProvider(context);
    private Realm realm = DatabaseManager.getInstance(context);
    private Ad ad;
    private boolean isWait;

    public DetailFragmentController(DetailFragment fragment, View view) {
        super(fragment, view);

        ad = realm.where(Ad.class).equalTo(Ad.FIELD_ID, fragment.id).findFirst();
        if (ad != null)
            fragment.bind(ad);

        getAdDetail();
    }

    private void getAdDetail() {
        appRestService.getAd(fragment.id, new ResponseCallback<Response<Ad>>(context) {

            @Override
            public void success(Response<Ad> adResponse) {
                if (adResponse.getStatus() == 1) {
                    Ad ad = adResponse.getResult();
                    realm.beginTransaction();
                    Ad edutainmentRealm = realm.copyToRealmOrUpdate(ad);
                    realm.commitTransaction();
                    fragment.setupVideo(edutainmentRealm);
                }
            }
        });
    }

    @Override
    public void onFinish() {
        appRestService.postMemberPoint(preferenceProvider.getMember().getId(), "1", ad.getPointReward(), new ResponseCallback<Response>(activity) {
            @Override
            public void success(Response response) {
                if (response.getStatus() == 1) {
                    Member member = preferenceProvider.getMember();
                    member.setTotalPoints(member.getTotalPoints() + ad.getPointReward());
                    preferenceProvider.setMember(member);
                    Bus.getInstance().post(new UpdateMemberEvent());
                }
            }
        });
    }

    @Override
    public void onPlay() {
        if (ad != null)
            appRestService.putAdPlayCount(ad.getId(), new ResponseCallback<Response>(context) {
                @Override
                public void success(Response response) {

                }

            });
    }

    @Override
    public void onWatchList() {

    }

    @Override
    public void onLike() {

    }

    @Override
    public void onUnLike() {

    }

    @Override
    public void onBuy() {
        fragment.forcePlay();
    }
}