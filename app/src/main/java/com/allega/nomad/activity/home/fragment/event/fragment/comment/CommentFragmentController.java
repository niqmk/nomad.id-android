package com.allega.nomad.activity.home.fragment.event.fragment.comment;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.review.ReviewFragment;
import com.allega.nomad.adapter.CommentEventAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.PostReviewEvent;
import com.allega.nomad.entity.EventComment;
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
    private final CommentEventAdapter adapter;
    private final RealmResults<EventComment> eventList;
    private Realm realm = DatabaseManager.getInstance(context);
    private String before = null;

    public CommentFragmentController(CommentFragment fragment, View view) {
        super(fragment, view);
        eventList = realm.where(EventComment.class).equalTo(EventComment.FIELD_EVENT_ID, fragment.id).findAll();
        eventList.sort(EventComment.FIELD_DATE, false);
        adapter = new CommentEventAdapter(context, eventList);
        fragment.setupCommentList(adapter);
        fragment.updateList(adapter);
        getComments();
    }

    private void getComments() {
        appRestService.getEventComments(fragment.id, before, new ResponseCallback<Response<EventComment>>(context) {
            @Override
            public void success(Response<EventComment> eventCommentResponse) {
                if (eventCommentResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(eventCommentResponse.getResults().getData());
                    realm.commitTransaction();
                    before = eventCommentResponse.getResults().getLastToken();
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
            homeActivity.goToFragment(ReviewFragment.newInstance(fragment.id, CommentFragment.REQUEST_CODE_COMMENT, Type.EVENT));
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