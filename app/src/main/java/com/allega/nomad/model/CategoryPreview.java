package com.allega.nomad.model;

import com.allega.nomad.entity.Video;

import java.util.List;

/**
 * Created by imnotpro on 6/2/15.
 */
public class CategoryPreview {

    private String name;
    private List<Video> previews;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Video> getPreviews() {
        return previews;
    }

    public void setPreviews(List<Video> previews) {
        this.previews = previews;
    }
}
