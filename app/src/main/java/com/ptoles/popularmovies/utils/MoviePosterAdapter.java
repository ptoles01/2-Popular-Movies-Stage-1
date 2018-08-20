package com.ptoles.popularmovies.utils;


import android.content.Context;
import android.content.Intent;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

//import com.google.gson.Gson;

import com.ptoles.popularmovies.*;
import com.ptoles.popularmovies.model.MoviePoster;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

// Creating a custom adapter requires the following:
//      1- Create a class that extends RecyclerView.Adapter
//      2- Define the necessary constructors
//      3- Override the getView method
//      4-


//RecyclerView.Adapter<MoviePosterAdapter.ViewHolder> implements Serializable{

// 1 - Constructor



public class MoviePosterAdapter extends
        RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder>{
    private static final String TAG = MoviePosterAdapter.class.getSimpleName();

    //https://stackoverflow.com/questions/40683817/how-to-set-two-adapters-to-one-recyclerview
    private static final int ITEM_TYPE_MOVIE_POSTER = 0;
    private static final int ITEM_TYPE_UNKNOWN = -1;



    private List<MoviePoster> moviePosters;
    private Context moviePosterContext;
    private ListItemClickListener moviePosterListener;

    public MoviePosterAdapter(MainActivity mainActivity, ArrayList<MoviePoster> moviePosters) {
        this(mainActivity, moviePosters, mainActivity)
    }


    public interface ListItemClickListener {
        void onListItemClick(int clickedPosition);
    }

     public MoviePosterAdapter(Context context, List<MoviePoster> newMoviePosters, ListItemClickListener listener) {

         moviePosterContext = context;
         this.moviePosters = newMoviePosters;
         moviePosterListener = listener;
     }

    public void updateMovies(List<MoviePoster> newMoviePosters) {
        this.moviePosters = newMoviePosters;
        notifyDataSetChanged();

    }


    @NonNull
     @Override
     public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         //LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         // View view =  layoutInflater.inflate(R.layout.grid_item, parent, false);

         // MoviePosterViewHolder viewHolder =
         View view = LayoutInflater.from(moviePosterContext)
                 .inflate(R.layout.grid_item, parent, false);
         return new MoviePosterViewHolder(view);
     }
    @Override
    public int getItemCount() {
        return moviePosters != null ? moviePosters.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {

        MoviePoster currentMoviePoster = moviePosters.get(position);
        ImageView moviePosterImageView= holder.imageView;
        if (currentMoviePoster != null){
            Picasso.with(moviePosterImageView.getContext())
                    .load(currentMoviePoster.getPosterPath())
                    .noFade()
                    //.resize(300,350)
                    //.error(R.drawable.bug)//when we get an error
                    .error(R.drawable.bug)//when we get an error
                    .placeholder(R.drawable.loading_1)// as the image loads
                    .into(moviePosterImageView) ;
        }else{
            Picasso.with(moviePosterImageView.getContext())
                    .load(R.drawable.movie_poster_template_dark_no_image)
                    .noFade()
                    //.resize(300,350)
                    //.error(R.drawable.bug)//when we get an error
                    .error(R.drawable.bug)//when we get an error
                    .placeholder(R.drawable.loading_1)// as the image loads
                    .into(moviePosterImageView);
        }

    }



     public class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         private Context moviePosterContext;

         View view = itemView;
         TextView titleView = view.findViewById(R.id.original_title_tv);//(TextView)
         TextView releaseDateView = view.findViewById(R.id.release_date_tv);//(TextView)
         TextView ratingView = view.findViewById(R.id.vote_average_tv);//(TextView)
         TextView synopsisView = view.findViewById(R.id.overview_tv); //(TextView)
         private ImageView imageView;

         public MoviePosterViewHolder(View itemView) {
             super(itemView);
             imageView = itemView.findViewById(R.id.poster_image_view); //(ImageView)
             itemView.setOnClickListener(this);
         }

         @Override
         public void onClick(View view) {

             int clickedPosition = getAdapterPosition();
             moviePosterListener.onListItemClick(clickedPosition);

             MoviePoster currentMovie = moviePosters.get(clickedPosition);
             //Gson gson = new Gson();

             Intent intent = new Intent(moviePosterContext, DetailActivity.class);
             intent.putExtra("key", /*gson.toJson*/(currentMovie)); //Optional parameters

             moviePosterContext.startActivity(intent);
         }
     }




        public int getItemViewType(int position) {

            if (moviePosters.get(position) != null) {
                return ITEM_TYPE_MOVIE_POSTER;
            } else {
                return ITEM_TYPE_UNKNOWN;
            }


        }// inner class MoviePosterViewHolder


     }
 // class MoviePosterAdapter
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

