package com.example.ded.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ded.movies.Adapters.FavoritesAdapter;
import com.example.ded.movies.Adapters.MovieAdapter;
import com.example.ded.movies.Adapters.ReviewAdapter;
import com.example.ded.movies.Adapters.TrailerAdapter;
import com.example.ded.movies.Models.Movie;
import com.example.ded.movies.Models.Review;
import com.example.ded.movies.Models.Trailer;
import com.example.ded.movies.ROOM.AppDatabase;
import com.example.ded.movies.ROOM.FavoriteMovieEntity;
import com.example.ded.movies.databinding.ActivityDetailBinding;//This class was generated based on the name of the xml layout

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.squareup.picasso.Picasso.with;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener {
    private static final String LOG_TAG = DetailActivity.class.getName();
    public static final String CURRENT_MOVIE = "current_movie";
    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private ActivityDetailBinding mBinding; //Create a data binding instance. This class was generated based on the name of the xml layout
    private TrailerAdapter adapter;
    private ReviewAdapter reviewAdapter;
    public Movie currentMovie;
    //  AppDatabase member variable for the Database
    private AppDatabase mDb;
    TextView titleTextView;
    TextView overviewTextView;
    TextView releaseDateTextView;
    TextView userRatingTextView;
    ImageView posterImage;
    ImageView backdrop;
    FloatingActionButton fabFavorite;
    FloatingActionButton fabShare;
    FloatingActionButton fabRating;
    Boolean isFavorite;
    private FavoritesAdapter favoritesAdapter;
    public static final String ID = "id";
    // Constant for default favorite movie id to be used when not in update mode
    private static final int DEFAULT_ID = -1;
    public static final String EXTRA_ID = "extraTaskId";
    // Extra for the ID to be received after rotation
    public static final String INSTANCE_ID = "instanceId";
    public List<Object> trailersPlusReviews;
    public Trailer firstTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the Content View using DataBindingUtil to the detail_activity layout
        // DataBindUtil.setContentView replaces our normal call of setContent view:
        // " setContentView(R.layout.activity_detail); "
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        // set up RecyclerView and adapter to display the trailers
        LinearLayoutManager trailersLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.rvTrailers.setLayoutManager(trailersLayoutManager);
        adapter = new TrailerAdapter(this);
        mBinding.rvTrailers.setAdapter(adapter);

        // set up RecyclerView and adapter to display the reviews
        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.rvReviews.setLayoutManager(reviewsLayoutManager);
        reviewAdapter = new ReviewAdapter(this);
        mBinding.rvReviews.setAdapter(reviewAdapter);

        //  Initializing member variable for the data base
        mDb = AppDatabase.getInstance(getApplicationContext());
        favoritesAdapter = new FavoritesAdapter(this, new ArrayList<FavoriteMovieEntity>());
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ID)) {
            int mId = savedInstanceState.getInt(INSTANCE_ID, DEFAULT_ID);
        }
        Intent intent = getIntent();
        currentMovie = intent.getParcelableExtra(CURRENT_MOVIE);

        //helps to see my list of favorite movies
