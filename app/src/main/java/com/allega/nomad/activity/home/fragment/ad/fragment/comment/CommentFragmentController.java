package com.allega.nomad.activity.home.fragment.ad.fragment.comment;

import android.view.View;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.review.ReviewFragment;
import com.allega.nomad.adapter.CommentAdAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.PostReviewEvent;
import com.allega.nomad.entity.AdComment;
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
    private final CommentAdAdapter adapter;
    private final RealmResults<AdComment> adCommentList;
    private Realm realm = DatabaseManager.getInstance(context);
    private String before = null;

    public CommentFragmentController(CommentFragment fragment, View view) {
        super(fragment, view);
        adCommentList = realm.where(AdComment.class).equalTo(AdComment.FIELD_AD_VIDEO_ID, fragment.id).findAll();
        adCommentList.sort(AdComment.FIELD_DATE, false);
        adapter = new CommentAdAdapter(context, adCommentList);
        fragment.setupCommentList(adapter);
        fragment.updateList(adapter);
        getComments();
    }

    private void getComments() {
        appRestService.getAdComments(fragment.id, before, new ResponseCallback<Response<AdComment>>(context) {
            @Override
            public void success(Response<AdComment> adCommentResponse) {
                if (adCommentResponse.getStatus() == 1) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(adCommentResponse.getResults().getData());
                    realm.commitTransaction();
                    before = adCommentResponse.getResults().getLastToken();
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
            homeActivity.goToFragment(ReviewFragment.newInstance(fragment.id, CommentFragment.REQUEST_CODE_COMMENT, Type.AD));
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