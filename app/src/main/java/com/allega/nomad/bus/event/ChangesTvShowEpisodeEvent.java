package com.allega.nomad.bus.event;

import com.allega.nomad.entity.TvShowEpisode;

/**
 * Created by imnotpro on 8/9/15.
 */
public class ChangesTvShowEpisodeEvent {
    private final TvShowEpisode tvShowEpisode;

    public ChangesTvShowEpisodeEvent(TvShowEpisode tvShowEpisode) {
        this.tvShowEpisode = tvShowEpisode;
    }

    public TvShowEpisode getTvShowEpisode() {
        return tvShowEpisode;
    }
}
