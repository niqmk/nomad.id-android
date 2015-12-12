package com.allega.nomad.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 6/2/15.
 */
public class EventPreview extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    @SerializedName("previews")
    private RealmList<Event> events = new RealmList<>();

    public EventPreview() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Event> getEvents() {
        return events;
    }

    public void setEvents(RealmList<Event> events) {
        this.events = events;
    }
}
