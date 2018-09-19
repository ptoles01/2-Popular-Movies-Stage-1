package com.ptoles.popularmovies.utils;

import static com.ptoles.popularmovies.utils.JsonParser.apiKey;

public class CONSTANTS {
    public static final String TAG_PAUSE = "ON PAUSE";
    protected static final String TAG_RESUME = "ON RESUME";
    protected static final String TAG_DESTROY = "ON DESTROY";
    protected static final String TAG_CREATE = "ON CREATE";
    protected static final String TAG_ON_RESTORE = "onRestoreSavedInstance";
    protected static final String TAG_ON_SAVE = "onSaveInstanceState";

    public static final String KEY_SAVED_INSTANCE_STATE = "savedInstanceState";
    public static final String KEY_LAYOUT_MANAGER_STATE = "layoutManagerState";
    public static final String KEY_LIST_STATE = "listState";
    protected static final String  KEY_MOVIE_POSITION = "moviePosition";
    public static final String  KEY_SUPER_STATE = "savedSuperState";


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
