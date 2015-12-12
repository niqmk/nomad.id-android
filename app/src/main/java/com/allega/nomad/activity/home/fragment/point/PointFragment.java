package com.allega.nomad.activity.home.fragment.point;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.PointAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.service.analytic.GAConstant;
import com.allega.nomad.viewgroup.PointHeaderViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class PointFragment extends BaseFragment {

    @InjectView(R.id.point_list_view)
    protected ListView pointListView;
    private PointController controller;
    private PointHeaderViewGroup headerViewGroup;

    public static PointFragment newInstance() {
        PointFragment pointFragment = new PointFragment();
        return pointFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_point, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new PointController(this, view);
        headerViewGroup.setListener(controller);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.POINT_SCREEN);
    }

    public void setupPoint(PointAdapter pointAdapter) {
        headerViewGroup = new PointHeaderViewGroup(getActivity());
        pointListView.addHeaderView(headerViewGroup);
        pointListView.setAdapter(pointAdapter);
    }
}
