package com.allega.nomad.activity.home.fragment.movie.child.review;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.review.ReviewFragment;
import com.allega.nomad.adapter.ReviewMovieAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.PostReviewEvent;
import com.allega.nomad.entity.MovieComment;
import com.allega.nomad.model.Type;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.viewgroup.LoadMoreListView;

import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ReviewFragmentController extends BaseFragmentController<com.allega.nomad.activity.home.fragment.movie.child.review.ReviewFragment> implements LoadMoreListView.OnLoadMoreListener {

    private final ReviewMovieAdapter adapter;
    private final RealmResults<MovieComment> movieCommentList;
    private final AppRestService appRestService = AppRestController.getAppRestService();
    private Realm realm = DatabaseManager.getInstance(context);
    private String before = null;

    public ReviewFragmentController(com.allega.nomad.activity.home.fragment.movie.child.review.ReviewFragment fragment, View view) {
        super(fragment, view);
        movieCommentList = realm.where(MovieComment.class).equalTo(MovieComment.FIELD_MOVIE_ID, fragment.id).findAll();
        movieCommentList.sort(MovieComment.FIELD_DATE, false);
        adapter = new ReviewMovieAdapter(context, movieCommentList);
        fragment.setupReviewList(adapter);
        fragment.updateList(adapter);
        getComments();
    }

    private void getComments() {
        appRestService.getMovieComments(fragment.id, before, new ResponseCallback<Response<MovieComment>>(context) {
            @Override
            public void success(Response<MovieComment> movieCommentResponse) {
                if (movieCommentResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(movieCommentResponse.getResults().getData());
                    realm.commitTransaction();
                    before = movieCommentResponse.getResults().getLastToken();
                    fragment.updateList(adapter);
                    fragment.setLoadMore(true);
                } else {
                    before = null;
                    fragment.setLoadMore(false);
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        getComments();
    }

    @OnClick(R.id.review_button)
    protected void onReview() {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(ReviewFragment.newInstance(fragment.id, com.allega.nomad.activity.home.fragment.movie.child.review.ReviewFragment.REQUEST_CODE_REVIEW, Type.MOVIE));
        }
    }

    public void checkData() {
        fragment.updateList(adapter);
    }

    public void onEventMainThread(PostReviewEvent postReviewEvent) {
        checkData();
    }
}