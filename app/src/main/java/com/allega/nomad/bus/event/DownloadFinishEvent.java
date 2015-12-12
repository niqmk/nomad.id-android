package com.allega.nomad.bus.event;

import com.allega.nomad.entity.Videos;

import java.io.File;

/**
 * Created by imnotpro on 6/16/15.
 */
public class DownloadFinishEvent {

    private Videos videos;
    private File file;

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
