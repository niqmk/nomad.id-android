package com.allega.nomad.bus.event;

/**
 * Created by imnotpro on 5/21/15.
 */
public class DownloadPauseEvent {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
