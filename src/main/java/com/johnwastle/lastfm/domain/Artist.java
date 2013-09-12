package com.johnwastle.lastfm.domain;


import org.codehaus.jackson.annotate.JsonProperty;

public class Artist {

    private String text;
    private String mbid;


    @JsonProperty("#text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

}









