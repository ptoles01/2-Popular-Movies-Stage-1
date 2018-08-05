package com.ptoles.popularmovies;

/*
https://stackoverflow.com/questions/26811212/
      android-studio-newly-created-directory-not-appearing-in-folders-view
*/

/*
Features: AsyncTask Loader
          Parcelable
          Serializable
https://www.youtube.com/watch?v=yP8KKpKEXYc
Gil @28:32 discusses MainActivityInterface

interface MainActivityInterface{

    String getSortBy();
    MovieAdapter getMoviePosterAdapter();
    TextView getErrorMessage();
    ProgressBar getProgressBar(); // i.e. getLoadingIndicator();
    RecyclerView getMoviePosterGrid();
    void launchDetailActivity(MoviePoster moviePoster);


}


*/


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ptoles.popularmovies.model.MoviePoster;
import com.ptoles.popularmovies.utils.JsonParser;
import com.ptoles.popularmovies.utils.MoviePosterAdapter;
import com.ptoles.popularmovies.utils.MoviePosterLoader;
import com.ptoles.popularmovies.utils.NetworkUtils;
import com.ptoles.popularmovies.utils.MovieSortPreferences;

import java.util.ArrayList;
import java.util.List;

import static com.ptoles.popularmovies.utils.JsonParser.*;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<MoviePoster>>,
        MoviePosterAdapter.ListItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String sortBy;

    private TextView errorMessage;
    private RecyclerView moviePosterGrid;
    private MoviePosterAdapter moviePosterAdapter;
    RecyclerView.LayoutManager moviePosterLayoutManager;
    private List<MoviePoster> moviePostersList;
    private static final int MOVIE_LOADER_ID = 0;
    private static boolean PREFERENCES_UPDATED = false;

    private ProgressBar progressBar;



    // URL to query the movie database
    private static final String MOVIE_MOST_POPULAR_URL =
            "https://api.themoviedb.org/3/movie?sort_by=popularity.desc&api_key="+apiKey;
            //"https://api.themoviedb.org/3/movie/popular?&api_key="+apiKey;

    private static final String MOVIE_HIGH_RATED_URL =
            "https://api.themoviedb.org/3/movie?sort_by=top_rated.desc&api_key="+apiKey;
           // "https://api.themoviedb.org/3/movie/top_rated?&api_key="+apiKey;


    android.support.v4.app.LoaderManager.LoaderCallbacks<MoviePoster> moviePosterLoaderCallbacks;


    public boolean isInternetAvailable() {
        return NetworkUtils.getInstance(this).isNetworkAvailable();
    }

    public String apiKey() {
        return JsonParser.COMPLETE_URL+apiKey;
    }


    // 1 - Define my data source
    // 2 - Reference my GridView Adapter
    // 3 - Obtain a reference to my GridView
    // 4 - Obtain a reference to my progressBar
    // 5 - Setup the shared preferences manager
    // 6 - Specify the OnClick Listener
    // 7 - Setup the main menu

    // http://myhexaville.com/2017/06/28/android-butterknife-vs-data-binding/
    // http://craftedcourses.io/all-about-butter-knife-part-1/
    // https://proandroiddev.com/how-to-android-dagger-2-10-2-11-butterknife-mvp-part-1-eb0f6b970fd
    // https://www.youtube.com/watch?v=3Zrwi3FFrC8 - video regarding shared preferences
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1 - Obtain a reference to my RecyclerView
        moviePosterGrid = findViewById(R.id.movie_poster_grid);
        moviePosterGrid.setAdapter(moviePosterAdapter);
        moviePosterGrid.setHasFixedSize(true);

        errorMessage = findViewById(R.id.error_message_text_view);

        int numberOfColumns = getResources().getInteger(R.integer.num_columns);//2 or 4;
        moviePosterLayoutManager = new GridLayoutManager(this, numberOfColumns);
        moviePosterGrid.setLayoutManager(moviePosterLayoutManager);

        progressBar = findViewById(R.id.progressBar);

        moviePostersList = new ArrayList<>();
        moviePosterAdapter = new MoviePosterAdapter(this, moviePostersList, this);

        moviePosterGrid.setAdapter(moviePosterAdapter);

        // Check for network connectivity before attempting to download  poster data

        // If there is a network connection, get the data
        if (isInternetAvailable()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
          //  Toast.makeText(this,"You are online!!!!",Toast.LENGTH_LONG).show();

            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
            errorMessage.setVisibility(View.GONE); // Hide the error message
        } else {
            // Otherwise, hide the progress bar then display an error message
            // Hide the progress bar before
            progressBar.setVisibility(View.GONE);

            // Update empty state with no connection error message
            moviePosterGrid.setVisibility(View.GONE);//
            errorMessage.setVisibility(View.VISIBLE);
          //  Toast.makeText(this,"You are not online!!!!",Toast.LENGTH_LONG).show();

        }

        // 5 - Setup shared preferences re: sort order
        // Register MainActivity as an OnPreferenceChangedListener to receive a callback when a
        // SharedPreference has changed. Please note that we must unregister MainActivity as an
        // OnSharedPreferenceChanged listener in onDestroy to avoid any memory leaks.
        // the manager is  the place/resource/database to store the preferences
        SharedPreferences sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        //sharedPreferencesEditor = sharedPreferences.edit();
        // preferences you intend to store

        //checkSharedPreferences(sharedPreferencesEditor);
    }// end - protected void onCreate(Bundle savedInstanceState)

