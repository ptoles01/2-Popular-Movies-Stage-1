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
    RecyclerView getMoviePosterRecyclerView();
    void launchDetailActivity(MoviePoster moviePoster);


}


*/


import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.preference.PreferenceManager;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.ptoles.popularmovies.model.MoviePoster;
import com.ptoles.popularmovies.utils.JsonParser;
import com.ptoles.popularmovies.utils.MoviePosterAdapter;
import com.ptoles.popularmovies.utils.MoviePosterLoader;
import com.ptoles.popularmovies.utils.NetworkUtils;
import com.ptoles.popularmovies.utils.MovieSortPreferences;

import static com.ptoles.popularmovies.utils.JsonParser.*;

public class MainActivity extends AppCompatActivity implements
         LoaderManager.LoaderCallbacks<List<MoviePoster>>,
       // MoviePosterAdapter.ListItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int MOVIE_LOADER_ID = 0;
    private static final String CURRENT_POSITION = "position";

    private TextView errorMessage;

    MoviePosterAdapter moviePosterAdapter;
    private String orderBy;
    private RecyclerView moviePosterRecyclerView;

    private ImageView moviePosterImageView;

    private int moviePosition;

    private ProgressBar progressBar;

    SharedPreferences sharedPreferences;

    RecyclerView moviePosterGrid;

    RecyclerView.LayoutManager moviePosterLayoutManager;
    private ArrayList<MoviePoster> moviePosters;

    android.support.v4.app.LoaderManager.LoaderCallbacks<MoviePoster> myLoaderCallbacks;


    // URL to query the movie database
    private static final String MOVIE_MOST_POPULAR_URL =
            "https://api.themoviedb.org/3/movie/popular?&api_key="+apiKey;

    private static final String MOVIE_TOP_RATED_URL =
            "https://api.themoviedb.org/3/movie/top_rated?&api_key="+apiKey;

    //https://api.themoviedb.org/3/movie/top_rated?api_key=75fa203cc819faba4f627132ce414b9c
    public ImageView getMoviePosterImageView() {
        return moviePosterImageView;
    }

    private boolean isInternetAvailable() {
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
    protected void onCreate(final Bundle savedInstanceState) {
        //https://www.youtube.com/watch?v=WBbsvqSu0is - sending parcelable bundle between activities
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                // Set the orderBy key to be whatever is already indicated in the class
       orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
                // now register your listener for changes


        // 1 - Obtain a reference to my RecyclerView

        moviePosterRecyclerView = findViewById(R.id.movie_poster_grid);

        errorMessage = findViewById(R.id.error_message_text_view);

        moviePosterRecyclerView.setHasFixedSize(true);

        int numberOfColumns = getResources().getInteger(R.integer.num_columns);//2 or 4;
        moviePosterLayoutManager = new GridLayoutManager(this, numberOfColumns);
        moviePosterRecyclerView.setLayoutManager(moviePosterLayoutManager);

        //moviePosters = new ArrayList<>();
        moviePosterAdapter = new MoviePosterAdapter(this, moviePosters);

        moviePosterRecyclerView.setAdapter(moviePosterAdapter);



        // Check for network connectivity before attempting to download  poster data
        progressBar = findViewById(R.id.progressBar);

        // If there is a network connection, get the data
        if (isInternetAvailable()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
          //  Toast.makeText(this,"You are online!!!!",Toast.LENGTH_LONG).show();

            //https://stackoverflow.com/questions/41267446/how-to-get-loadermanager-initloader-working-within-a-fragment
            //implementation 'com.android.support.v4.app.LoaderManager.LoaderCallbacks'
            //    implementation 'com.android.support.v4.app.LoaderManager.LoaderCallbacks'
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);//.forceload();

            errorMessage.setVisibility(View.GONE); // Hide the error message

        } else {
            // Otherwise, hide the progress bar then display an error message
            // Hide the progress bar before
            progressBar.setVisibility(View.GONE);

            // Update empty state with no connection error message
            moviePosterRecyclerView.setVisibility(View.GONE);//
            errorMessage.setVisibility(View.VISIBLE);
          //  Toast.makeText(this,"You are not online!!!!",Toast.LENGTH_LONG).show();

        }

        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_POSITION)) {
            moviePosition = savedInstanceState.getInt(CURRENT_POSITION);
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
    //https://www.learnhowtoprogram.com/android/web-service-backends-and-custom-fragments/custom-adapters-with-recyclerview


    private void startPreferencesActivity(MoviePoster moviePoster) {
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }// end - private void startPreferencesActivity(int position)


    @Override
    public boolean onCreateOptionsMenu(Menu mainMenu) {
        //*MenuInflater inflater
        getMenuInflater().inflate(R.menu.main_menu, mainMenu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    } // end - public boolean onOptionsItemSelected(MenuItem item)


    //@Override
    public Loader<List<MoviePoster>> onCreateLoader(int id, Bundle bundle) {

        String preferredSortOrder = MovieSortPreferences.getPreferredSortOrder(this);
        Uri moviePosterUri = null;
        String moviePosterString = null;

        if (preferredSortOrder.equals(getString(R.string.pref_most_popular_key))) {
            Log.d(TAG, "LoadInBackground: use URL for most popular");
            moviePosterUri = Uri.parse(JsonParser.mostPopularUrl);
            moviePosterString = JsonParser.mostPopularUrl.toString();

        } else {
            Log.d(TAG, "LoadInBackground: use URL for highest rated");
            //return new MoviePosterLoader(this, MOVIE_TOP_RATED_URL);
            moviePosterUri = Uri.parse(JsonParser.topRatedUrl);
            moviePosterString = JsonParser.topRatedUrl.toString();
        }
        return new MoviePosterLoader(this, moviePosterString);
    }

    @Override

    public void onLoadFinished(Loader<List<MoviePoster>> loader, List<MoviePoster> moviePosters) {
 //   public void onLoadFinished(Loader<List<MoviePoster>> loader, List<MoviePoster> moviePosters) {
            // Hide(i.e. setVisibility(View.GONE) ) the progressBar since the data has finished loading

            progressBar.setVisibility(View.GONE);

            if (moviePosters != null && !moviePosters.isEmpty()) {//call a public method from here
                moviePosterAdapter.updateMovies(moviePosters);
            } else {
                errorMessage.setVisibility(View.VISIBLE);

            }
            // Set empty state text to display "No movies available!"
            errorMessage.setText(R.string.no_movies_available);

            moviePosterAdapter.notifyDataSetChanged();
        }


    @Override
    public void onLoaderReset(Loader<List<MoviePoster>> loader) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister MainActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    //Store the position(mPosition) in the bundle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (moviePosition != RecyclerView.NO_POSITION) {
            outState.putInt(CURRENT_POSITION, moviePosition);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
       // boolean PREFERENCES_UPDATED = true;
        boolean PREFERENCES_UPDATED=true;
        String defaultSortOrder = getString(R.string.settings_order_by_default);
        //String currentSortOrder = sharedPreferences.getString(key, orderBy);
        String popularSortOrder = getString(R.string.pref_most_popular_key);
        String topRatedSortOrder = getString(R.string.pref_top_rated_key);
        String newSortOrder = null;

        //PREFERENCES_UPDATED = (!currentSortOrder.equals(defaultSortOrder));

       // if(PREFERENCES_UPDATED){

            String currentSortOrder = sharedPreferences.getString(key, getString(R.string.settings_order_by_default));
            if (currentSortOrder.equals(getString(R.string.settings_order_top_rated)) ||
                    currentSortOrder.equals(getString(R.string.settings_order_most_popular))) {
                if (!currentSortOrder.equals(orderBy)) {
                    orderBy = null;
                    orderBy = currentSortOrder;
                    android.app.LoaderManager loaderManager = getLoaderManager();
                    loaderManager.restartLoader(MOVIE_LOADER_ID, null,  this);
                }
            } else if (currentSortOrder.equals(getString(R.string.setting_order_favorite))) {
            //Intent favoriteIntent = new Intent(this, FavoriteMovieActivity.class);
            //startActivity(favoriteIntent);
        }

           // }

        PREFERENCES_UPDATED = false;

        }

   // @Override
    public void onListItemClick(int clickedPosition) {

    }
  /*

        if (NetworkUtils.getInstance(this).isNetworkAvailable()) {

        Toast.makeText(this,"You are online!!!!",8000).show();

    } else {

        Toast.makeText(this,"You are not online!!!!",8000).show();
        Log.v("Home", "############################You are not online!!!!");
    }*/
} // end of the class MainActivity.java
/*

    public RecyclerView buildMoviePosterRecyclerView(RecyclerView view) {
         view.setHasFixedSize(true);

        ArrayList<MoviePoster> moviePosters = new ArrayList<>();
        moviePosterAdapter = new MoviePosterAdapter(this, moviePosters);
        view.setAdapter(moviePosterAdapter);


        errorMessage = findViewById(R.id.error_message_text_view);

        int numberOfColumns = getResources().getInteger(R.integer.num_columns);//2 or 4;
        RecyclerView.LayoutManager moviePosterLayoutManager = new GridLayoutManager(this, numberOfColumns);
        view.setLayoutManager(moviePosterLayoutManager);


        return view;
    }
 */