package com.allega.nomad.activity.home.fragment.category.child;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;

import org.lucasr.twowayview.ItemClickSupport;

/**
 * Created by imnotpro on 5/30/15.
 */
public abstract class ChildCategoryFragmentController extends BaseFragmentController<ChildCategoryFragment> implements ItemClickSupport.OnItemClickListener {

    protected final AppRestService appRestService = AppRestController.getAppRestService();
    protected String before = null;

    public ChildCategoryFragmentController(ChildCategoryFragment fragment, View view) {
        super(fragment, view);
    }

    protected abstract void setupList(RecyclerView recyclerView);

    protected abstract void getItem();
}