package com.ptoles.popularmovies.utils;


import android.content.Context;
import android.content.Intent;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


// RecyclerView.Adapter<MoviePosterAdapter.ViewHolder> implements Serializable{
// 1 - Constructor


// https://www.specbee.com/blogs/android-tutorials-recyclerview-and-its-item-click-listener-part-1
// The Adapter class will be extend RecyclerView.Adapter and must, therefore,
// override 3 methods: 1- onCreateViewHolder (), 2- onBindViewHolder(), 3- getItemCount().
//
// Also, in the adapter class In the adapter class we have a xml file to hold one single row.
// (i.e. grid_item.xml)

public class MoviePosterAdapter extends
        RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder>{
    private static final String TAG = MoviePosterAdapter.class.getSimpleName();

    //https://stackoverflow.com/questions/40683817/how-to-set-two-adapters-to-one-recyclerview
    private static final int ITEM_TYPE_MOVIE_POSTER = 0;
    private static final int ITEM_TYPE_UNKNOWN = -1;

    public static ArrayList<MoviePoster> moviePosters;
    private int rowLayout;
    private Context moviePosterContext;
    private OnItemClickListener moviePosterClickListener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener moviePosterClickListener) {
        this.moviePosterClickListener = moviePosterClickListener;
    }


    public MoviePosterAdapter(Context context, ArrayList<MoviePoster> newMoviePosters, int rowLayout) {

        this.moviePosterContext = context;
        moviePosters = newMoviePosters;
        this.rowLayout = rowLayout;// R.layout.grid_item
        //this.moviePosterClickListener = listener;
    }

    public void  updateMovies(ArrayList<MoviePoster> newMoviePosters ) {
        moviePosters= newMoviePosters;
        notifyDataSetChanged();
   }


    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        // onCreateViewHolder() :  only creates a new viewholder when there are no existing
        // view holders which the RecyclerView can reuse.
// https://stackoverflow.com/questions/28904479/best-place-to-attach-onclicklistener-in-recyclerview
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new MoviePosterViewHolder(view);
         return (MoviePosterViewHolder) viewHolder;
    }
    @Override
    public int getItemCount() {
        // getItemCount() returns the number of items in the list.
        return moviePosters != null ? moviePosters.size() : 0;    }


    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {
        // onBindViewHolder() is Called by RecyclerView to display the data at the specified position
        // onBindViewHolder is called every time you bind your view with data. So here is not the
        // best place to set click listener

        final MoviePoster currentMoviePoster = moviePosters.get(position);
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

        //View view = itemView;
        /*TextView titleView = view.findViewById(R.id.original_title_tv);//(TextView)
        TextView releaseDateView = view.findViewById(R.id.release_date_tv);//(TextView)
        TextView ratingView = view.findViewById(R.id.vote_average_tv);//(TextView)
        TextView synopsisView = view.findViewById(R.id.overview_tv); //(TextView)*/
        public ImageView imageView;

        public MoviePosterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poster_image_view); //(ImageView)
            itemView.setTag(itemView);//???
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (moviePosterClickListener != null) {
                if(getAdapterPosition()!=RecyclerView.NO_POSITION){

                    moviePosterClickListener.onItemClick(view, getAdapterPosition(),moviePosters);

                }
            }
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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
