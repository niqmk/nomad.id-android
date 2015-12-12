package com.allega.nomad.service.rest.app.response;

import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.Movie;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CategoryResponse {

    @Expose
    private List<Movie> movies = new ArrayList<Movie>();
    @Expose
    private List<Edutainment> edutainments = new ArrayList<Edutainment>();
    @Expose
    private List<Event> events = new ArrayList<Event>();

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Edutainment> getEdutainments() {
        return edutainments;
    }

    public void setEdutainments(List<Edutainment> edutainments) {
        this.edutainments = edutainments;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
