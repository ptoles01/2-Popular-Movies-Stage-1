package com.ptoles.popularmovies;

/*
https://stackoverflow.com/questions/26811212/
      android-studio-newly-created-directory-not-appearing-in-folders-view
*/

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ptoles.popularmovies.model.MoviePoster;
import com.ptoles.popularmovies.utils.MoviePosterAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @NonNull
    public static Intent makeIntent(Context context){
        return new Intent(context, DetailActivity.class);
    }
    private static final String TAG = DetailActivity.class.getSimpleName();

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_poster_thumbnail);

        ImageView mpImageView = (ImageView) findViewById(R.id.poster_image_view);

        Intent intent       = getIntent(); Log.d(TAG, "getIntent() line 30.");
        MoviePoster moviePoster = intent.getParcelableExtra("Movie Poster");
                //https://www.youtube.com/watch?v=WBbsvqSu0is - Send Custom Object Using Parcelable
                //see MoviePosterAdapter for this reference

        if (moviePoster != null) {
            TextView titleView = (TextView) findViewById(R.id.original_title_tv);//(TextView)
            titleView.setText(moviePoster.getOriginalTitle());

            TextView releaseDateView = (TextView) findViewById(R.id.release_date_tv);//(TextView)
            releaseDateView.setText("Released: " + moviePoster.getReleaseDate());

            TextView ratingView = (TextView) findViewById(R.id.vote_average_tv);//(TextView)
            ratingView.setText("Viewer rating: " + moviePoster.getVoteAverage() + "/10");

            TextView synopsisView = (TextView) findViewById(R.id.overview_tv); //(TextView)
            synopsisView.setText(moviePoster.getOverview());

//with(mpContext)
            Picasso.with(mpImageView.getContext())
                    .load(moviePoster.getPosterPath()) /* or .getBackdropPath()*/
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

