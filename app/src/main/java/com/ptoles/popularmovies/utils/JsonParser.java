package com.ptoles.popularmovies.utils;

//import android.support.v7.recyclerview.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

import com.ptoles.popularmovies.BuildConfig;
import com.ptoles.popularmovies.model.MoviePoster;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
// https://medium.com/@sanjeevy133/an-idiots-guide-to-android-asynctaskloader-76f8bfb0a0c0
// https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-concepts/
// content/en/Unit%203/71c_asynctask_and_asynctaskloader_md.html
//        Loading data can be memory intensive, and you want the data to be available even if the
//        device configuration changes. For these situations, use loaders, which are a set of classes
//         that facilitate loading data into an activity.
//
// public static MoviePoster MoviePosterJson(String jsonData) throws JSONException {
// Initialize MoviePoster object from json string in 3 steps:
//      receive the json text as a string
//      assign the string to a json array
//      iterate over the jsonArray and extract values into
//          ArrayLists

//http://www.appsdeveloperblog.com/java-into-json-json-into-java-all-possible-examples/

// https://www.youtube.com/watch?v=wfoqsl5REpo
//  public Boolean Parse (String jsonData){
// Initialize MoviePoster object from jsonData in 3 steps:
//      receive the json text as a string
//      assign the string to a json array
//      iterate over the jsonArray and extract values into
//          ArrayLists
// https://www.tanelikorri.com/tutorial/android/set-variables-in-build-gradle/
// https://stackoverflow.com/questions/50595704/glteximage2d-got-err-pre-0x506-internal-0x1908-format-0x1908-type-0x1401/50605097
public class JsonParser {
    private static final String TAG = JsonParser.class.getSimpleName();
    public static final String apiKey = BuildConfig.ApiKey;


    private static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    public static final String COMPLETE_URL = BASE_URL;

    /*1*/  static String ImageURL = "https://api.themoviedb.org/3/movie/550?";


    /* 2 */ final String SORT_BY_POPULARITY = "sort_by=popularity.desc&";
    /* 3 */ final String SORT_BY_AVERAGE_RATING = "sort_by=vote_average.desc&";
    /* 5 */ final String API_KEY = "api_key=" + apiKey;
    public static final String topRatedUrl = "https://api.themoviedb.org/3/movie/top_rated?api_key="+apiKey;//"?&language=en-US";
//    public static final String topRatedUrl = "https://api.themoviedb.org/3/movie?sort_by=top_rated.desc?api_key="+apiKey+"&language=en-US";
    public static final String mostPopularUrl = "https://api.themoviedb.org/3/movie/popular?api_key="+apiKey;//+"?&language=en-US";
 //   public static final String mostPopularUrl = "https://api.themoviedb.org/3/movie?sort_by=popularity.desc?api_key="+apiKey+"&language=en-US";

    public final static String URL_BASE = "http://api.themoviedb.org/3/discover/movie";
    // URL to query the movie database
    private static final String MOVIE_MOST_POPULAR_URL =
            "https://api.themoviedb.org/3/movie/popular?&api_key=[apiKey]";

    private static final String MOVIE_TOP_RATED_URL =
            "https://api.themoviedb.org/3/movie/top_rated?&api_key=[apiKey]";

    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w185";
    private static final String BACKDROP_URL = "http://image.tmdb.org/t/p/original";

    //final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    //TODO: add your API key Here

    final static String BEFORE_API_KEY = "?api_key=";

    //            URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=...");
    public static List<MoviePoster> parseMoviesFromJson(String jsonData) {

        // ASSUMPTION: receives jsonData directly from JsonDownloader
        // Initialize MoviePoster object from jsonData in 3 steps:
        //      receive the json text as a string
        //      assign the string to a json array
        //      iterate over the jsonArray and extract values into
        //          ArrayLists

        //http://www.appsdeveloperblog.com/java-into-json-json-into-java-all-possible-examples/

        // https://www.youtube.com/watch?v=wfoqsl5REpo

        //http://www.appsdeveloperblog.com/java-into-json-json-into-java-all-possible-examples/


        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }

        // Create an empty moviePosters arraylist used to build our list of MoviePosters
        JSONObject baseResponse = null;

        // The difference is that optString returns the empty string ("")
        // if the key you specify doesn't exist. getString on the other hand throws
        // a JSONException. Use getString if it's an error for the data to be missing,
        // or optString if you're not sure if it will be there.
        try {
            baseResponse = new JSONObject(jsonData);

            List<MoviePoster> moviePosters = new ArrayList<>();
            JSONArray jsonArray = baseResponse.optJSONArray("results");
            // Create an empty ArrayList that we can start adding movies to

            for (int thisPoster = 0; thisPoster < jsonArray.length(); thisPoster++) {


                /* Step 1 */
                Log.d(TAG, "line 87 reached");

                /*Sub steps A-D */
                // A - Since our JSON data begins with a base object, we
                //     need to first initialize a base JSON Object
                JSONObject jsonMoviePoster = jsonArray.optJSONObject(thisPoster);
                JSONArray genreIDsArray = jsonMoviePoster.optJSONArray("genre_ids");


                String backdropPath = jsonMoviePoster.optString("backdrop_path");
                backdropPath = BACKDROP_URL + backdropPath;

                String posterPath = jsonMoviePoster.optString("poster_path");
                posterPath = POSTER_URL+ posterPath;

                String originalTitle = jsonMoviePoster.optString("original_title");
                Double voteAverage = jsonMoviePoster.optDouble("vote_average");

                String overview = jsonMoviePoster.optString("overview");


                // D - Additional details for each MoviePoster
                Double voteCount = jsonMoviePoster.optDouble("vote_count");
                Integer id = jsonMoviePoster.optInt("id");
                Boolean video = jsonMoviePoster.optBoolean("video");
                String title = jsonMoviePoster.optString("title");
                Double popularity = jsonMoviePoster.optDouble("popularity");
                String originalLanguage = jsonMoviePoster.optString("original_language");
                Boolean adult = jsonMoviePoster.optBoolean("adult");

                List<Integer> genreIdsList = new ArrayList<>();
                List<String> resultsArrayList = new ArrayList<>();

                String releaseDate = jsonMoviePoster.optString("release_date");


                /* 2 */
                Log.d(TAG, "line 120 reached");




                /* 3 */
                Log.d(TAG, "line 126 reached");
                for (int i = 0; i < genreIDsArray.length(); i++) {
                    genreIdsList.add(Integer.valueOf(genreIDsArray.optString(i)));
                }
                // for (int j = 0; j < resultsArray.length(); j++) {
                //    resultsArrayList.add(resultsArray.getopString(j));
                //}

                /* 5 */
                Log.d(TAG, "line 135 reached");
                MoviePoster moviePoster = new MoviePoster(voteCount, id.toString(), video,
                        voteAverage.toString(), title, popularity.toString(),
                        posterPath, originalLanguage, originalTitle,
                        genreIdsList, backdropPath, adult, overview, releaseDate);

                moviePosters.add(moviePoster);
                Log.d(TAG, "line 152 reached");



            }// end for loop
            return moviePosters;

        }     // try
        catch (Exception e) {
            Log.d(TAG, e.getMessage());


        }
        // Return the list of movie posters.
        return null;

    } //end method- parseMoviesFromJson
}// end class - JsonParser