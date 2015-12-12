package com.allega.nomad.activity.splash;

import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.intro.IntroActivity;
import com.allega.nomad.base.BaseController;
import com.allega.nomad.entity.AdCategory;
import com.allega.nomad.entity.EdutainmentCategory;
import com.allega.nomad.entity.EventCategory;
import com.allega.nomad.entity.LiveChannelCategory;
import com.allega.nomad.entity.MovieCategory;
import com.allega.nomad.entity.SerialCategory;
import com.allega.nomad.entity.TvShowCategory;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;

import io.realm.Realm;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SplashController extends BaseController<SplashActivity> {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final Realm realm;
    private int numRest = 0;
    private PreferenceProvider preferenceProvider;

    public SplashController(SplashActivity activity) {
        super(activity);
        preferenceProvider = new PreferenceProvider(context);
        realm = DatabaseManager.getInstance(context);
    }

    public void getCategory() {
        numRest = 7;
        appRestService.getMovieGenre(new ResponseCallback<Response<MovieCategory>>(context) {
            @Override
            public void success(Response<MovieCategory> movieGenreResponse) {
                if (movieGenreResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(movieGenreResponse.getResults().getData());
                    realm.commitTransaction();
                }
                checkNext();
            }

            @Override
            public void failure(RetrofitError error) {
                checkNext();
            }
        });
        appRestService.getAdCategory(new ResponseCallback<Response<AdCategory>>(context) {
            @Override
            public void success(Response<AdCategory> adCategoryResponse) {
                if (adCategoryResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(adCategoryResponse.getResults().getData());
                    realm.commitTransaction();
                }
                checkNext();
            }

            @Override
            public void failure(RetrofitError error) {
                checkNext();
            }
        });
        appRestService.getEdutainmentCategory(new ResponseCallback<Response<EdutainmentCategory>>(context) {
            @Override
            public void success(Response<EdutainmentCategory> edutainmentCategoryResponse) {
                if (edutainmentCategoryResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(edutainmentCategoryResponse.getResults().getData());
                    realm.commitTransaction();
                }
                checkNext();
            }

            @Override
            public void failure(RetrofitError error) {
                checkNext();
            }
        });
        appRestService.getTvShowCategory(new ResponseCallback<Response<TvShowCategory>>(context) {
            @Override
            public void success(Response<TvShowCategory> tvShowCategoryResponse) {
                if (tvShowCategoryResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(tvShowCategoryResponse.getResults().getData());
                    realm.commitTransaction();
                }
                checkNext();
            }

            @Override
            public void failure(RetrofitError error) {
                checkNext();
            }
        });
        appRestService.getLiveChannelCategory(new ResponseCallback<Response<LiveChannelCategory>>(context) {
            @Override
            public void success(Response<LiveChannelCategory> liveChannelCategoryResponse) {
                if (liveChannelCategoryResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(liveChannelCategoryResponse.getResults().getData());
                    realm.commitTransaction();
                }
                checkNext();
            }

            @Override
            public void failure(RetrofitError error) {
                checkNext();
            }
        });
        appRestService.getEventCategory(new ResponseCallback<Response<EventCategory>>(context) {
            @Override
            public void success(Response<EventCategory> eventCategoryResponse) {
                if (eventCategoryResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(eventCategoryResponse.getResults().getData());
                    realm.commitTransaction();
                }
                checkNext();
            }

            @Override
            public void failure(RetrofitError error) {
                checkNext();
            }
        });
        appRestService.getSerialCategory(new ResponseCallback<Response<SerialCategory>>(context) {
            @Override
            public void success(Response<SerialCategory> serialCategoryResponse) {
                if (serialCategoryResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(serialCategoryResponse.getResults().getData());
                    realm.commitTransaction();
                }
                checkNext();
            }

            @Override
            public void failure(RetrofitError error) {
                checkNext();
            }
        });
    }

    private void checkNext() {
        numRest--;
        if (numRest == 0) {
            next();
        }
    }

    public void next() {
        if (preferenceProvider.getMember() != null) {
            HomeActivity.startActivity(context);
        } else {
            IntroActivity.startActivity(context);
        }
    }
}
