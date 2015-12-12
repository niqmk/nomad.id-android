package com.allega.nomad.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by imnotpro on 6/2/15.
 */
public class Images extends RealmObject {

    @Expose
    private String original;
    @Expose
    private String thumb;
    @SerializedName("by_width")
    @Expose
    private String byWidth;
    @SerializedName("by_height")
    @Expose
    private String byHeight;
    @Expose
    private String crop;
    @Expose
    private String def;
    @Expose
    private String hq;
    @Expose
    private String mq;
    @Expose
    private String sd;
    @Expose
    private String max;

    public Images() {
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getByWidth() {
        return byWidth;
    }

    public void setByWidth(String byWidth) {
        this.byWidth = byWidth;
    }

    public String getByHeight() {
        return byHeight;
    }

    public void setByHeight(String byHeight) {
        this.byHeight = byHeight;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getHq() {
        return hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }

    public String getMq() {
        return mq;
    }

    public void setMq(String mq) {
        this.mq = mq;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
