package com.johnwastle.lastfm.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class LastFMDate {

    private String text = "";
    private long uts;

    @JsonProperty("#text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getUts() {
        return uts;
    }

    public void setUts(long uts) {
        this.uts = uts;
    }

}
