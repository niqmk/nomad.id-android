package com.allega.nomad.model;

/**
 * Created by imnotpro on 5/30/15.
 */
public class Channel {

    private Type type;
    private String image;
    private boolean isLabel;
    private boolean isLock;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isLabel() {
        return isLabel;
    }

    public void setIsLabel(boolean isLabel) {
        this.isLabel = isLabel;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setIsLock(boolean isLock) {
        this.isLock = isLock;
    }

    public enum Type {MOVIES, EVENTS, EDUTAINMENT, TV_CHANNELS}
}
