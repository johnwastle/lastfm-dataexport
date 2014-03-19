package com.johnwastle.lastfm;

import com.johnwastle.lastfm.domain.Track;
import com.johnwastle.lastfm.domain.Tracks;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LastFmExportTracksToRawJsonFile {

    public static String TAB = "\t";
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String DEFAULT_ACCOUNT = "johnwastle";
    public static String API_KEY = "";        // get one of these from http://www.last.fm/api/account/create

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

        (new LastFmExportTracksToRawJsonFile() ).processAccount(account, startPage, endPage);

    }

    public synchronized void processAccount(String accountName, int startPage, int endPage) throws Exception{

        PrintWriter writer = new PrintWriter("tracks.txt", "UTF-8");

        for (int i=startPage; i <= endPage; i++) {

            String url = "http://ws.audioscrobbler.com/2.0/?format=json&method=user.getRecentTracks&limit=200&user=" + accountName + "&api_key=" + API_KEY+ "&page=" + i;
            System.out.println(url);

            String response = httpRequestExecutor.getURLContents(url);
            System.out.println(response);

            //wait(200);    // throttle calls to last.fm API, if they execute too quickly

            ArrayList<String> tracksList = getTrackListFromResponseUsingJacksonRawDataBinding(response);
            System.out.println(tracksList.size());
            System.out.println(tracksList.get(0).toString());

            for (int j=0; j < tracksList.size(); j++) {
                String trackJson = tracksList.get(j);
                writer.println(trackJson);
            }
        }

        writer.close();
    }


    private ArrayList getTrackListFromResponseUsingJacksonRawDataBinding(String response) throws IOException {
        Map<?,?> rootAsMap = mapper.readValue(response, Map.class);
        Map recentTracks = (Map)rootAsMap.get("recenttracks");


        return (ArrayList)recentTracks.get("track");
    }

    protected <T> List<T> mapJsonToObjectList(T typeDef,String json,Class clazz) throws Exception {
        List<T> list;
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(json);
        TypeFactory t = TypeFactory.defaultInstance();
        list = mapper.readValue(json, t.constructCollectionType(ArrayList.class,clazz));

        System.out.println(list);
        System.out.println(list.get(0).getClass());
        return list;
    }

}

