package com.johnwastle.lastfm.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

public class RecentTracks {

    private ArrayList<Track> track;
    private LastFMAttribute attr;

    public ArrayList<Track> getTrack() {
        return track;
    }

    public void setTrack(ArrayList<Track> track) {
        this.track = track;
    }

    @JsonProperty("@attr")
    public LastFMAttribute getAttr() {
        return attr;
    }

    public void setAttr(LastFMAttribute attr) {
        this.attr = attr;
    }
}



