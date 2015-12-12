package com.allega.nomad.bus.event;

/**
 * Created by imnotpro on 5/21/15.
 */
public class DownloadProgressEvent {

    private String url;
    private long current;
    private long max;

    public DownloadProgressEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DownloadProgressEvent) {
            DownloadProgressEvent other = (DownloadProgressEvent) o;
            return url.equals(other.getUrl());
        }
        return super.equals(o);
    }
}
