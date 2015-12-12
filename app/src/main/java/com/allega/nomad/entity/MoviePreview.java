package com.allega.nomad.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imnotpro on 6/2/15.
 */
public class MoviePreview extends RealmObject {

    public static final String FIELD_ID = "id";

    @PrimaryKey
    private long id;
    private String name;
    @SerializedName("previews")
    private RealmList<Movie> movies = new RealmList<>();

    public MoviePreview() {
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

    public RealmList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(RealmList<Movie> movies) {
        this.movies = movies;
    }
}
