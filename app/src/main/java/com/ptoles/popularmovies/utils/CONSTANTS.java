package com.ptoles.popularmovies.utils;

import android.content.Context;

import com.ptoles.popularmovies.R;

import static com.ptoles.popularmovies.utils.JsonParser.apiKey;

public class CONSTANTS {

    public static final String TAG_PAUSE = "ON PAUSE";
    protected static final String TAG_RESUME = "ON RESUME";
    protected static final String TAG_DESTROY = "ON DESTROY";
    protected static final String TAG_CREATE = "ON CREATE";
    protected static final String TAG_ON_RESTORE = "onRestoreSavedInstance";
    protected static final String TAG_ON_SAVE = "onSaveInstanceState";

    public static final String DEFAULT_ORDER_BY_KEY = "order_by";
    public static final String DEFAULT_SORT_ORDER_KEY = "popular";
    public static final String POPULAR_SORT_ORDER_KEY = "popular";
    public static final String HIGHEST_RATED_ORDER_KEY = "top_rated";

    public static final String KEY_LAYOUT_MANAGER_STATE = "layoutManagerState";
    public static final String KEY_CURRENT_SORT_ORDER_STATE = "CurrentSortOrder";
    public static final String KEY_DEFAULT_SORT_ORDER_STATE = "DefaultSortOrder";


    //Movie Poster field names
    public static final String MOVIE_POSTER_RESULTS_KEY       = "results";
    public static final String MOVIE_REVIEW_RESULTS_KEY       = "results";
    public static final String MOVIE_TRAILER_RESULTS_KEY      = "results";

    public static final String GENRE_IDS_KEY          = "genre_ids";
    public static final String BACKDROP_PATH_KEY      = "backdrop_path";
    public static final String POSTER_PATH_KEY        = "poster_path";
    public static final String ORIGINAL_TITLE_KEY     = "original_title";
    public static final String VOTE_AVERAGE_KEY       = "vote_average";
    public static final String OVERVIEW_KEY           = "overview";
    public static final String VOTE_COUNT_KEY         = "vote_count";
    public static final String MOVIE_POSTER_ID_KEY    = "id";
    public static final String HAS_VIDEO_KEY          = "video";
    public static final String MOVIE_POSTER_TITLE_KEY = "title";
    public static final String POPULARITY_KEY         = "popularity";
    public static final String ORIGINAL_LANGUAGE_KEY  = "original_language";
    public static final String IS_ADULT_KEY           = "adult";
    public static final String RELEASE_DATE_KEY       = "release_date";

    //Movie Review field names
    public static final String AUTHOR_KEY             = "author";
    public static final String CONTENT_KEY            = "content";
    public static final String MOVIE_REVIEW_URL_KEY   = "url";

    /*
                    List<Integer> genreIdsList = new ArrayList<>();
                    List<String> resultsArrayList = new ArrayList<>();

     */
    final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    //TODO: add your API key Here
    final static String API_KEY = apiKey;
    final static String BEFORE_API_KEY = "?api_key=";

    // URL to query the movie database
    protected static final String MOVIE_MOST_POPULAR_URL =
            "https://api.themoviedb.org/3/movie/popular?&api_key=" + apiKey;

    protected static final String MOVIE_TOP_RATED_URL =
            "https://api.themoviedb.org/3/movie/top_rated?&api_key=" + apiKey;


}
