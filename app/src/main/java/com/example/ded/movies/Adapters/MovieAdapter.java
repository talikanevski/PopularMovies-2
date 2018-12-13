package com.example.ded.movies.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ded.movies.Models.Movie;
import com.example.ded.movies.R;
import com.example.ded.movies.ROOM.FavoriteMovieEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private final Context mContext;

    // Class variables for the List that holds favorite movies data and the Context
    private List<FavoriteMovieEntity> mFavoriteMovie;

    public MovieAdapter(Activity context, ArrayList<Movie> movies) {
        /*
         the second argument is used when the ArrayAdapter is populating a single TextView.
         Because this is a custom adapter for more then 1 TextView , the adapter is not going to use
         this second argument, so it can be any value. Here, I used 0.
         */
        super(context, 0, movies);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        /* Check if the existing view is being reused, otherwise inflate the view**/
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item, parent, false);
        }

        /*Get the Movie object located at this position in the list**/
        final Movie currentMovie = getItem(position);

        /* Find the View in the activity_detail.xml layout with the poster of the current movie**/
        ImageView posterImage = listItemView.findViewById(R.id.grid_poster);

        assert currentMovie != null;
        Picasso.with(mContext)
                .load("https://image.tmdb.org/t/p/w185" + currentMovie.getPoster())
                .into(posterImage);

        /*Return the list activity_detail view that is now showing the appropriate data**/
        return listItemView;
    }


    /**
     * When data changes, this method updates the list of FavoriteMovieEntity
     * and notifies the adapter to use the new values on it
     */
    public void setFavoriteMovies(List<FavoriteMovieEntity> favoriteMovies) {
        mFavoriteMovie = favoriteMovies;
        notifyDataSetChanged();
    }
}
