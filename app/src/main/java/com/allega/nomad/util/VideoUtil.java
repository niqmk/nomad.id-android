package com.allega.nomad.util;

import com.allega.nomad.entity.Videos;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by imnotpro on 8/10/15.
 */
public class VideoUtil {

    public static String getVideo(Videos videos) {
        if (!StringUtils.isEmpty(videos.getAuto()))
            return videos.getAuto();
        if (!StringUtils.isEmpty(videos.getV720()))
            return videos.getV720();
        return null;
    }
}
