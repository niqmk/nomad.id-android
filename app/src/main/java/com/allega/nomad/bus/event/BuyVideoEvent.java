package com.allega.nomad.bus.event;

/**
 * Created by imnotpro on 8/12/15.
 */
public class BuyVideoEvent {

    private long videoId;

    public BuyVideoEvent(long videoId) {
        this.videoId = videoId;
    }

    public long getVideoId() {
        return videoId;
    }
}
