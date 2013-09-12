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

public class LastFmExport {

    public static String TAB = "\t";
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String DEFAULT_ACCOUNT = "johnwastle";
    public static String API_KEY = "3b0c397650d6804f204708e08ed0b26b";

    private static ObjectMapper mapper = new ObjectMapper();
    private static HttpRequestExecutor httpRequestExecutor = new HttpRequestExecutor();

    public static void main(String[] args) throws Exception {

        String account = "";
        int startPage = 1;
        int endPage = 401;

        if (args==null || args.length==0) { account = DEFAULT_ACCOUNT; }
        if (args.length>0) { account = args[0]; }
        if (args.length>1) { startPage = Integer.parseInt( args[1] ); }
        if (args.length>2) { endPage = Integer.parseInt( args[2] ); }

        (new LastFmExport() ).processAccount(account, startPage, endPage);

    }

    public synchronized void processAccount(String accountName, int startPage, int endPage) throws Exception{

        PrintWriter writer = new PrintWriter("tracks.txt", "UTF-8");

        for (int i=startPage; i <= endPage; i++) {

            String url = "http://ws.audioscrobbler.com/2.0/?format=json&method=user.getRecentTracks&limit=200&user=" + accountName + "&api_key=" + API_KEY+ "&page=" + i;
System.out.println(url);

            String response = httpRequestExecutor.getURLContents(url);

            //wait(200);    // throttle calls to last.fm API

            Tracks tracks = mapper.readValue(response, Tracks.class);
            ArrayList<Track> tracksList = tracks.getRecenttracks().getTrack();

            for (int j=0; j < tracksList.size(); j++) {
                String trackOutput = formatTrackOutput(tracksList.get(j));
                //System.out.println(trackOutput);
                writer.println(trackOutput);
            }
        }

        writer.close();
    }

    private String formatTrackOutput(Track track) {
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

    //  example usage:
    //  ArrayList tracks0 = getTrackListFromResponseUsingSimpleDataBinding(response);

    private ArrayList getTrackListFromResponseUsingSimpleDataBinding(String response) throws IOException {
        Map<?,?> rootAsMap = mapper.readValue(response, Map.class);
        Map recentTracks = (Map)rootAsMap.get("recenttracks");
        return (ArrayList)recentTracks.get("track");
    }


}

