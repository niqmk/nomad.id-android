package com.allega.nomad.activity.edutainmentcategory;

import com.allega.nomad.adapter.EdutainmentCategoryFragmentStateAdapter;
import com.allega.nomad.base.BaseController;

/**
 * Created by imnotpro on 5/30/15.
 */
public class EdutainmentCategoryController extends BaseController<EdutainmentCategoryActivity> {

    private final EdutainmentCategoryFragmentStateAdapter adapter;

    public EdutainmentCategoryController(EdutainmentCategoryActivity activity) {
        super(activity);
        adapter = new EdutainmentCategoryFragmentStateAdapter(activity, activity.getSupportFragmentManager());
        activity.setupViewPager(adapter);
    }
}
