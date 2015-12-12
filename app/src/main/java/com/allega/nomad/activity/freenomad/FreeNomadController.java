package com.allega.nomad.activity.freenomad;

import com.allega.nomad.adapter.FreeNomadPointAdapter;
import com.allega.nomad.base.BaseController;
import com.allega.nomad.entity.NomadPoint;

import java.util.ArrayList;

/**
 * Created by imnotpro on 5/30/15.
 */
public class FreeNomadController extends BaseController<FreeNomadActivity> {


    private final FreeNomadPointAdapter adapter;

    public FreeNomadController(FreeNomadActivity activity) {
        super(activity);
        adapter = new FreeNomadPointAdapter(context, new ArrayList<NomadPoint>());
        activity.setupList(adapter);
    }

}
