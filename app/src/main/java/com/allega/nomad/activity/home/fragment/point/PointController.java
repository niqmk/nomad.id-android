package com.allega.nomad.activity.home.fragment.point;

import android.view.View;

import com.allega.nomad.adapter.PointAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.model.Point;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.viewgroup.PointHeaderViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class PointController extends BaseFragmentController<PointFragment> implements PointHeaderViewGroup.PointHeaderListener {

    private final AppRestService appRestService = AppRestController.getAppRestService();
    private final PointAdapter pointAdapter;
    private final List<Point> pointList = new ArrayList<>();
    private boolean isBuy = true;

    public PointController(PointFragment activity, View view) {
        super(activity, view);
        pointAdapter = new PointAdapter(context, pointList);
        activity.setupPoint(pointAdapter);

        getItem();
    }

    private void getItem() {
        pointList.clear();
        pointAdapter.setType(isBuy);
        pointAdapter.notifyDataSetChanged();
        if (isBuy) {
            appRestService.getPointPaid(new ResponseCallback<Response<Point>>(context) {
                @Override
                public void success(Response<Point> pointResponse) {
                    pointList.addAll(pointResponse.getResults().getData());
                    pointAdapter.notifyDataSetChanged();
                }
            });
        } else {
            appRestService.getPointFree(new ResponseCallback<Response<Point>>(context) {
                @Override
                public void success(Response<Point> pointResponse) {
                    pointList.addAll(pointResponse.getResults().getData());
                    pointAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onBuy() {
        if (!isBuy) {
            isBuy = true;
            getItem();
        }
    }

    @Override
    public void onFree() {
        if (isBuy) {
            isBuy = false;
            getItem();
        }
    }
}
