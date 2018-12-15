package com.example.ded.movies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ded.movies.Adapters.MovieAdapter;
import com.example.ded.movies.Models.Movie;
import com.example.ded.movies.ROOM.AppDatabase;
import com.example.ded.movies.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>, MovieAdapter.ListItemClickListener {
    private static final String LOG_TAG = MainActivity.class.getName();

    public static final String API_Key_Label = "api_key=";
    public static final String API_Key = BuildConfig.ApiKey;
    public static final String THE_MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULAR = "popular?";
    private static final String TOP_RATED = "top_rated?";
    private String THE_MOVIE_DB_URL = THE_MOVIE_DB_BASE_URL + POPULAR + API_Key_Label + API_Key; // by default
// https://api.themoviedb.org/3/movie/335983?api_key=27489f2914bd9f638025496deaad801e&append_to_response=reviews%2Cvideos
    /**
     * Constant value for the movies loader ID. We can choose any integer.
     */
    private static final int MOVIES_LOADER_ID = 1;

    /**
     * Adapter for the list of movies
     */
    private MovieAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     **/

    ActivityMainBinding mBinding;
    //Create a data binding instance. This class was generated based on the name of the xml layout
    private RecyclerView mRv;
    private TextView mEmptyView;
    private ProgressBar mLoadingSpinner;

    //  Creation AppDatabase member variable for the Database
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "Test: Main Activity onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the Content View using DataBindingUtil to the activity_main layout
        // DataBindUtil.setContentView replaces our normal call of setContent view:
        // " setContentView(R.layout.activity_main); "
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // set up recyclerView and adapter to display the posters
        mRv = mBinding.rv;
        mEmptyView = mBinding.emptyView;
        mLoadingSpinner = mBinding.loadingSpinner;
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRv.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        mRv.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        /* In case that there is no internet connection:
          I don't want to show that "No movies found" -
          I want to show that "No internet connection"
          Get a reference to the ConnectivityManager to check state of network connectivity**/

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        /*Get details on the currently active default data network**/
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        /* If there is a network connection, fetch data**/
        if (networkInfo != null && networkInfo.isConnected()) {

            /* to retrieve a movie info, we need to get the loader manager
               and tell the loader manager to initialize the loader with the specified ID,
               the second argument allows us to pass a bundle of additional information, which we'll skip.
               The third argument is what object should receive the LoaderCallbacks
               (and therefore, the data when the load is complete!) - which will be this activity.
               This code goes inside the onCreate() method of the MainActivity,
               so that the loader can be initialized as soon as the app opens.**/

            /*Get a reference to the LoaderManager, in order to interact with loaders.**/
            final LoaderManager loaderManager = getLoaderManager();

            /*Initialize the loader. Pass in the int ID constant defined above and pass in null for
             // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
             // because this activity implements the LoaderCallbacks interface).**/

            Log.i(LOG_TAG, "Test: calling initLoader");

            loaderManager.initLoader(MOVIES_LOADER_ID, null, this);

            /* if internet connection got lost and than back, we'll see mEmptyView
              with text "no internet connection"
              we need to reload the app and while it's reloading it's better to see a spinner**/
            mEmptyView.setOnClickListener(new View.OnClickListener() {//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!
                @Override
                public void onClick(View v) {
                    reload();
                    View loading = findViewById(R.id.loading_spinner);
                    loading.setVisibility(View.VISIBLE);
                }
            });
        } else {/* Otherwise, display error
         // First, hide loading indicator so error message will be visible**/
            View loading = findViewById(R.id.loading_spinner);
            loading.setVisibility(View.GONE);

            /* Update empty state with no connection error message**/
            mEmptyView.setText(R.string.no_internet);
        }

        //  Initialization of member variable for the data base
        mDb = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "Test:  onCreateLoader called");

        // Create a new loader for the given URL
        return new MovieLoader(this, THE_MOVIE_DB_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        Log.i(LOG_TAG, "Test:  onLoadFinished called");

        ProgressBar loading = findViewById(R.id.loading_spinner);
        loading.setVisibility(View.GONE);

        /* in case the internet connection got lost in the middle**/
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Set empty state text to display "No movies found."
            mEmptyView.setText(R.string.no_movies);
        } else// Update empty state with no connection error message
        {
            mEmptyView.setText(R.string.no_internet);
        }

        // Clear the adapter of previous movies data// TODO pay attention to modification I've made- it might be wrong
//        mAdapter.clear();
//        mAdapter.notifyDataSetChanged();

        // If there is a valid list of movies, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            movies.addAll(movies);
        }
        mAdapter = new MovieAdapter(this, movies);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        Log.i(LOG_TAG, "Test:  onLoaderReset called");

        /* Loader reset, so we can clear out our existing data.**/
//        mAdapter.clear();// TODO pay attention to modification I've made- it might be wrong
        mAdapter.notifyDataSetChanged();

    }

    private void reload() {
        getLoaderManager().restartLoader(MOVIES_LOADER_ID, null, this);

    }

    @Override
    /* This method initialize the contents of the Activity's options menu.
     /** onCreateOptionsMenu() inflates the Options Menu specified in the XML
      when the MainActivity opens up, it actually make it visible.**/
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the Options Menu we specified in XML**/
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * This method is where we can setup the specific action
     * that occurs when any of the items in the Options Menu are selected.
     * <p>
     * This method passes the MenuItem that is selected:
     * <p>
     * public boolean onOptionsItemSelected(MenuItem item)
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_popular:
                THE_MOVIE_DB_URL = THE_MOVIE_DB_BASE_URL + POPULAR + API_Key_Label + API_Key;
                new MovieLoader(this, THE_MOVIE_DB_URL);
                reload();
                return true;
            case R.id.navigation_top_rated:
                THE_MOVIE_DB_URL = THE_MOVIE_DB_BASE_URL + TOP_RATED + API_Key_Label + API_Key;
                new MovieLoader(this, THE_MOVIE_DB_URL);
                reload();
                return true;
            case R.id.navigation_favorites:
                // TODO (1) add what need to be added
                reload();
                return true;
        }
        return false;
    }

    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an FAB that add the movie to favorites,
     * so this re-queries the database data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        //  Call the adapter's setFavoriteMovies method using the result
        // of the loadFavoriteMovies method from the FavoriteMovieDao
        //TODO
//        mAdapter.setFavoriteMovies(mDb.FavoriteMovieDao().loadFavoriteMovies());
    }

    @Override
    public void onClick(int clickedItem) {
//        Movie currentMovie = mAdapter.getItem(position);
//        moviesGridView.setAdapter(mAdapter);
//        launchDetailActivity(currentMovie);
    }
//
//    private void launchDetailActivity(Movie currentMovie) {
//        Intent intent = new Intent(this, DetailActivity.class);
//        intent.putExtra(DetailActivity.CURRENT_MOVIE, currentMovie);
//        startActivity(intent);
//    }
}

