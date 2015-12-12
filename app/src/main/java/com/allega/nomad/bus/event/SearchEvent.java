package com.allega.nomad.bus.event;

/**
 * Created by imnotpro on 6/16/15.
 */
public class SearchEvent {

    private String query;

    public SearchEvent(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
