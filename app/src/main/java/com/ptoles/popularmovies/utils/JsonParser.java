package com.ptoles.popularmovies.utils;

//import android.support.v7.recyclerview.BuildConfig;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ptoles.popularmovies.BuildConfig;
import com.ptoles.popularmovies.model.MoviePoster;
import com.ptoles.popularmovies.model.MovieReview;

import org.json.JSONArray;
import org.json.JSONObject;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.ptoles.popularmovies.utils.CONSTANTS.*;

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

    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String SIZE_PARAM = "w185";

    private static final String PARAM_API_KEY="api_key";
    private static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";

    private static final String MOVIEDB_BASE_URL="https://api.themoviedb.org/3/movie";

    public final static String URL_BASE = "http://api.themoviedb.org/3/discover/movie";
    // URL to query the movie database

    private static final String VIDEOS_PATH   = "videos";
    private static final String REVIEWS_PATH  = "reviews";


    /* 5 */ final String API_KEY = "api_key=" + apiKey;

    public static final String topRatedUrl = "https://api.themoviedb.org/3/movie/top_rated?api_key="+apiKey;//"?&language=en-US";
//    public static final String topRatedUrl = "https://api.themoviedb.org/3/movie?sort_by=top_rated.desc?api_key="+apiKey+"&language=en-US";

    public static final String mostPopularUrl = "https://api.themoviedb.org/3/movie/popular?api_key="+apiKey;//+"?&language=en-US";
 //   public static final String mostPopularUrl = "https://api.themoviedb.org/3/movie?sort_by=popularity.desc?api_key="+apiKey+"&language=en-US";

    public static final String favoritesUrl = "https://api.themoviedb.org/3/movie/popular?api_key="+apiKey;//+"?&language=en-US";
    //   public static final String favoritesUrl = "https://api.themoviedb.org/3/movie?sort_by=popularity.desc?api_key="+apiKey+"&language=en-US";


    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w185";
    private static final String BACKDROP_URL = "http://image.tmdb.org/t/p/original";

    //final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    //TODO: add your API key Here


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
            if (baseResponse.has("status_code")) {
                int errorCode = baseResponse.getInt("status_code");
                Log.e(TAG, "parse json movies error code: " + String.valueOf(errorCode));
            }
            List<MoviePoster> moviePosters = new ArrayList<>();
            JSONArray jsonArray = baseResponse.optJSONArray(MOVIE_POSTER_RESULTS_KEY);
            // Create an empty ArrayList that we can start adding movies to

            for (int thisPoster = 0; thisPoster < jsonArray.length(); thisPoster++) {


                /* Step 1 */
                Log.d(TAG, "line 124 reached");

                /*Sub steps A-D */
                // A - Since our JSON data begins with a base object, we
                //     need to first initialize a base JSON Object
                JSONObject jsonMoviePoster = jsonArray.optJSONObject(thisPoster);
                JSONArray genreIDsArray = jsonMoviePoster.optJSONArray(GENRE_IDS_KEY);



                String backdropPath = jsonMoviePoster.optString(BACKDROP_PATH_KEY);
                backdropPath = BACKDROP_URL + backdropPath;

                String posterPath = jsonMoviePoster.optString(POSTER_PATH_KEY);
                posterPath = POSTER_URL+ posterPath;

                String originalTitle = jsonMoviePoster.optString(ORIGINAL_TITLE_KEY);
                Double voteAverage = jsonMoviePoster.optDouble(VOTE_AVERAGE_KEY);

                String overview = jsonMoviePoster.optString(OVERVIEW_KEY);


                // D - Additional details for each MoviePoster
                Double voteCount = jsonMoviePoster.optDouble(VOTE_COUNT_KEY);
                Integer movieId = jsonMoviePoster.optInt(MOVIE_POSTER_ID_KEY);
                Boolean video = jsonMoviePoster.optBoolean(HAS_VIDEO_KEY);
                String title = jsonMoviePoster.optString(MOVIE_POSTER_TITLE_KEY);
                Double popularity = jsonMoviePoster.optDouble(POPULARITY_KEY);
                String originalLanguage = jsonMoviePoster.optString(ORIGINAL_LANGUAGE_KEY);
                Boolean adult = jsonMoviePoster.optBoolean(IS_ADULT_KEY);

                List<Integer> genreIdsList = new ArrayList<>();
                List<String> resultsArrayList = new ArrayList<>();

                String releaseDate = jsonMoviePoster.optString(RELEASE_DATE_KEY);


                /* 2 */
                Log.d(TAG, "line 161 reached");




                /* 3 */
                Log.d(TAG, "line 167 reached");
                for (int i = 0; i < genreIDsArray.length(); i++) {
                    genreIdsList.add(Integer.valueOf(genreIDsArray.optString(i)));
                }
                // for (int j = 0; j < resultsArray.length(); j++) {
                //    resultsArrayList.add(resultsArray.getopString(j));
                //}

                /* 5 */
                Log.d(TAG, "line 176 reached");
                MoviePoster moviePoster = new MoviePoster(voteCount, movieId.toString(), video,
                        voteAverage.toString(), title, popularity.toString(),
                        posterPath, originalLanguage, originalTitle,
                        genreIdsList, backdropPath, adult, overview, releaseDate);

                moviePosters.add(moviePoster);
                Log.d(TAG, "line 183 reached");



            }// end for loop
            return moviePosters;

        }     // try
        catch (Exception e) {
            Log.d(TAG, e.getMessage());


        }
        // Return the list of movie posters.
        return null;

    } //end method- parseMoviesFromJson



    public static List<MovieReview> parseReviewsFromJson(String jsonData) {

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

        // Create an empty movieReviews arraylist used to build our list of MovieReviews
        JSONObject baseResponse = null;

        // The difference is that optString returns the empty string ("")
        // if the key you specify doesn't exist. getString on the other hand throws
        // a JSONException. Use getString if it's an error for the data to be missing,
        // or optString if you're not sure if it will be there.
        try {
            baseResponse = new JSONObject(jsonData);
            if (baseResponse.has("status_code")) {
                int errorCode = baseResponse.getInt("status_code");
                Log.e(TAG, "parse reviews from json error code: " + String.valueOf(errorCode));
            }

            List<MovieReview> movieReviews = new ArrayList<>();
            JSONArray jsonArray = baseResponse.optJSONArray(MOVIE_REVIEW_RESULTS_KEY);
            // Create an empty ArrayList that we can start adding movies to

            for (int thisReview = 0; thisReview < jsonArray.length(); thisReview++) {

                /* Step 1 */
                Log.d(TAG, "line 236 reached");

                /*Sub steps A-D */
                // A - Since our JSON data begins with a base object, we
                //     need to first initialize a base JSON Object
                JSONObject jsonMovieReview = jsonArray.optJSONObject(thisReview);

                String author = jsonMovieReview.optString(AUTHOR_KEY);
               // backdropPath = BACKDROP_URL + backdropPath;

                String content = jsonMovieReview.optString(CONTENT_KEY);
                String url = jsonMovieReview.optString(MOVIE_REVIEW_URL_KEY);







                /* 2 */
                Log.d(TAG, "line 257 reached");





                // for (int j = 0; j < resultsArray.length(); j++) {
                //    resultsArrayList.add(resultsArray.getopString(j));
                //}

                /* 5 */
                Log.d(TAG, "line 268 reached");
                MovieReview movieReview = new MovieReview(author, content, url );

                movieReviews.add(movieReview);
                Log.d(TAG, "line 272 reached");



            }// end for loop
            return movieReviews;

        }     // try
        catch (Exception e) {
            Log.d(TAG, e.getMessage());


        }
        // Return the list of movie reviews for a given poster.
        return null;

    } //end method- parseReviewsFromJson
}// end class - JsonParser