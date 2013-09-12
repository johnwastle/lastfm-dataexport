package com.johnwastle.lastfm.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class LastFMImage {
    private String text;
    private String size;

    @JsonProperty("#text")
    private String getText() {
        return text;
    }

    private void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
