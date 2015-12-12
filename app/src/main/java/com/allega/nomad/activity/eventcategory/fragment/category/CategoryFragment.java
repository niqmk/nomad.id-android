package com.allega.nomad.activity.eventcategory.fragment.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.CategoryAdapter;
import com.allega.nomad.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CategoryFragment extends BaseFragment {

    @InjectView(R.id.category_grid_view)
    protected GridView categoryGridView;

    private CategoryFragmentController controller;

    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new CategoryFragmentController(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    void setupCategory(CategoryAdapter homeAdapter) {
        categoryGridView.setAdapter(homeAdapter);
    }
}
