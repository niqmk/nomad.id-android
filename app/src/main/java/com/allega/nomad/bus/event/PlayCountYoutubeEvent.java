package com.allega.nomad.bus.event;

/**
 * Created by imnotpro on 6/16/15.
 */
public class PlayCountYoutubeEvent {

    private final String videoCode;

    public PlayCountYoutubeEvent(String videoCode) {
        this.videoCode = videoCode;
    }

    public String getVideoCode() {
        return videoCode;
    }
}
