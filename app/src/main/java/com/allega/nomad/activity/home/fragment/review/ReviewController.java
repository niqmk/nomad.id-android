package com.allega.nomad.activity.home.fragment.review;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.PostReviewEvent;
import com.allega.nomad.entity.AdComment;
import com.allega.nomad.entity.EdutainmentComment;
import com.allega.nomad.entity.EventComment;
import com.allega.nomad.entity.LiveChannelComment;
import com.allega.nomad.entity.MovieComment;
import com.allega.nomad.entity.SerialEpisodeComment;
import com.allega.nomad.entity.TvShowEpisodeComment;
import com.allega.nomad.model.Type;
import com.allega.nomad.service.analytic.GAConstant;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;

import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ReviewController extends BaseFragmentController<ReviewFragment> {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final int MAX_REVIEW;
    private final PreferenceProvider preferenceProvider = new PreferenceProvider(context);
    private Realm realm;

    public ReviewController(ReviewFragment fragment, View view) {
        super(fragment, view);
        MAX_REVIEW = Integer.valueOf(fragment.getResources().getString(R.string.max_review));
        realm = DatabaseManager.getInstance(context);
        if (fragment.type.equals(Type.MOVIE)) {
            MovieComment movieComment = realm.where(MovieComment.class)
                    .equalTo(MovieComment.FIELD_MOVIE_ID, fragment.id)
                    .equalTo(MovieComment.FIELD_MEMBER_ID, preferenceProvider.getMember().getId()).findFirst();
            if (movieComment != null) {
                fragment.fillReview(movieComment);
            }
        }
    }

    @OnClick(R.id.send_button)
    protected void publishComment() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String comment = fragment.getComment();
        if (!comment.isEmpty()) {
            switch (fragment.type) {
                case MOVIE:
                    sendGAEvent(GAConstant.CATEGORY_COMMENT, GAConstant.MOVIE);
                    appRestService.postMovieComment(fragment.id, (int) fragment.getRating(), comment, new ResponseCallback<Response<MovieComment>>(context) {
                        @Override
                        public void success(Response<MovieComment> response) {
                            if (response.getStatus() == 1) {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(response.getResult());
                                realm.commitTransaction();
                                finish();
                            }
                        }
                    });
                    break;
                case EVENT:
                    sendGAEvent(GAConstant.CATEGORY_COMMENT, GAConstant.EVENT);
                    appRestService.postEventComment(fragment.id, comment, new ResponseCallback<Response<EventComment>>(context) {
                        @Override
                        public void success(Response<EventComment> response) {
                            if (response.getStatus() == 1) {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(response.getResult());
                                realm.commitTransaction();
                                finish();
                            }
                        }
                    });
                    break;
                case EDUTAINMENT:
                    sendGAEvent(GAConstant.CATEGORY_COMMENT, GAConstant.EDUTAINMENT);
                    appRestService.postEdutainmentComment(fragment.id, comment, new ResponseCallback<Response<EdutainmentComment>>(context) {
                        @Override
                        public void success(Response<EdutainmentComment> response) {
                            if (response.getStatus() == 1) {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(response.getResult());
                                realm.commitTransaction();
                                finish();
                            }
                        }
                    });
                    break;
                case TVSHOW:
                    sendGAEvent(GAConstant.CATEGORY_COMMENT, GAConstant.TV_SHOW);
                    appRestService.postTvShowComment(fragment.id, comment, new ResponseCallback<Response<TvShowEpisodeComment>>(context) {
                        @Override
                        public void success(Response<TvShowEpisodeComment> response) {
                            if (response.getStatus() == 1) {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(response.getResult());
                                realm.commitTransaction();
                                finish();
                            }
                        }
                    });
                    break;
                case LIVECHANNEL:
                    sendGAEvent(GAConstant.CATEGORY_COMMENT, GAConstant.LIVE_CHANNEL);
                    appRestService.postLiveChannelComment(fragment.id, comment, new ResponseCallback<Response<LiveChannelComment>>(context) {
                        @Override
                        public void success(Response<LiveChannelComment> response) {
                            if (response.getStatus() == 1) {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(response.getResult());
                                realm.commitTransaction();
                                finish();
                            }
                        }
                    });
                    break;
                case AD:
                    sendGAEvent(GAConstant.CATEGORY_COMMENT, GAConstant.AD);
                    appRestService.postAdComment(fragment.id, comment, new ResponseCallback<Response<AdComment>>(context) {
                        @Override
                        public void success(Response<AdComment> response) {
                            if (response.getStatus() == 1) {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(response.getResult());
                                realm.commitTransaction();
                                finish();
                            }
                        }
                    });
                    break;
                case SERIAL:
                    sendGAEvent(GAConstant.CATEGORY_COMMENT, GAConstant.SERIAL);
                    appRestService.postSerialComment(fragment.id, comment, new ResponseCallback<Response<SerialEpisodeComment>>(context) {
                        @Override
                        public void success(Response<SerialEpisodeComment> response) {
                            if (response.getStatus() == 1) {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(response.getResult());
                                realm.commitTransaction();
                                finish();
                            }
                        }
                    });
                    break;
            }
        }
    }

    @OnTextChanged(R.id.review_edit_text)
    void onTextChanged(CharSequence text) {
        fragment.updateCounter(MAX_REVIEW - text.length());
    }

    private void finish() {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.backFragment();
            Bus.getInstance().post(new PostReviewEvent());
        }
    }
}
