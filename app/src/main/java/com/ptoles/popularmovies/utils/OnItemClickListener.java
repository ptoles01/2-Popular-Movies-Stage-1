package com.ptoles.popularmovies.utils;

//https://www.specbee.com/blogs/android-tutorials-recyclerview-and-its-item-click-listener-part-1
// https://gist.github.com/Isabellle/d66ccbc7e88dc8c9b3429cf3ea8b6d97
import android.graphics.Movie;
import android.view.View;

import com.ptoles.popularmovies.model.MoviePoster;

import java.util.ArrayList;

// To Implement Recyclerview onItemClickListener, an interface should be created
// which specifies listener’s behaviour, similar to normal click listener except
// the fact  that it also has the position as parameter
public interface  OnItemClickListener {
        void onItemClick(View view, int clickedPosition, ArrayList<MoviePoster>moviePosters);

}
/*
public interface CustomMoviePosterClickListener {
        public void onItemClick(View view, int clickedPosition);

}

*/
 