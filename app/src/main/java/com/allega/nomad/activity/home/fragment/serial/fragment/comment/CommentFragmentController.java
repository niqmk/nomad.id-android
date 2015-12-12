package com.allega.nomad.activity.home.fragment.serial.fragment.comment;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.review.ReviewFragment;
import com.allega.nomad.adapter.CommentSerialAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.ChangesTvShowEpisodeEvent;
import com.allega.nomad.bus.event.PostReviewEvent;
import com.allega.nomad.entity.SerialEpisodeComment;
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
    private RealmResults<SerialEpisodeComment> serialEpisodeCommentList;
    private CommentSerialAdapter adapter;
    private Realm realm = DatabaseManager.getInstance(context);
    private String before = null;

    public CommentFragmentController(CommentFragment fragment, View view) {
        super(fragment, view);
        prepareCommentEpisode();
    }

    private void prepareCommentEpisode() {
        serialEpisodeCommentList = realm.where(SerialEpisodeComment.class).equalTo(SerialEpisodeComment.FIELD_TV_SHOW_EPISODE_ID, fragment.episodeId).findAll();
        serialEpisodeCommentList.sort(SerialEpisodeComment.FIELD_DATE, false);
        adapter = new CommentSerialAdapter(context, serialEpisodeCommentList);
        fragment.setupCommentList(adapter);
        fragment.updateList(adapter);
        getComments();
    }

    private void getComments() {
        appRestService.getSerialEpisodeComments(fragment.episodeId, before, new ResponseCallback<Response<SerialEpisodeComment>>(context) {
            @Override
            public void success(Response<SerialEpisodeComment> serialCommentResponse) {
                if (serialCommentResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(serialCommentResponse.getResults().getData());
                    realm.commitTransaction();
                    if (!serialCommentResponse.getResults().getLastToken().equals(before))
                        fragment.setLoadMore(true);
                    else
                        fragment.setLoadMore(false);
                    fragment.updateList(adapter);
                    before = serialCommentResponse.getResults().getLastToken();
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
            homeActivity.goToFragment(ReviewFragment.newInstance(fragment.episodeId, CommentFragment.REQUEST_CODE_COMMENT, Type.SERIAL));
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