package com.allega.nomad.activity.home.fragment.livechannel.fragment.comment;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.review.ReviewFragment;
import com.allega.nomad.adapter.CommentLiveChannelAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.PostReviewEvent;
import com.allega.nomad.entity.LiveChannelComment;
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
    private final CommentLiveChannelAdapter adapter;
    private final RealmResults<LiveChannelComment> liveChannelList;
    private Realm realm = DatabaseManager.getInstance(context);
    private String before = null;

    public CommentFragmentController(CommentFragment fragment, View view) {
        super(fragment, view);
        liveChannelList = realm.where(LiveChannelComment.class).equalTo(LiveChannelComment.FIELD_LIVE_CHANNEL_ID, fragment.id).findAll();
        liveChannelList.sort(LiveChannelComment.FIELD_DATE, false);
        adapter = new CommentLiveChannelAdapter(context, liveChannelList);
        fragment.setupCommentList(adapter);
        fragment.updateList(adapter);
        getComments();
    }

    private void getComments() {
        appRestService.getLiveChannelComments(fragment.id, before, new ResponseCallback<Response<LiveChannelComment>>(context) {
            @Override
            public void success(Response<LiveChannelComment> liveChannelCommentResponse) {
                if (liveChannelCommentResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(liveChannelCommentResponse.getResults().getData());
                    realm.commitTransaction();
                    fragment.updateList(adapter);
                    before = liveChannelCommentResponse.getResults().getLastToken();
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
            homeActivity.goToFragment(ReviewFragment.newInstance(fragment.id, CommentFragment.REQUEST_CODE_COMMENT, Type.LIVECHANNEL));
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