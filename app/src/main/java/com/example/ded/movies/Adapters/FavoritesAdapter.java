package com.example.ded.movies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private Context mContext;
    public List<FavoriteMovieEntity> favoriteMovieEntityList;
    FavoriteMovieEntity currentFavoriteMovie;
    Movie currentMovie;
    String posterUrl;

    // data is passed into the constructor
    public FavoritesAdapter(Context context, List<FavoriteMovieEntity> favoriteMovies) {
        this.favoriteMovieEntityList = favoriteMovies;
        this.mContext = context;
    }

    @Override
    public FavoritesAdapter.FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.poster_item, parent, false);
        return new FavoritesAdapter.FavoritesAdapterViewHolder(view);
    }

    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder {
        public final ImageView posterImage;
        public final View mView;

        public FavoritesAdapterViewHolder(View view) {
            super(view);
            mView = view;
            /* Find the View in the poster_item.xml layout with the poster of the current movie**/
            posterImage = view.findViewById(R.id.poster_item);
        }

        void bind(final FavoriteMovieEntity currentFavoriteMovie) {
            posterUrl = "https://image.tmdb.org/t/p/w185" + currentFavoriteMovie.getPoster();
            assert currentFavoriteMovie != null;
            Picasso.with(posterImage.getContext())
                    .load(posterUrl)
                    .into(posterImage);
            posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    currentMovie = new Movie(currentFavoriteMovie.getTitle(),
                            currentFavoriteMovie.getOverview(), currentFavoriteMovie.getReleaseDate(),
                            currentFavoriteMovie.getUserRating(), currentFavoriteMovie.getPoster(),
                            currentFavoriteMovie.getBackdrop(), currentFavoriteMovie.getTheMovieDbId());
                    currentMovie.setmId(currentFavoriteMovie.getTheMovieDbId());
                    intent.putExtra(DetailActivity.CURRENT_MOVIE, (Parcelable) currentMovie);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, int position) {
        currentFavoriteMovie = favoriteMovieEntityList.get(position);
        holder.bind(currentFavoriteMovie);
    }

    @Override
    public int getItemCount() {
        return favoriteMovieEntityList != null ? favoriteMovieEntityList.size() : 0;
    }

    /**
     * The interface that receives onItemClickListener messages.
     */
    public interface ListItemClickListener {
        void onClick(int clickedItem);
    }

    /**
     * When data changes, this method updates the list of FavoriteMovieEntity
     * and notifies the adapter to use the new values on it
     */
    public void setFavoriteMovies(List<FavoriteMovieEntity> favoriteMovies) {
        favoriteMovieEntityList = favoriteMovies;
        notifyDataSetChanged();
    }
}

