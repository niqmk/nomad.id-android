package com.allega.nomad.bus.event;

import com.allega.nomad.entity.SerialEpisode;

/**
 * Created by imnotpro on 8/9/15.
 */
public class ChangesSerialEpisodeEvent {
    private final SerialEpisode serialEpisode;

    public ChangesSerialEpisodeEvent(SerialEpisode serialEpisode) {
        this.serialEpisode = serialEpisode;
    }

    public SerialEpisode ChangesSerialEpisodeEvent() {
        return serialEpisode;
    }
}
