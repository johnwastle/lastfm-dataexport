package com.johnwastle.lastfm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequestExecutor {

    public String getURLContents(String urlString) throws Exception {

        StringBuffer results = new StringBuffer();
        InputStream is = null;

        try {
            URL url = new URL(urlString);
            URLConnection urlc = url.openConnection();
            urlc.setConnectTimeout(60000);
            urlc.setReadTimeout(60000);

            is = urlc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;

            while ((line = reader.readLine()) != null) {
                results.append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally { if (is != null) is.close(); }

        return results.toString();

    }

}

