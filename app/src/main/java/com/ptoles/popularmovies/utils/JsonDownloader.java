package com.ptoles.popularmovies.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ptoles.popularmovies.model.MoviePoster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;
// https://medium.com/@sanjeevy133/an-idiots-guide-to-android-asynctaskloader-76f8bfb0a0c0
// https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-concepts/
// content/en/Unit%203/71c_asynctask_and_asynctaskloader_md.html
//        Loading data can be memory intensive, and you want the data to be available even if the
//        device configuration changes. For these situations, use loaders, which are a set of classes
//         that facilitate loading data into an activity.

class JsonDownloader extends AsyncTaskLoader<List<MoviePoster>> {

    private static final String TAG = JsonDownloader.class.getSimpleName();


    private String jsonURL;
    String jsonURLIsNull = "";// it is required that this remains an empty string
                              // because the first test in the JsonParser code is
                              // for an empty string
    private static List<MoviePoster> moviePosters;

    private JsonDownloader(Context context) {
        super(context);
        moviePosters = new ArrayList<>();
    }

    private JsonDownloader(Context context, String jsonData) {
        super(context);
        ProgressBar progressBar = new ProgressBar(context);
        moviePosters = new ArrayList<>();
        jsonURL = jsonData;

        if (jsonData.startsWith("Error")){

            Toast.makeText(context, jsonData, Toast.LENGTH_SHORT).show();
        }else{
            // You should now have items in your movie poster array list
           moviePosters = JsonDownloader.DownloadMovieData(jsonURL);
        }

        for (int thisPoster = 0; thisPoster <moviePosters.size(); thisPoster++){

           Log.i(TAG, moviePosters.get(thisPoster).toString());

        }

    }



    @Override
    public List<MoviePoster> loadInBackground() {
        if (jsonURL == null) {
            return null;
        }
        return  DownloadMovieData(jsonURL);
    }



    public static List<MoviePoster> DownloadMovieData(String jsonURL){

        // Read the response of the request and put it in results StringBuffer
       // https://www.youtube.com/watch?v=WN_sw5sZLKY
       // http://www.baeldung.com/java-http-request

        HttpURLConnection connection=null;
       BufferedReader input;
       InputStreamReader inputStream = null;

       try {
           connection = (HttpURLConnection)JsonConnector.Connect(jsonURL);

           if (connection.toString().startsWith("Error")){
               Log.e(TAG, "Problem making the HTTP request.");
           }
           // If the request was successful (response code 200),
           // then read the input stream and parse the response.

           if(connection.getResponseCode()== HTTP_OK) {

                inputStream = new InputStreamReader(connection.getInputStream());
                input = new BufferedReader(inputStream);

                String inputLine;
                StringBuilder output = new StringBuilder();



                while ((inputLine = input.readLine()) != null) {
                    output.append(inputLine).append("\n");
                }
                input.close(); // Close the buffered reader
                inputStream.close(); // close the connection

               // Extract relevant fields from the JSON response and create a list of {@link Movies}
                moviePosters = JsonParser.parseMoviesFromJson(output.toString());

               return moviePosters;
            } else{

                Log.i( TAG, "Error " + connection.getResponseMessage());
                return null;
            }
    } catch (IOException e) {
        e.printStackTrace();
           Log.e(TAG, "Problem retrieving the JSON results.", e);
           return null;
    } finally {
           if (connection != null) {
               connection.disconnect();
           }
           if (inputStream != null) {
               // Closing the input stream could throw an IOException, which is why
               // the makeHttpRequest(URL url) method signature specifies than an IOException
               // could be thrown.
               try {
                   inputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }



    }


}
