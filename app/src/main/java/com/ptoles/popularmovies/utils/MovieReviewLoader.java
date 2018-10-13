package com.ptoles.popularmovies.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.ptoles.popularmovies.model.MovieReview;

import java.util.List;


    public class MovieReviewLoader extends AsyncTaskLoader<List<MovieReview>> {

        private static final String TAG = com.ptoles.popularmovies.utils.MovieReviewLoader.class.getName();

        //
        private String jsonURL;

        // Constructor
        public MovieReviewLoader(Context context, String jsonData) {
            super(context);
            jsonURL = jsonData;// jsondata found at this url containing
            // a list of movie data and their poster images
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<MovieReview> loadInBackground() {
            //loadInBackground - uses JsonParser's parseReviews method
            //                  to parse an arraylist of movieposters and populate
            //                   the movieposters variable declared in

            //returns moviesPosters  i.e. List<MoviePoster>
            if (jsonURL == null) {
                return null;
            } else {
                return JsonDownloader.DownloadReviewData(jsonURL);
            }
        }
    }