// @ 29:36
    public String getSortBy() { return sortBy; }

    public MoviePosterAdapter getMoviePosterAdapter() { return moviePosterAdapter;  }

    public TextView getErrorMessage() { return errorMessage;  }

    public RecyclerView getMoviePosterGrid() {  return moviePosterGrid;   }

    public ProgressBar getProgressBar() {return progressBar; }



    private void launchDetailActivity(Integer position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }// end - private void launchDetailActivity(int position)

    private void startDetailActivity(MoviePoster moviePoster) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("moviePoster", moviePoster);
        startActivity(intent);
    }// end - private void startDetailActivity(int position)


    @Override
    public boolean onCreateOptionsMenu(Menu mainMenu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, mainMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_preferences) {
            Intent startPreferencesActivity = new Intent(this, PreferencesActivity.class);
            startActivity(startPreferencesActivity);
            return true;
        }
//@ 30:28
        if (id == R.id.action_most_popular) {
            Intent startDetailActivity = new Intent(this, DetailActivity.class);
            sortBy = mostPopularUrl;
            //startActivity(startDetailActivity); @31:02 // he called FetchMovies();
            return true;

        }

        if (id == R.id.action_highest_rated) {
            Intent startDetailActivity = new Intent(this, DetailActivity.class);
            sortBy = topRatedUrl;
           // startActivity(startDetailActivity); @31:02 s // he called FetchMovies();
            return true;


        }



            return super.onOptionsItemSelected(item);
    } // end - public boolean onOptionsItemSelected(MenuItem item)

    @Override
    public android.support.v4.content.Loader<List<MoviePoster>> onCreateLoader(int id, Bundle bundle) {

        String preferredSortOrder = MovieSortPreferences.getPreferredSortOrder(this);

        if (preferredSortOrder.equals(getString(R.string.pref_most_popular_key))) {
            Log.d(TAG, "LoadInBackground: use URL for most popular");
            MoviePosterLoader moviePosterLoader = new MoviePosterLoader(this, JsonParser.mostPopularUrl);
            return moviePosterLoader;
        } else {
            Log.d(TAG, "LoadInBackground: use URL for highest rated");
            MoviePosterLoader moviePosterLoader = new MoviePosterLoader(this, JsonParser.topRatedUrl);
            return moviePosterLoader;
        }
    }
    @Override
//         public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<Movies>> loader, List<Movies> movies) {
    public void onLoadFinished(Loader<List<MoviePoster>> loader, List<MoviePoster> moviePosters) {
            // Hide(i.e. setVisibility(View.GONE) ) the progressBar since the data has finished loading

            progressBar.setVisibility(View.GONE);

            if (moviePosters != null && !moviePosters.isEmpty()) {
                moviePostersList.addAll(moviePosters);
            } else {
                errorMessage.setVisibility(View.VISIBLE);
            }

            // Set empty state text to display "No movies available!"
            errorMessage.setText(R.string.no_movies_available);
            moviePosterAdapter.notifyDataSetChanged();
        }


    @Override
    public void onLoaderReset(Loader<List<MoviePoster>> loader) {
        moviePostersList.clear();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister MainActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onListItemClick(int clickedPosition) {
        // Handled by MoviePosterAdapter.
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_UPDATED = true;
        if (PREFERENCES_UPDATED) {
            Log.d(TAG, "onSharedPreferenceChanged: preferences were changed");
            moviePostersList.clear();
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
            PREFERENCES_UPDATED = false;
        }
    }
  /*

        if (NetworkUtils.getInstance(this).isNetworkAvailable()) {

        Toast.makeText(this,"You are online!!!!",8000).show();

    } else {

        Toast.makeText(this,"You are not online!!!!",8000).show();
        Log.v("Home", "############################You are not online!!!!");
    }*/
} // end of the class MainActivity.java
