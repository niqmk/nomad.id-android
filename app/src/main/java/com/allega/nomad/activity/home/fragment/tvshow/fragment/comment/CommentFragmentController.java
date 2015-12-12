package com.allega.nomad.activity.home.fragment.tvshow.fragment.comment;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.review.ReviewFragment;
import com.allega.nomad.adapter.CommentTvShowAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.ChangesTvShowEpisodeEvent;
import com.allega.nomad.bus.event.PostReviewEvent;
import com.allega.nomad.entity.TvShowEpisodeComment;
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
public class CommentFragmentController extends BaseFragmentController<CommentFragment> implements LoadMoreListView.OnLoadMoreListener {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private RealmResults<TvShowEpisodeComment> tvShowEpisodeCommentList;
    private CommentTvShowAdapter adapter;
    private Realm realm = DatabaseManager.getInstance(context);
    private String before = null;

    public CommentFragmentController(CommentFragment fragment, View view) {
        super(fragment, view);
        prepareCommentEpisode();
    }

    private void prepareCommentEpisode() {
        tvShowEpisodeCommentList = realm.where(TvShowEpisodeComment.class).equalTo(TvShowEpisodeComment.FIELD_TV_SHOW_EPISODE_ID, fragment.episodeId).findAll();
        tvShowEpisodeCommentList.sort(TvShowEpisodeComment.FIELD_DATE, false);
        adapter = new CommentTvShowAdapter(context, tvShowEpisodeCommentList);
        fragment.setupCommentList(adapter);
        fragment.updateList(adapter);
        getComments();
    }

    private void getComments() {
        appRestService.getTvShowEpisodeComments(fragment.episodeId, before, new ResponseCallback<Response<TvShowEpisodeComment>>(context) {
            @Override
            public void success(Response<TvShowEpisodeComment> tvShowCommentResponse) {
                if (tvShowCommentResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(tvShowCommentResponse.getResults().getData());
                    realm.commitTransaction();
                    if (!tvShowCommentResponse.getResults().getLastToken().equals(before))
                        fragment.setLoadMore(true);
                    else
                        fragment.setLoadMore(false);
                    fragment.updateList(adapter);
                    before = tvShowCommentResponse.getResults().getLastToken();
                } else {
                    before = null;
                    fragment.setLoadMore(false);
                }
            }
        });
    }

    @OnClick(R.id.comment_button)
    protected void onComment() {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(ReviewFragment.newInstance(fragment.episodeId, CommentFragment.REQUEST_CODE_COMMENT, Type.TVSHOW));
        }
    }

    @Override
    public void onLoadMore() {
        getComments();
    }

    public void checkData() {
        fragment.updateList(adapter);
    }

    public void onEventMainThread(ChangesTvShowEpisodeEvent changesTvShowEpisodeEvent) {
        fragment.episodeId = changesTvShowEpisodeEvent.getTvShowEpisode().getId();
        prepareCommentEpisode();
    }

    public void onEventMainThread(PostReviewEvent postReviewEvent) {
        checkData();
    }
}