package com.ptoles.popularmovies.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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


//RecyclerView.Adapter<MoviePosterAdapter.ViewHolder> implements Serializable{

// 1 - Constructor



public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder>{
//https://stackoverflow.com/questions/40683817/how-to-set-two-adapters-to-one-recyclerview
    private static final int ITEM_TYPE_MOVIE_POSTER = 0;
    private static final int ITEM_TYPE_UNKNOWN = -1;

    private ArrayList<MoviePoster> moviePosters = new ArrayList<>();
    private Context moviePosterContext;

    private static final String TAG = MoviePosterAdapter.class.getSimpleName();


     public MoviePosterAdapter(Context context, ArrayList<MoviePoster> moviePosters) {

         this.moviePosterContext = context;
         this.moviePosters = moviePosters;
     }



     @NonNull
     @Override
     public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         View view =  layoutInflater.inflate(R.layout.grid_item, parent, false);

        // MoviePosterViewHolder viewHolder =
         return new MoviePosterViewHolder(view);
     }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {

        MoviePoster currentMoviePoster = moviePosters.get(position);
        ImageView ivPoster= (ImageView) holder.view;


        if (currentMoviePoster != null) {
            Picasso.get()
                    .load(currentMoviePoster.getPosterPath())
                    .noFade()
                    //.resize(300,350)
                    //.error(R.drawable.bug)//when we get an error
                    .error(R.drawable.bug)//when we get an error
                    .placeholder(R.drawable.loading_1)// as the image loads
                    .into(ivPoster);
        }else {

            Picasso.get()
                    .load(R.drawable.movie_poster_template_dark_no_image)
                    .noFade()
                    //.resize(300,350)
                    //.error(R.drawable.bug)//when we get an error
                    .error(R.drawable.bug)//when we get an error
                    .placeholder(R.drawable.loading_1)// as the image loads
                    .into(ivPoster);





        }
    }

    @Override
     public int getItemCount() {
         return 0;
     }


     public class MoviePosterViewHolder extends RecyclerView.ViewHolder {
         private Context moviePosterContext;

         View view = itemView;
         TextView titleView = view.findViewById(R.id.original_title_tv);//(TextView)
         TextView releaseDateView = view.findViewById(R.id.release_date_tv);//(TextView)
         TextView ratingView = view.findViewById(R.id.vote_average_tv);//(TextView)
         TextView synopsisView = view.findViewById(R.id.overview_tv); //(TextView)


         public MoviePosterViewHolder(View itemView) {
             super(itemView);
             moviePosterContext = itemView.getContext();
         }


        public int getItemViewType(int position) {

            if (moviePosters.get(position) instanceof MoviePoster) {
                return ITEM_TYPE_MOVIE_POSTER;
            } else {
                return ITEM_TYPE_UNKNOWN;
            }


        }



        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(moviePosterContext).inflate(
                        R.layout.grid_item, parent, false);
            }
            // Get the {@link MoviePoster} object located at this position in the list
            MoviePoster currentMoviePoster = moviePosters.get(position);
            ImageView ivPoster = listItemView.findViewById(R.id.poster_image_view);


            if (ivPoster == null) {
                //get the layout from the xml file
                listItemView = LayoutInflater
                        .from(moviePosterContext)
                        .inflate(R.layout.activity_blank_poster_thumbnail, null);

                ivPoster = new ImageView(moviePosterContext);
                ivPoster.setAdjustViewBounds(true);
            }

            if (currentMoviePoster != null) {
                Picasso.get()
                        .load(currentMoviePoster.getPosterPath())
                        .noFade()
                        //.resize(300,350)
                        //.error(R.drawable.bug)//when we get an error
                        .error(R.drawable.movie_poster_template_dark_no_image)//when we get an error
                        .placeholder(R.drawable.loading_1)// as the image loads
                        .into(ivPoster);
            }
            return listItemView;
        }// getView
     }// inner class MoviePosterViewHolder
 }// class MoviePosterAdapter
    //@32:30 - https://www.youtube.com/watch?v=yP8KKpKEXYc
// https://antonioleiva.com/recyclerview/

    // https://www.tutorialspoint.com/android/android_grid_view.htm

    // private static final String TAG = MoviePosterAdapter.class.getSimpleName();
    // https://www.concretepage.com/android/android-asynctaskloader-example-with-listview-and-baseadapter
    // URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=75fa203cc819faba4f627132ce414b9c")

    // https://www.youtube.com/watch?v=wfoqsl5REpo


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




/*

            private void bind(final MoviePosterViewHolder holder) {
               // holder.textView.setText("item " + holder.getAdapterPosition());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int position = holder.getAdapterPosition();
                        Log.d("butt", "click " + position);
                        MoviePosterAdapter.this.notifyItemChanged(position, "payload " + position);
                    }
                });

 */

