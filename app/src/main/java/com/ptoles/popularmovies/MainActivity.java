
package com.ptoles.popularmovies;

/*
https://stackoverflow.com/questions/26811212/
      android-studio-newly-created-directory-not-appearing-in-folders-view
*/

/*
Features: AsyncTask Loader
          Constraint and Relative Layouts
          Scrollbars
          Parcelable Data
          Portrait and Landscape Orientation of Main and Detail Activities
          Custom Grid Layout Recyclerview which "remembers" your previous scroll position
          JSON Data Manipulation
          Downloading from URLs
          Shared Preferences
          Sort Menu
          Custom "Star" Rating Display

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
Soon to come:
Cardview Layout
Carousel Layout
Alternating Layouts
Tabbed Layouts
Master Detail Layouts
Navigation Drawer Layouts
Floating Menu Layouts
Build Variants

*/



import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.app.LoaderManager;
import android.content.SharedPreferences;


import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import com.ptoles.popularmovies.model.MoviePoster;
import com.ptoles.popularmovies.utils.CONSTANTS;
import com.ptoles.popularmovies.utils.JsonParser;
import com.ptoles.popularmovies.utils.MoviePosterAdapter;
import com.ptoles.popularmovies.utils.MoviePosterLoader;
import com.ptoles.popularmovies.utils.NetworkUtils;
import com.ptoles.popularmovies.utils.MovieSortPreferences;
import com.ptoles.popularmovies.utils.OnItemClickListener;

import static com.ptoles.popularmovies.utils.CONSTANTS.DEFAULT_ORDER_BY_KEY;


public class MainActivity extends AppCompatActivity
        implements  LoaderManager.LoaderCallbacks<List<MoviePoster>>,
        SharedPreferences.OnSharedPreferenceChangeListener {


     private static final String TAG = MainActivity.class.getSimpleName();

    private static final int MOVIE_LOADER_ID = 0;

    private TextView errorMessage;

    private static Parcelable layoutManagerState;

    private RecyclerView moviePosterRecyclerView;

    private MoviePosterAdapter moviePosterAdapter;
    private ArrayList<MoviePoster> moviePosters = new ArrayList<>();


// TODO: 1 - Check whether I am properly rewriting my orderBy throughout the application
    private String orderBy = "";// initialized below in
                                  // setupSharedPreferences

    private ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    RecyclerView.LayoutManager moviePosterLayoutManager;





    private boolean isInternetAvailable() {
        NetworkUtils.getInstance(this);
        return NetworkUtils.isNetworkAvailable();
    }


    //TODO: Define moviePosition as static so as to retain its value
    //      when we return
    private static int moviePosition=0;


    // 1 - Define my data source
    // 2 - Reference my GridView Adapter
    // 3 - Obtain a reference to my RecyclerView
    // 4 - Obtain a reference to my progressBar
    // 5 - Setup the shared preferences manager
    // 6 - Get a reference to the loader manager
    // 7 - Specify the OnClick Listener
    // 8 - Setup the main menu

    // http://myhexaville.com/2017/06/28/android-butterknife-vs-data-binding/
    // http://craftedcourses.io/all-about-butter-knife-part-1/
    // https://proandroiddev.com/how-to-android-dagger-2-10-2-11-butterknife-mvp-part-1-eb0f6b970fd
    // https://www.youtube.com/watch?v=3Zrwi3FFrC8 - video regarding shared preferences
    //
    static int recyclerViewTop;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        //https://www.youtube.com/watch?v=WBbsvqSu0is - sending parcelable bundle between activities
        super.onCreate(savedInstanceState);

            createMainActivity();
                // retrieve all widgets on MainActivity that need initializing
            displayMainActivity();
                // initialize widgets from MainActivity with appropriate data
      //  this.onRestoreInstanceState(savedInstanceState);
    }// end - onCreate(Bundle savedInstanceState)

    //https://www.learnhowtoprogram.com/android/web-service-backends-and-custom-fragments/custom-adapters-with-recyclerview
    private void createMainActivity() {
        // ties variables to their corresponding widgets where appropriate
        setContentView(R.layout.activity_main);
            // display MainActivity

        moviePosterRecyclerView = findViewById(R.id.movie_poster_grid);
            // use RecyclerView as our layout
        moviePosterRecyclerView.setVisibility(View.VISIBLE);
            // make the RecyclerView widget visible
        moviePosterRecyclerView.setHasFixedSize(true);
            // limit its size
        int numberOfColumns = getResources().getInteger(R.integer.num_columns);
            //2 or 4 columns depending on orientation

        // https://stackoverflow.com/questions/36568168/how-to-save-scroll-position-of-recyclerview-in-android
        //Create Layout manager to set the recyclerview to grid

        // Initialize the layout manager
        moviePosterLayoutManager = (moviePosterRecyclerView.getLayoutManager() == null) ?
        new GridLayoutManager(this, numberOfColumns) :  moviePosterRecyclerView.getLayoutManager();
                // create a new layoutManager                       // reuse the existing layoutManager


        // Initialize the adapter for the layout manager
        // create the adapter for the recyclerview (i.e. moviePosterRecyclerView )
        moviePosterAdapter = (moviePosterAdapter == null) ?
        new MoviePosterAdapter(this, moviePosters, R.layout.grid_item)  :  (MoviePosterAdapter) moviePosterRecyclerView.getAdapter();
                    // create a new adapter                                           // reuse the existing adapter

    }// end - createMainActivity()

    public void displayMainActivity( ) {

        // Check for network connectivity before attempting to download  poster data

        // If there is a network connection, get the data
        if (!isInternetAvailable()) { //no internet so fail early

            // Update error message text with no connection error message
            errorMessage = findViewById(R.id.error_message_text_view);
            errorMessage.setVisibility(View.VISIBLE);
            Toast.makeText(this, "You are not online!!!!", Toast.LENGTH_LONG).show();

        } else { // internet is available so proceed

            moviePosterAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View recyclerView, int clickedPosition, ArrayList<MoviePoster> moviePosters) {

                    moviePosition = clickedPosition; // the value of moviePosition will be saved in
                    // the bundle, so establish this equality now
                    // for when that happens later

                    Context moviePosterContext = recyclerView.getContext();

                    MoviePoster currentMovie = moviePosters.get(moviePosition);

                    Intent detailActivityIntent = new Intent(moviePosterContext, DetailActivity.class);
                    

                    String voteAverageString = String.valueOf(new StringBuilder().append(currentMovie.getVoteAverage()).append("/10"));

                    detailActivityIntent.putExtra("moviePosition",moviePosition );

                    detailActivityIntent.putExtra("backdropPath",currentMovie.getBackdropPath() );
                    detailActivityIntent.putExtra("originalTitle",currentMovie.getOriginalTitle() );
                    detailActivityIntent.putExtra("releaseDate", currentMovie.getReleaseDate() );
                    detailActivityIntent.putExtra("voteAverage", voteAverageString);
                    detailActivityIntent.putExtra("rating", currentMovie.getVoteAverage());
                    detailActivityIntent.putExtra("synopsisOverview",currentMovie.getOverview() );

                    moviePosterContext.startActivity(detailActivityIntent);

                }
            });

        }// end - displayMainActivity
