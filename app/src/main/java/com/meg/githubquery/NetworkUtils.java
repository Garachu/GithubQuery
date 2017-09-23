package com.meg.githubquery;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by root on 9/22/17.
 */

public class NetworkUtils {


    final static String GITHUB_BASE_URL =
            "https://api.github.com/search/repositories";

    final static String PARAM_QUERY = "q";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String PARAM_SORT = "sort";

    final static String sortBy = "stars";

    /*
    * Create a URL object with the appropriate query
    * */
    public static URL buildUrl(String githubSearchQuery) {

        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                .appendQueryParameter(PARAM_QUERY, sortBy)
                .build();

        try {
            URL url =  new URL(builtUri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        //Gets a HTTP connection
        //This doesn't talk to the network yet.
        //It just creates the HttpURLConnection object.
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            //Get InputStream from the HttpURLConnection
            InputStream in = urlConnection.getInputStream();

            //Read the content of the InputStream
            //Scanner is used to tokenize Streams, because it's simple and relatively fast
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }




}
