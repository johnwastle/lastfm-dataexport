package com.johnwastle.lastfm;

import com.johnwastle.lastfm.domain.Track;
import com.johnwastle.lastfm.domain.Tracks;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class LastFmExportTracksToTabDelimitedFile {

    public static String TAB = "\t";
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String DEFAULT_ACCOUNT = "johnwastle";
    public static String API_KEY = "3b0c397650d6804f204708e08ed0b26b";        // get one of these from http://www.last.fm/api/account/create

    private static ObjectMapper mapper = new ObjectMapper();
    private static HttpRequestExecutor httpRequestExecutor = new HttpRequestExecutor();

    public static void main(String[] args) throws Exception {

        String account = "";
        int startPage = 1;
        int endPage = 1; //450

        if (args==null || args.length==0) { account = DEFAULT_ACCOUNT; }
        if (args.length>0) { account = args[0]; }
        if (args.length>1) { startPage = Integer.parseInt( args[1] ); }
        if (args.length>2) { endPage = Integer.parseInt( args[2] ); }

        (new LastFmExportTracksToTabDelimitedFile() ).processAccount(account, startPage, endPage);

    }

    public synchronized void processAccount(String accountName, int startPage, int endPage) throws Exception{

        PrintWriter writer = new PrintWriter("tracks.tsv", "UTF-8");

        for (int i=startPage; i <= endPage; i++) {

            String url = "http://ws.audioscrobbler.com/2.0/?format=json&method=user.getRecentTracks&limit=200&user=" + accountName + "&api_key=" + API_KEY+ "&page=" + i;
            System.out.println(url);

            String response = httpRequestExecutor.getURLContents(url);

            //wait(200);    // throttle calls to last.fm API, if they execute too quickly

            // Jackson "Raw" Data Binding:
            // http://wiki.fasterxml.com/JacksonInFiveMinutes#A.22Raw.22_Data_Binding_Example
            // could use
            // ArrayList tracks0 = getTrackListFromResponseUsingJacksonRawDataBinding(response);

            // Full Data Binding (POJO):
            // http://wiki.fasterxml.com/JacksonInFiveMinutes#Full_Data_Binding_.28POJO.29_Example
            Tracks tracks = mapper.readValue(response, Tracks.class);
            ArrayList<Track> tracksList = tracks.getRecenttracks().getTrack();

            for (int j=0; j < tracksList.size(); j++) {
                String trackAsTabDelimited = formatTrackToTabDelimitedString(tracksList.get(j));
                writer.println(trackAsTabDelimited);
            }
        }

        writer.close();
    }

    private String formatTrackToTabDelimitedString(Track track) {
        StringBuffer trackDetail = new StringBuffer();
        trackDetail.append(track.getArtist().getText());
        trackDetail.append(TAB);
        trackDetail.append(track.getName());
        trackDetail.append(TAB);
        trackDetail.append(track.getAlbum().getText());
        trackDetail.append(TAB);
        trackDetail.append(DATE_FORMAT.format(new Date(track.getDate().getUts()*1000)));
        return trackDetail.toString();
    }


    private ArrayList getTrackListFromResponseUsingJacksonRawDataBinding(String response) throws IOException {
        Map<?,?> rootAsMap = mapper.readValue(response, Map.class);
        Map recentTracks = (Map)rootAsMap.get("recenttracks");
        return (ArrayList)recentTracks.get("track");
    }


}

