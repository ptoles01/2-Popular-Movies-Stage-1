package com.ptoles.popularmovies.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


// https://androidkennel.org/android-networking-tutorial-with-asynctask/
// https://www.youtube.com/watch?v=Fmo3gDMtp8s
// https://www.youtube.com/watch?v=6mT3r8Qn1VY
// http://www.baeldung.com/java-http-request
// https://www.concretepage.com/android/android-asynctaskloader-example-with-listview-and-baseadapter
// https://www.codeproject.com/Tips/773464/How-to-Add-an-AsyncTask-to-an-Android-Activity
// https://www.concretepage.com/android/android-asynctaskloader-example-with-listview-and-baseadapter

public class JsonConnector {
    private static final String TAG = JsonConnector.class.getName();

// http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=75fa203cc819faba4f627132ce414b9c
// Make an HTTP request to the given URL and return a String as the response.

    /*1*/  static String ImageURL = "https://api.themoviedb.org/3/movie/550?";



    public static Object Connect(String jsonURL) {

        StringBuffer results = null;
        HttpURLConnection connection = null;
        try {
            // Create a connection to a given URL
            // Query the URL with that connection
            // Parse the data from the JSON - use a fixed reference at first, then get the live search to work
            //
            URL url = createUrlFromString(jsonURL);


            //URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=75fa203cc819faba4f627132ce414b9c");

            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                connection.setRequestMethod("GET");

                // Configure Timeouts
                connection.setReadTimeout(10000); //milliseconds
                connection.setConnectTimeout(15000); //milliseconds
               // connection.setDoInput(true);


                return connection;

            } else{

                return "Connection error using " + jsonURL;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "URL Error " + e.getMessage();

        } catch (IOException e) {
            e.printStackTrace();
            return "I/O Error " + e.getMessage();

        }

    }


    // Returns new URL object from the given string.
    private static URL createUrlFromString(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }
        return url;
    }

}