package com.example.ded.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener {

    public static final String CURRENT_MOVIE = "current_movie";
    private RecyclerView mTrailersRecyclerView;
    ActivityDetailBinding mBinding; //Create a data binding instance. This class was generated based on the name of the xml layout
    private com.example.ded.movies.TrailerAdapter adapter;

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
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Trailer clickedItem) {

    }

    // in a background thread get ArrayList of the trailers for selected movie
    // AsyncTask extends Object java.lang.Object android.os.AsyncTask<Params, Progress, Result>; ==Movie[]
    public class TrailersTask extends AsyncTask<String, String, List<Object>> {

        @Override
        protected List<Object> doInBackground(String... params) {
            URL trailerUrl = QueryUtils.trailersReviewsUrl(params[0]);
            try {
                String response = QueryUtils.getResponseFromHttpUrl(trailerUrl);
                List<Trailer> trailer = QueryUtils.extractTrailersFromJson(this, response);
                List<Object> trailers = new ArrayList<Object>();
                trailers.add(trailer);
                return trailers;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Object> listOfTrailers) {
            if (listOfTrailers != null) {
                Trailer[] trailers = (Trailer[]) listOfTrailers.get(1);
                adapter.setTrailerData(trailers);

            } else {
                Toast.makeText(DetailActivity.this, getString(R.string.no_data), Toast.LENGTH_LONG).show();
            }
        }
    }
}

