package com.example.ded.movies;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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

@SuppressWarnings("unused")
public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener {
    private static final String LOG_TAG = DetailActivity.class.getName();
    public static final String CURRENT_MOVIE = "current_movie";
    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private TrailerAdapter adapter;
    private ReviewAdapter reviewAdapter;
    private Movie currentMovie;
    //  AppDatabase member variable for the Database
    private AppDatabase mDb;
    private ImageView backdrop;
    private FloatingActionButton fabFavorite;
    private Boolean isFavorite;
    private FavoritesAdapter favoritesAdapter;
    private static final String ID = "id";
    // Constant for default favorite movie id to be used when not in update mode
    private static final int DEFAULT_ID = -1;
    // Extra for the ID to be received after rotation
    private static final String INSTANCE_ID = "instanceId";
    private Trailer firstTrailer;
    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the Content View using DataBindingUtil to the detail_activity layout
        // DataBindUtil.setContentView replaces our normal call of setContent view:
        // " setContentView(R.layout.activity_detail); "
        //Create a data binding instance. This class was generated based on the name of the xml layout
        ActivityDetailBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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
            //noinspection unused
            int mId = savedInstanceState.getInt(INSTANCE_ID, DEFAULT_ID);
        }
        Intent intent = getIntent();
        currentMovie = intent.getParcelableExtra(CURRENT_MOVIE);

        //helps to see my list of favorite movies
//        List<FavoriteMovieEntity> favoriteMoviesMy = mDb.favoriteMovieDao().loadFavoriteMoviesMy();

        /* Find the TextView in the activity_detail.xml layout with title**/
        TextView titleTextView = findViewById(R.id.title);
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "The title of the movie";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.TOP | Gravity.START, 130, 240);
                toast.show();

                vibe.vibrate(100);
            }
        });

        /* Get the version name from the current Movie object and set this text on the name TextView**/
        // Display the title of the current movie in that TextView
        titleTextView.setText(currentMovie.getTitle());

        /* Find the TextView in the activity_detail.xml layout with overview**/
        TextView overviewTextView = findViewById(R.id.overview);
        overviewTextView.setText(currentMovie.getOverview());

        /* Find the TextView in the activity_detail.xml layout with releaseDate**/
        TextView releaseDateTextView = findViewById(R.id.release_date);
        releaseDateTextView.setText(currentMovie.getReleaseDate()); //.substring(0, 4)
        releaseDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "The release date of the movie";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.TOP | Gravity.START, 130, 240);
                toast.show();

                vibe.vibrate(100);
            }
        });

        /* Find the TextView in the activity_detail.xml_detail.xml layout with userRating**/
        TextView userRatingTextView = findViewById(R.id.avg_rating);
        userRatingTextView.setText(currentMovie.getUserRating());
        FloatingActionButton fabRating = findViewById(R.id.avg_rating_fab);
        fabRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "The rating of the movie";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.TOP | Gravity.START, 130, 240);
                toast.show();

                vibe.vibrate(100);
            }
        });

        /* Find the View in the activity_detail.xml_detail.xml layout with the poster of the of the current movie**/
        ImageView posterImage = findViewById(R.id.poster);
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
        fabFavorite = findViewById(R.id.favorite_fab);
        isFavorite = checkIfFavorite();
        setButton(isFavorite);
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(100);
                favoriteButtonClicked();
            }
        });

        // Setup FAB to share the first movie's trailer
        //Implement sharing functionality to allow the user to share the first trailer’s YouTube URL from the movie details screen.
        FloatingActionButton fabShare = findViewById(R.id.share_fab);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(100);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                }
                i.putExtra(Intent.EXTRA_SUBJECT, firstTrailer.getTrailerName());
                i.putExtra(Intent.EXTRA_TEXT, Uri.parse(YOUTUBE_URL + firstTrailer.getTrailerKey()).toString());
                startActivity(Intent.createChooser(i, getString(R.string.share_text_for_chooser) + currentMovie.getTitle()));
            }
        });
    }

    private void setButton(Boolean isFavorite) {
        if (isFavorite) {
            fabFavorite.setImageResource(R.drawable.favorites_red);
        } else {
            fabFavorite.setImageResource(R.drawable.favorites);
        }
    }

    private boolean checkIfFavorite() {
        FavoriteMovieEntity favoriteMovie = mDb.favoriteMovieDao().loadFavoritesByIdMy(currentMovie.getId());
        return (favoriteMovie != null);
    }

    private void favoriteButtonClicked() {
        if (!isFavorite) {
            String title = currentMovie.getTitle();
            String overview = currentMovie.getOverview();
            String releaseDate = currentMovie.getReleaseDate();
            String userRating = currentMovie.getUserRating();
            String poster = currentMovie.getPoster();
            isFavorite = true;
            setButton(true);
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
            toast.setGravity(Gravity.TOP | Gravity.START, 130, 240);
            toast.show();

        } else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    isFavorite = false;
                    setButton(false);
                    mDb.favoriteMovieDao().deleteMovieMy(currentMovie.getId());
                    setUpViewModel();
                }
            });
            Context context = getApplicationContext();
            CharSequence text = "The movie removed from favorites";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP | Gravity.START, 130, 240);
            toast.show();
        }
    }

// --Commented out by Inspection START (07-Jan-19 10:29):
//    private void closeOnError() {
//        finish();
//        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
//    }
// --Commented out by Inspection STOP (07-Jan-19 10:29)

    @Override
    public void onClick(Trailer clickedTrailer) {
        Uri trailerUri = Uri.parse(YOUTUBE_URL + clickedTrailer.getTrailerKey());
        /*Create a new intent to view the trailer URI**/
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, trailerUri);
        /* Send the intent to launch a new activity**/
        startActivity(websiteIntent);
    }

    // In a background thread get ArrayList of the trailers and reviews for selected movie.
    // AsyncTask extends Object java.lang.Object android.os.AsyncTask<Params, Progress, Result>; ==Movie[]
    @SuppressLint("StaticFieldLeak")
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
            List<Object> trailersPlusReviews = new ArrayList<>();
            trailersPlusReviews.add(trailers);
            trailersPlusReviews.add(reviews);
            return trailersPlusReviews;
        }

        @Override
        protected void onPostExecute(List<Object> trailersPlusReviews) {

            if (trailersPlusReviews != null) {
                Trailer[] trailers = (Trailer[]) trailersPlusReviews.get(0);
                if (trailers.length != 0) {
                    firstTrailer = trailers[0];
                    adapter.setTrailerData(trailers);
                } else {
                    View noTrailers = findViewById(R.id.no_trailers_found);
                    noTrailers.setVisibility(View.VISIBLE);
                }

                Review[] reviews = (Review[]) trailersPlusReviews.get(1);
                if (reviews.length != 0) {
                    reviewAdapter.setReviewData(reviews);
                } else {
                    View noReviews = findViewById(R.id.no_reviews_found);
                    noReviews.setVisibility(View.VISIBLE);
                }

            } else {
                Toast.makeText(DetailActivity.this, getString(R.string.no_data), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadTrailersPlusReviews(String movieId) {
        new Task().execute(movieId, null, null);
    }

    private void setUpViewModel() {
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

