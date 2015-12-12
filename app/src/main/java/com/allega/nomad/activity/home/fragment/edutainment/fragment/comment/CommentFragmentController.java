package com.allega.nomad.activity.home.fragment.edutainment.fragment.comment;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.review.ReviewFragment;
import com.allega.nomad.adapter.CommentEdutainmentAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.PostReviewEvent;
import com.allega.nomad.entity.EdutainmentComment;
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
    private final CommentEdutainmentAdapter adapter;
    private final RealmResults<EdutainmentComment> edutainmentCommentList;
    private Realm realm = DatabaseManager.getInstance(context);
    private String before = null;

    public CommentFragmentController(CommentFragment fragment, View view) {
        super(fragment, view);
        edutainmentCommentList = realm.where(EdutainmentComment.class).equalTo(EdutainmentComment.FIELD_EDUTAINMENT_ID, fragment.id).findAll();
        edutainmentCommentList.sort(EdutainmentComment.FIELD_DATE, false);
        adapter = new CommentEdutainmentAdapter(context, edutainmentCommentList);
        fragment.setupCommentList(adapter);
        fragment.updateList(adapter);
        getComments();
    }

    private void getComments() {
        appRestService.getEdutainmentComments(fragment.id, before, new ResponseCallback<Response<EdutainmentComment>>(context) {
            @Override
            public void success(Response<EdutainmentComment> movieCommentResponse) {
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

    @OnClick(R.id.comment_button)
    protected void onComment() {
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.goToFragment(ReviewFragment.newInstance(fragment.id, CommentFragment.REQUEST_CODE_COMMENT, Type.EDUTAINMENT));
        }
    }

    @Override
    public void onLoadMore() {
        getComments();
    }

    public void checkData() {
        fragment.updateList(adapter);
    }

    public void onEventMainThread(PostReviewEvent postReviewEvent) {
        checkData();
    }
}