package com.ptoles.popularmovies;

/*
https://stackoverflow.com/questions/26811212/
      android-studio-newly-created-directory-not-appearing-in-folders-view
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_poster_thumbnail);

        ImageView imageIv = findViewById(R.id.poster_image_view);

        Intent intent = getIntent(); Log.d(TAG, "getIntent() line 30.");


        // Selected image id
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        //MoviePosterAdapter movieAdapter = new MoviePosterAdapter();




    }


}

