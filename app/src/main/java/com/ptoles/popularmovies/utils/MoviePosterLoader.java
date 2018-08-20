package com.ptoles.popularmovies.utils;

import android.content.Context;
import android.content.AsyncTaskLoader;
import com.ptoles.popularmovies.model.MoviePoster;
import java.util.List;

public class MoviePosterLoader extends AsyncTaskLoader<List<MoviePoster>> {

    private static final String TAG = MoviePosterLoader.class.getName();

    //
    private String jsonURL;

    // Constructor
    public MoviePosterLoader(Context context, String jsonData) {
        super(context);
        jsonURL = jsonData;// jsondata found at this url containing
                      // a list of movie data and their poster images
    }

    @Override
    protected void onStartLoading() {
       forceLoad();
    }

    @Override
    public List<MoviePoster> loadInBackground() {
        //loadInBackground - uses JsonParser's parseMovies method
        //                  to parse an arraylist of movieposters and populate
        //                   the movieposters variable declared in

        //returns moviesPosters  i.e. List<MoviePoster>
        if (jsonURL == null) {
            return null;
        } else {
            return JsonDownloader.DownloadMovieData(jsonURL);
        }
    }
}
