package com.allega.nomad.model;

import android.graphics.drawable.Drawable;

/**
 * Created by imnotpro on 8/1/15.
 */
public class Menu {

    private long id;
    private String name;
    private Drawable drawable;

    public Menu() {
    }

    public Menu(int id, String name, Drawable drawable) {
        this.id = id;
        this.name = name;
        this.drawable = drawable;
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

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
