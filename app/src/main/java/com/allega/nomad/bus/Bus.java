package com.allega.nomad.bus;

import android.os.Handler;
import android.os.Looper;

import de.greenrobot.event.EventBus;

/**
 * Created by imnotpro on 5/21/15.
 */
public class Bus extends EventBus {
    private static Bus instance = new Bus();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    protected Bus() {

    }

    public static Bus getInstance() {
        return instance;
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Bus.super.post(event);
                }
            });
        }
    }
}
