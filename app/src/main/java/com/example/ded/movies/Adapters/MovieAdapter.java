package com.example.ded.movies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ded.movies.DetailActivity;
import com.example.ded.movies.Models.Movie;
import com.example.ded.movies.R;
import com.example.ded.movies.ROOM.FavoriteMovieEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    Context mContext;
    public List<Movie> movieList;
    Movie currentMovie;
    String posterUrl;
    /**
     * An on-click handler that  defined to make it easy for an Activity to interface with
     * RecyclerView
     */
    private static MovieAdapter.ListItemClickListener mOnClickListener;


    /**
     * Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener
     **/
    public void setClickListener(MovieAdapter.ListItemClickListener movieAdapterOnClickHandler) {
        mOnClickListener = movieAdapterOnClickHandler;
    }
    // data is passed into the constructor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.movieList = movies;
        this.mContext = context;

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.poster_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    public static class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public static ImageView posterImage = null;
        public final View mView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mView = view;
            /* Find the View in the poster_item.xml layout with the poster of the current movie**/
            posterImage = view.findViewById(R.id.poster);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onClick(getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        /*Get the Movie object located at this position in the list**/
//        final int intCurrentMovie = movieAdapterViewHolder.getAdapterPosition(); // TODO Kxm....Hm...
        currentMovie = movieList.get(position);

        posterUrl = "https://image.tmdb.org/t/p/w185" + currentMovie.getPoster();
        assert currentMovie != null;
        Picasso.with(mContext)
                .load(posterUrl)
                .into(MovieAdapterViewHolder.posterImage);
//        MovieAdapterViewHolder.posterImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra(DetailActivity.CURRENT_MOVIE, currentMovie);
//
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onClick(int clickedItem);
    }

    /**
     * When data changes, this method updates the list of FavoriteMovieEntity
     * and notifies the adapter to use the new values on it
     */
    public void setFavoriteMovies(List<FavoriteMovieEntity> favoriteMovies) {
//        mFavoriteMovie = favoriteMovies;
        notifyDataSetChanged();
    }

    // convenience method for getting data at click position
    Movie getItem(int id) {
        return movieList.get(id);
    }

}

