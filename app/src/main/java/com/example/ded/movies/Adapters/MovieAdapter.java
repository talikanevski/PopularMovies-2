package com.example.ded.movies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ded.movies.DetailActivity;
import com.example.ded.movies.Models.Movie;
import com.example.ded.movies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Context mContext;
    private final List<Movie> movieList;

    // data is passed into the constructor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.movieList = movies;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.poster_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        final ImageView posterImage;
        @SuppressWarnings("unused")
        final View mView;

        MovieAdapterViewHolder(View view) {
            super(view);
            mView = view;
            /* Find the View in the poster_item.xml layout with the poster of the current movie**/
            posterImage = view.findViewById(R.id.poster_item);
        }

        void bind(final Movie currentMovie) {
            String posterUrl = "https://image.tmdb.org/t/p/w185" + currentMovie.getPoster();
            Picasso.with(posterImage.getContext())
                    .load(posterUrl)
                    .into(posterImage);
            posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.CURRENT_MOVIE, currentMovie);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie currentMovie = movieList.get(position);
        movieAdapterViewHolder.bind(currentMovie);
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    /**
     * The interface that receives onItemClickListener messages.
     */
    @SuppressWarnings({"unused", "EmptyMethod"})
    public interface ListItemClickListener {
        void onClick(int clickedItem);
    }

    /*
      When data changes, this method updates the list of FavoriteMovieEntity
      and notifies the adapter to use the new values on it
     */
}

