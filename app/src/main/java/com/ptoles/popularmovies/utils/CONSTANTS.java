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
