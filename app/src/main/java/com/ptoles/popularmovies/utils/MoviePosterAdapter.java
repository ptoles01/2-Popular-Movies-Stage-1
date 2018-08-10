package com.ptoles.popularmovies.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;

import com.ptoles.popularmovies.*;
import com.ptoles.popularmovies.model.MoviePoster;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Creating a custom adapter requires the following:
//      1- Create a class that extends RecyclerView.Adapter
//      2- Define the necessary constructors
//      3- Override the getView method
//      4-


public class MoviePosterAdapter extends
        RecyclerView.Adapter<MoviePosterAdapter.ViewHolder> implements Serializable{

    private final Context mpContext;
    private List<MoviePoster> mpMoviePosterList;
    private ListItemClickListener mpOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedPosition);
    }

    // 1 - Constructor
    public MoviePosterAdapter(Context context, List<MoviePoster> moviePosters, ListItemClickListener listener) {
        mpContext = context;
        mpMoviePosterList = moviePosters;
        mpOnClickListener = listener;

    }
    public MoviePosterAdapter(Context context, List<MoviePoster> moviePosters) {
        mpContext = context;
        mpMoviePosterList = moviePosters;

    }

    //@32:30 - https://www.youtube.com/watch?v=yP8KKpKEXYc
// https://antonioleiva.com/recyclerview/

    // https://www.tutorialspoint.com/android/android_grid_view.htm

    // private static final String TAG = MoviePosterAdapter.class.getSimpleName();
    // https://www.concretepage.com/android/android-asynctaskloader-example-with-listview-and-baseadapter
    // URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=75fa203cc819faba4f627132ce414b9c")

    // https://www.youtube.com/watch?v=wfoqsl5REpo



    @NonNull
    @Override
    public MoviePosterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View view = LayoutInflater.from(mpContext).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    // 2 - The number of movie posters in the list
    //     Count of data items represented by adapter.
    @Override
    public int getItemCount() {
        if (mpMoviePosterList == null || mpMoviePosterList.size() == 0){
            return -1;
        }
        return mpMoviePosterList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    //
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // the data item is just an image stored as a string in this case
        private ImageView mpImageView;

        ViewHolder(View itemView) {// listener for the view itself
            super(itemView);
            mpImageView = itemView.findViewById(R.id.poster_image_view);
            mpImageView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {// listener for the item contained in the view

            int clickedPosition = getAdapterPosition();
            mpOnClickListener.onListItemClick(clickedPosition);
            Intent intent = new Intent(mpContext, DetailActivity.class);
            intent.putExtra("Movie Poster",mpMoviePosterList.get(clickedPosition));
                    //see DetailActivity for dereference
                    //https://www.youtube.com/watch?v=WBbsvqSu0is - Send Custom Object Using Parcelable
            mpContext.startActivity(intent);
        }
    }



    // 3 - required by the BaseAdapter class but not used in this application
    //     Returns the data item for a given position.
//https://www.google.com/search?client=opera&q=what+does+onbindviewholder+do&sourceid=opera&ie=UTF-8&oe=UTF-8
 //   @Override
 //   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
 //   }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LayoutInflater movieInflater = (LayoutInflater) mpContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        MoviePoster moviePoster = mpMoviePosterList.get(position);
        // Get item from your data
        // Replace the contents of the view with that item
        if (holder.mpImageView == null) {
            //get the layout from the xml file
            View  movieGridView = movieInflater.inflate(R.layout.activity_blank_poster_thumbnail,null);

            holder.mpImageView  = new ImageView(mpContext);
            holder.mpImageView.setAdjustViewBounds(true);
        }

//with(mpContext)
        Picasso.get()
                .load(moviePoster.getPosterPath())
                .noFade()
                //.resize(300,350)
                //.error(R.drawable.bug)//when we get an error
                .error(R.drawable.movie_poster_template_dark_no_image)//when we get an error
                .placeholder(R.drawable.loading_1)// as the image loads
                .into(holder.mpImageView);

    }

    // 5 - create a new moviePosterView for each item listed in the MoviePosterAdapter
    // The second parameter of the getView() method is what enables us to reuse
    // View objects. If you ignore it, the performance of your adapter view will be
    // poor. When the getView() method is called for the first time, originalView
    // is null. You must initialize it by inflating the resource file that specifies
    // the layout of the list items. To do so, obtain a reference to a LayoutInflater
    // using the getLayoutInflater() method and invoke its inflate() method.
    // https://www.concretepage.com/android/android-asynctaskloader-example-with-listview-and-baseadapter
    // https://www.101apps.co.za/articles/gridview-tutorial-using-the-picasso-library.html
    // the parameters:
    //  position – the image’s position in the adapter (we can also use it as an index to our mThumbIds array)
    //  originalView – an old view that we may be able to recycle
    //  parent – this is the grid view into which we place this image view
    //  ImageView – the image view that we return to display in one of the grid’s cells. We check if we can re-use the convertView. If not, we create a new image view
    //  Picasso – we’re using the Picasso Library
    //      with – the context of the Picasso instance
    //      load – the image that we want to load into the grid cell
    //      placeholder – a placeholder image that’s displayed while the required image is loaded
    //      error – an image that displays if the requested image can’t be loaded
    //      noFade – by default, the image fades in if loaded from the disc cache or from the network. We’ve disabled the fade-in
    //      centerCrop – crops the image so it fits inside our dimensions
    //      into – asynchronously loads the image into this image view




}

