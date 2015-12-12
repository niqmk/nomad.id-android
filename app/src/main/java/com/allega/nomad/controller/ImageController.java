package com.allega.nomad.controller;

import com.allega.nomad.entity.Images;

/**
 * Created by imnotpro on 6/16/15.
 */
public class ImageController {

    private static final String SIZE = "{size}";
    private static final String WIDTH = "{width}";
    private static final String HEIGHT = "{height}";

    public static String getThumb(Images images) {
        if (images != null && images.getThumb() != null)
            return images.getThumb().replace(SIZE, "600");
        else
            return "";
    }

    public static String getPortait(Images images) {
        if (images != null && images.getThumb() != null)
            return images.getCrop().replace(WIDTH, "405").replace(HEIGHT, "615");
        else
            return "";
    }

    public static String getLandscape(Images images) {
        if (images.getHq() != null && !images.getHq().isEmpty()) {
            return images.getHq();
        }
        return images.getByWidth().replace(WIDTH, "1200");
    }

    public static String getLandscape(Images images, int height) {
        return images.getByHeight().replace(HEIGHT, String.valueOf(height));
    }
}