//        List<FavoriteMovieEntity> favoriteMoviesMy = mDb.favoriteMovieDao().loadFavoriteMoviesMy();

        if (intent == null) {
            closeOnError();
        }
        if (intent != null && intent.hasExtra(ID)) {

        } else {
            /* Find the TextView in the activity_detail.xml layout with title**/
            titleTextView = findViewById(R.id.title);
            final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getApplicationContext();
                    CharSequence text = "The title of the movie";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.TOP|Gravity.START, 130, 240);
                    toast.show();

                    vibe.vibrate(1000);
                }
            });

            /* Get the version name from the current Movie object and set this text on the name TextView**/
            // Display the title of the current movie in that TextView
            titleTextView.setText(currentMovie.getTitle());

            /* Find the TextView in the activity_detail.xml layout with overview**/
            overviewTextView = findViewById(R.id.overview);
            overviewTextView.setText(currentMovie.getOverview());

            /* Find the TextView in the activity_detail.xml layout with releaseDate**/
            releaseDateTextView = findViewById(R.id.release_date);
            releaseDateTextView.setText(currentMovie.getReleaseDate()); //.substring(0, 4)
            releaseDateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getApplicationContext();
                    CharSequence text = "The release date of the movie";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.TOP|Gravity.START, 130, 240);
                    toast.show();

                    vibe.vibrate(1000);
                }
            });

            /* Find the TextView in the activity_detail.xml_detail.xml layout with userRating**/
            userRatingTextView = findViewById(R.id.avg_rating);
            userRatingTextView.setText(currentMovie.getUserRating());
            fabRating = findViewById(R.id.avg_rating_fab);
            fabRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getApplicationContext();
                    CharSequence text = "The rating of the movie";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.TOP|Gravity.START, 130, 240);
                    toast.show();

                    vibe.vibrate(1000);
                }
            });

            /* Find the View in the activity_detail.xml_detail.xml layout with the poster of the of the current movie**/
            posterImage = findViewById(R.id.poster);
            with(this)
                    .load("https://image.tmdb.org/t/p/w185" + currentMovie.getPoster())
                    .into(posterImage);

            /* Find the View in the activity_detail.xml layout with the poster of the of the current movie**/
            backdrop = findViewById(R.id.backdrop);

            with(this)
                    .load("https://image.tmdb.org/t/p/w185" + currentMovie.getPoster())
                    .into(backdrop);

            loadTrailersPlusReviews(currentMovie.getId());

            // Setup FAB to add the movie to favorites
            fabFavorite = (FloatingActionButton) findViewById(R.id.favorive_fab);
            isFavorite = checkIfFavorite();
            setButton(isFavorite);
            fabFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteButtonClicked();
                }
            });

            // Setup FAB to share the first movie's trailer
            //Implement sharing functionality to allow the user to share the first trailer’s YouTube URL from the movie details screen.
            fabShare = (FloatingActionButton) findViewById(R.id.share_fab);
            fabShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.setType("text/plain");
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_TEXT, "");
                    // Add data to the intent, the receiving app will decide
                    // what to do with it.

                    i.putExtra(Intent.EXTRA_SUBJECT, firstTrailer.getTrailerName());
                    i.putExtra(Intent.EXTRA_TEXT, Uri.parse(YOUTUBE_URL + firstTrailer.getTrailerKey()));

                    startActivity(Intent.createChooser(i, getString(R.string.share_text_for_chooser) + currentMovie.getTitle()));

                    startActivity(i);
                }
            });
        }
    }

    public void setButton(Boolean isFavorite) {
        if (isFavorite) {
            fabFavorite.setImageResource(R.drawable.favorites_red);
        } else {
            fabFavorite.setImageResource(R.drawable.favorites);
        }
    }

    public boolean checkIfFavorite() {
        FavoriteMovieEntity favoriteMovie = mDb.favoriteMovieDao().loadFavoritesByIdMy(currentMovie.getId());
        return (favoriteMovie != null);
    }

    public void favoriteButtonClicked() {
        if (!isFavorite) {
            String title = currentMovie.getTitle();
            String overview = currentMovie.getOverview();
            String releaseDate = currentMovie.getReleaseDate();
            String userRating = currentMovie.getUserRating();
            String poster = currentMovie.getPoster();
            isFavorite = true;
            setButton(isFavorite);
            // Creation FavoriteMovieEntity variable using the variables defined above
            final FavoriteMovieEntity favoriteMovieEntity;
            favoriteMovieEntity = new FavoriteMovieEntity(title, overview, releaseDate, userRating, poster, backdrop, currentMovie.getId());
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteMovieDao().insertMovie(favoriteMovieEntity);
                    setUpViewModel();
                }
            });
            Context context = getApplicationContext();
            CharSequence text = "The movie added to favorites";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP|Gravity.START, 130, 240);
            toast.show();

        } else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    isFavorite = false;
                    setButton(isFavorite);
                    mDb.favoriteMovieDao().deleteMovieMy(currentMovie.getId());
                    setUpViewModel();
                }
            });
            Context context = getApplicationContext();
            CharSequence text = "The movie removed from favorites";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP|Gravity.START, 130, 240);
            toast.show();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Trailer clickedTrailer) {
        Uri trailerUri = Uri.parse(YOUTUBE_URL + clickedTrailer.getTrailerKey());
        /**Create a new intent to view the trailer URI**/
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, trailerUri);
        /** Send the intent to launch a new activity**/
        startActivity(websiteIntent);
    }

    // In a background thread get ArrayList of the trailers and reviews for selected movie.
    // AsyncTask extends Object java.lang.Object android.os.AsyncTask<Params, Progress, Result>; ==Movie[]
    public class Task extends AsyncTask<String, String, List<Object>> {

        @Override
        protected List<Object> doInBackground(String... params) {
            URL trailersReviewsUrl = Utils.trailersReviewsUrl(params[0], params[1]);

            String response = null;
            try {
                response = Utils.getResponseFromHttpUrl(trailersReviewsUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Trailer[] trailers = new Trailer[0];
            try {
                trailers = Utils.extractTrailersFromJson(this, response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Review[] reviews = new Review[0];
            try {
                reviews = Utils.extractReviewsFromJson(this, response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            trailersPlusReviews = new ArrayList<Object>();
            trailersPlusReviews.add(trailers);
            trailersPlusReviews.add(reviews);
            return trailersPlusReviews;
        }

        @Override
        protected void onPostExecute(List<Object> trailersPlusReviews) {
            if (trailersPlusReviews != null) {
                Trailer[] trailers = (Trailer[]) trailersPlusReviews.get(0);
                firstTrailer = trailers[0];
                adapter.setTrailerData(trailers);

                Review[] reviews = (Review[]) trailersPlusReviews.get(1);
                reviewAdapter.setReviewData(reviews);

            } else {
                Toast.makeText(DetailActivity.this, getString(R.string.no_data), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadTrailersPlusReviews(String movieId) {
        new Task().execute(movieId, null, null);
    }

    public void setUpViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavoriteMovies().observe(this, new Observer<List<FavoriteMovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovieEntity> favoriteMovieEntities) {
                Log.d(LOG_TAG, "Updating list of favorite movies from LiveData in ViewModel, DetailActivity");
                favoritesAdapter.setFavoriteMovies(favoriteMovieEntities);
            }
        });
    }
}

