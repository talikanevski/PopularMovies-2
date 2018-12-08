package com.example.ded.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ded.movies.databinding.ActivityDetailBinding;//This class was generated based on the name of the xml layout
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.ded.movies.TrailerAdapter.YOUTUBE_BASE_URL;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener {

    public static final String CURRENT_MOVIE = "current_movie";
    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    ActivityDetailBinding mBinding; //Create a data binding instance. This class was generated based on the name of the xml layout
    private com.example.ded.movies.TrailerAdapter adapter;
    private com.example.ded.movies.ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the Content View using DataBindingUtil to the detail_activity layout
        // DataBindUtil.setContentView replaces our normal call of setContent view:
        // " setContentView(R.layout.activity_detail); "\
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        // set up recyclerview and adapter to display the trailers
        LinearLayoutManager trailersLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.rvTrailers.setLayoutManager(trailersLayoutManager);
        adapter = new com.example.ded.movies.TrailerAdapter( this);
        mBinding.rvTrailers.setAdapter(adapter);

        // set up recyclerview and adapter to display the reviews
        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.rvReviews.setLayoutManager(reviewsLayoutManager);
        reviewAdapter = new com.example.ded.movies.ReviewAdapter( this);
        mBinding.rvReviews.setAdapter(reviewAdapter);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        } else {
            Movie currentMovie = intent.getParcelableExtra(CURRENT_MOVIE);

            /* Find the TextView in the activity_detail.xml layout with title**/
            TextView titleTextView = findViewById(R.id.title);

            /* Get the version name from the current Movie object and set this text on the name TextView**/
            // Display the title of the current movie in that TextView
            titleTextView.setText(currentMovie.getTitle());

            /* Find the TextView in the activity_detail.xml layout with overview**/
            TextView overviewTextView = findViewById(R.id.overview);
            overviewTextView.setText(currentMovie.getOverview());

            /* Find the TextView in the activity_detail.xml layout with releaseDate**/
            TextView releaseDateTextView = findViewById(R.id.release_date);
            releaseDateTextView.setText(currentMovie.getReleaseDate().substring(0, 4));

            /* Find the TextView in the activity_detail.xml_detail.xml layout with userRating**/
            TextView userRatingTextView = findViewById(R.id.avg_rating);
            userRatingTextView.setText(currentMovie.getUserRating());

            /* Find the View in the activity_detail.xml_detail.xml layout with the poster of the of the current movie**/
            ImageView posterImage = findViewById(R.id.poster);

            Picasso.with(this)
                    .load("https://image.tmdb.org/t/p/w185" + currentMovie.getPoster())
                    .into(posterImage);

            /* Find the View in the activity_detail.xml layout with the poster of the of the current movie**/
            ImageView backdrop = findViewById(R.id.backdrop);

            Picasso.with(this)
                    .load("https://image.tmdb.org/t/p/w185" + currentMovie.getPoster())
                    .into(backdrop);
            loadTrailersPlusReviews(currentMovie.getId());
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

    // in a background thread get ArrayList of the trailers and reviews for selected movie
    // AsyncTask extends Object java.lang.Object android.os.AsyncTask<Params, Progress, Result>; ==Movie[]
    public class Task extends AsyncTask<String, String, List<Object>> {

        @Override
        protected List<Object> doInBackground(String... params) {
            URL trailersReviewsUrl = QueryUtils.trailersReviewsUrl(params[0], params[1]);

            String response = null;
            try {
                response = QueryUtils.getResponseFromHttpUrl(trailersReviewsUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Trailer []trailers = new Trailer[0];
            try {
                trailers = QueryUtils.extractTrailersFromJson(this, response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Review [] reviews = new Review[0];
            try {
                reviews = QueryUtils.extractReviewsFromJson(this, response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            List<Object> trailersPlusReviews = new ArrayList<Object>();
                trailersPlusReviews.add(trailers);
                trailersPlusReviews.add(reviews);
                return trailersPlusReviews;
        }

        @Override
        protected void onPostExecute(List<Object> trailersPlusReviews) {
            if (trailersPlusReviews != null) {
                Trailer[] trailers = (Trailer[]) trailersPlusReviews.get(0);
                adapter.setTrailerData(trailers);

                Review [] reviews = (Review[]) trailersPlusReviews.get(1);
                reviewAdapter.setReviewData(reviews);

            } else {
                Toast.makeText(DetailActivity.this, getString(R.string.no_data), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadTrailersPlusReviews(String movieId){
        new Task().execute(movieId, null, null);
    }
}

