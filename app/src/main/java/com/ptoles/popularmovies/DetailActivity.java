package com.ptoles.popularmovies;

/*
https://stackoverflow.com/questions/26811212/
      android-studio-newly-created-directory-not-appearing-in-folders-view
*/

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ptoles.popularmovies.model.MoviePoster;
import com.ptoles.popularmovies.utils.MoviePosterAdapter;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @NonNull
    public static Intent makeIntent(Context context){
        return new Intent(context, DetailActivity.class);
    }
    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final Float MAX_RATING = 10f;
    private static final Float STEP_SIZE = .1f;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent       = getIntent(); Log.d(TAG, "getIntent() line 30.");


        ImageView mpImageView = findViewById(R.id.poster_image_view);


        if (intent != null) {

            int moviePosition = intent.getIntExtra("moviePosition", 0);
            String originalTitle = intent.getStringExtra("originalTitle");
            Float rating = Float.valueOf(intent.getStringExtra("rating"));
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT  )
            { // Portrait Mode
                TextView titleView = findViewById(R.id.original_title_tv);//(TextView)intent.getStringExtra("originalTitle")
                titleView.setText(originalTitle);

                RatingBar ratingBar = findViewById(R.id.ratingBar);

                Float scale =  (MAX_RATING / ratingBar.getNumStars());// convert numStars from an integer to a float percentage of numStars

                //ratingBar.setVisibility(View.INVISIBLE); //maybe make it invisible rather than remove it as is now the case

                ratingBar.setRating(rating/scale); // to set rating value
                ratingBar.setStepSize(STEP_SIZE); // to show to stars

            } /*else {
                // Landscape Mode
                // The titleView resource doesn't exist in landscape mode.
                // The window title is used to display the information instead.
            }*/


            // Set these for either mode: Portrait or Landscape
            setTitle(originalTitle);

            TextView releaseDateView = findViewById(R.id.release_date_tv);//(TextView)
            releaseDateView.setText("Released: "+intent.getStringExtra("releaseDate"));

            TextView ratingView = findViewById(R.id.vote_average_tv);//(TextView)
            ratingView.setText("Rating:  " + intent.getStringExtra("rating")+ "/10");

            TextView synopsisView = findViewById(R.id.overview_tv); //(TextView)
            String synopsisOverview = intent.getStringExtra("synopsisOverview");
            synopsisView.setText("Synopsis:"+ "\n" +synopsisOverview);



//with(mpContext)
            Picasso.with(mpImageView.getContext())
                    .load(intent.getStringExtra("backdropPath")) /* or .getBackdropPath()*/

                   // .load(String.valueOf(currentMovie.getBackdropPath())) /* or .getBackdropPath()*/
                    .noFade()
                    //.resize(300,350)
                    //.error(R.drawable.bug)//when we get an error
                    .error(R.drawable.movie_poster_template_dark_no_image)//when we get an error
                    .placeholder(R.drawable.loading_1)// as the image loads
                    .into(mpImageView); /* or holder.mpImageView*/
        } else {

            Picasso.with(mpImageView.getContext())
                    .load(R.drawable.movie_poster_template_dark_no_image) /* or .getBackdropPath()*/
                    .noFade()
                    //.resize(300,350)
                    .error(R.drawable.bug)//when we get an error
                    //.error(R.drawable.movie_poster_template_dark_no_image)//when we get an error
                    .placeholder(R.drawable.loading_1)// as the image loads
                    .into(mpImageView); /* or holder.mpImageView*/

        }
    }


}

