package com.johnwastle.lastfm.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

    private Artist artist;
    private String name;
    private int streamable;
    private String mbid;
    private Album album;
    private String url;
    private ArrayList<LastFMImage> image;
    private LastFMDate date = new LastFMDate();

    public LastFMDate getDate() {
        return date;
    }

    public void setDate(LastFMDate date) {
        this.date = date;
    }



    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStreamable() {
        return streamable;
    }

    public void setStreamable(int streamable) {
        this.streamable = streamable;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<LastFMImage> getImage() {
        return image;
    }

    public void setImage(ArrayList<LastFMImage> image) {
        this.image = image;
    }


}