// TODO: 1- Make both align

        if (layoutManagerState != null) {
            //moviePosterLayoutManager
            moviePosterLayoutManager.onRestoreInstanceState(layoutManagerState);
            moviePosterRecyclerView.setLayoutManager(moviePosterLayoutManager);

        } else {

            moviePosterRecyclerView.setLayoutManager(moviePosterLayoutManager);
            moviePosterRecyclerView.scrollToPosition(moviePosition);

           // https://newfivefour.com/android-restore-position-or-recyclerview.html
          //  https://stackoverflow.com/questions/38247602/android-how-can-i-get-current-positon-on-recyclerview-that-user-scrolled-to-item/52426802#52426802
        }



        // 4 - Obtain a reference to my progress bar
        progressBar = findViewById(R.id.progressBar);


        // 5 - Setup shared preferences re: sort order
        //     Register MainActivity as an OnPreferenceChangedListener to receive a callback when a
        //     SharedPreference has changed. Please note that we must unregister MainActivity as an
        //     OnSharedPreferenceChanged listener in onDestroy to avoid any memory leaks.
        //     the manager is  the place/resource/database to store the preferences
        setUpSharedPreferences();

        // 6 - Get a reference to the loader manager
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        //  Toast.makeText(this,"You are online!!!!",Toast.LENGTH_LONG).show();
        //https://stackoverflow.com/questions/41267446/how-to-get-loadermanager-initloader-working-within-a-fragment
        //implementation 'com.android.support.v4.app.LoaderManager.LoaderCallbacks'
        //    implementation 'com.android.support.v4.app.LoaderManager.LoaderCallbacks'
        loaderManager.initLoader(MOVIE_LOADER_ID, null, this);//.forceload();
        // errorMessage.setVisibility(View.GONE); // Hide the error message

        // Otherwise, hide the progress bar then display an error message
        // Hide the progress bar before
        progressBar.setVisibility(View.GONE);


    }// end - internet is available

    private void setUpSharedPreferences (){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        // Initialize the orderBy key to be whatever is already indicated in the class
        //TODO: 2 - Our first use of orderBy and it overrides the "" value in the declarations above
                // In this case, sharedpreferences is the bundle, and I'm getting the order_by string
                // from the bundle. However, if no string exists default to the constant noted in
                // the second parameter.
        orderBy = sharedPreferences.getString(
                "order_by",
                CONSTANTS.HIGHEST_RATED_ORDER_KEY); // TODO: 3 - if you don't find anything, use
                                                    // highest rated???


        // now register your listener for changes
         sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu mainMenu) {
        //*MenuInflater inflater
        getMenuInflater().inflate(R.menu.main_menu, mainMenu);
        return true;
    }// end - onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    } // end - onOptionsItemSelected(MenuItem item)

    @Override
    public Loader<List<MoviePoster>> onCreateLoader(int id, Bundle bundle) {

        //TODO: 3 - change sort order here based on user menu selection in SettingsActivity
        orderBy = MovieSortPreferences.getPreferredSortOrder(this);

        String moviePosterString = null;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch(orderBy){

            case "top_rated":
                Log.d(TAG, "LoadInBackground: use URL for highest rated");
                moviePosterString = JsonParser.topRatedUrl;
                editor.putString(DEFAULT_ORDER_BY_KEY, orderBy);// these are key-value pairs
                // TODO: 4- Does this mean we are tracking what is to be shown on the
                //           settings activity window?
                editor.apply();
                //PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
                        // TODO: 3 - sCan I rewrite the preferences.xml file???
                        // Setting the readagain flag to true will ensure that
                        // all unset-default values are set again. That's the summary.
                        // https://stackoverflow.com/questions/10578501/
                //         preferencemanager-setdefaultvalues-readagain-parameter-and-new-preferences
                break;

            case "popular":
                Log.d(TAG, "LoadInBackground: use URL for most popular");

                moviePosterString = JsonParser.mostPopularUrl;
                editor.putString(DEFAULT_ORDER_BY_KEY, orderBy);// these are key-value pairs
                editor.apply();

                break;

        }

        return new MoviePosterLoader(this, moviePosterString);
    }// end - onCreateLoader

    @Override

    public void onLoadFinished(Loader<List<MoviePoster>> loader, List<MoviePoster> moviePosters) {

        progressBar.setVisibility(View.GONE);

        if (moviePosters == null) {
            Log.e("LoadFinished", "Load finished with no movie posters! bailing");
            return;
        }

        // Hide(i.e. setVisibility(View.GONE) ) the progressBar since the data has finished loading
        if (!moviePosters.isEmpty()) {//call a public method from here

            moviePosterAdapter.updateMovies((ArrayList<MoviePoster>) moviePosters);

            // now, set the adapter for the recyclerview
            moviePosterRecyclerView.setAdapter(moviePosterAdapter);

        } else {
            errorMessage.setVisibility(View.VISIBLE);

            // Set empty state text to display "No movies available!"
            errorMessage.setText(R.string.no_movies_available);
        }
        if (moviePosition != GridView.INVALID_POSITION) {
            moviePosterRecyclerView.setScrollX(recyclerViewTop);
        }

    }


    @Override
    public void onLoaderReset(Loader<List<MoviePoster>> loader) {
        //  moviePosterAdapter.notifyDataSetChanged();

    }


    @Override
    // 5 - Setup shared preferences re: sort order
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(key, MODE_PRIVATE).edit();


        String value= sharedPreferences.getString(key, getString(R.string.settings_order_by));
                // this value was stored in the Settings Activity and is being read here

        final String mostPopularSortOrder  = "@string/pref_most_popular_key";
        final String topRatedSortOrder = "@string/pref_top_rated_key";


        boolean PREFERENCES_UPDATED = ( value.equals(getString(R.string.settings_order_top_rated)) ||
                                        value.equals(getString(R.string.settings_order_most_popular)) ) ;

        if(PREFERENCES_UPDATED) {
            if (!value.equals(orderBy)) {//this is for efficiency. should value != orderby(i.e. the
                                        // default sort mode), perform the load below. Should they
                                        // match, there is no need to reload

                orderBy = null;  // because of the test above, we know that orderBy has changed
                orderBy = value; // so reset its value here as it represents the default for the app
                LoaderManager loaderManager = getLoaderManager();

                PREFERENCES_UPDATED = false;

                switch (orderBy) {

                    case "top_rated":
                        orderBy = topRatedSortOrder;

                        //put in bundle
                        editor.putString(DEFAULT_ORDER_BY_KEY, topRatedSortOrder);
                        editor.apply();
                        loaderManager.restartLoader(MOVIE_LOADER_ID, null, this);

                        break;

                    case "popular":
                        orderBy = mostPopularSortOrder;
                        //put in bundle
                        editor.putString(DEFAULT_ORDER_BY_KEY, mostPopularSortOrder);
                        editor.apply();
                        loaderManager.restartLoader(MOVIE_LOADER_ID, null, this);
                        break;

                    default:

                        break;
                }
            }

        }
    }


    @Override
    protected void onPause()
    {
        this.onSaveInstanceState( new Bundle() );
        super.onPause();


    }

    @Override //https://www.youtRube.com/watch?v=tWtaDNJs48o
    // http://qaru.site/questions/144487/recyclerview-store-restore-state-between-activities
    protected void onResume() {
        super.onResume();


        if (layoutManagerState != null) {
            moviePosterLayoutManager.onRestoreInstanceState(layoutManagerState);
        }

    }

    @Override
    protected void onDestroy() {

        // Unregister MainActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this).
                unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }


    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        layoutManagerState = moviePosterLayoutManager.onSaveInstanceState();

        // Save currently selected layout manager.

            outState.putParcelable(CONSTANTS.KEY_LAYOUT_MANAGER_STATE,layoutManagerState);


    }
    @Override
    public void onBackPressed() {

        //do nothing....
    }
} // end of the class MainActivity.java

