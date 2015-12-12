package com.allega.nomad.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by imnotpro on 7/5/15.
 */
public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private boolean loadMore = true;
    private OnLoadMoreListener onLoadMoreListener;
    private FooterLoadMoreViewGroup footerLoadMoreViewGroup;

    public LoadMoreListView(Context context) {
        super(context);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnScrollListener(this);
        footerLoadMoreViewGroup = new FooterLoadMoreViewGroup(getContext());
        addFooterView(footerLoadMoreViewGroup);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (onLoadMoreListener != null && loadMore)
            for (int i = 0; i < view.getChildCount(); ++i) {
                View child = view.getChildAt(i);
                if (loadMore && child instanceof FooterLoadMoreViewGroup) {
                    if (onLoadMoreListener != null) {
                        loadMore = false;
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
    }

    public boolean isLoadMore() {
        return loadMore;
    }

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
        if (loadMore)
            setVisibilityFooter(VISIBLE);
        else
            setVisibilityFooter(GONE);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setVisibilityFooter(int visibility) {
        footerLoadMoreViewGroup.setVisibility(visibility);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
