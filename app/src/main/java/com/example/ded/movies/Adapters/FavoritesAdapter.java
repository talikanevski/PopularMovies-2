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
import com.example.ded.movies.R;
import com.example.ded.movies.ROOM.FavoriteMovieEntity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    public static FavoritesAdapter.FavoritesAdapterViewHolder FavoritesAdapterViewHolder;//TODO   I am not sure about this at all....
    Context mContext;
    public List<FavoriteMovieEntity> favoriteMovieEntityList;
    FavoriteMovieEntity currentMovie;
    String posterUrl;
    private ListItemClickListener mClickListener;

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
        public ImageView posterImage;
        public final View mView;

        public FavoritesAdapterViewHolder(View view) {
            super(view);
            mView = view;
            /* Find the View in the poster_item.xml layout with the poster of the current movie**/
            posterImage = view.findViewById(R.id.poster_item);
        }

        void bind(final FavoriteMovieEntity currentMovie) {
            posterUrl = "https://image.tmdb.org/t/p/w185" + currentMovie.getPoster();
            assert currentMovie != null;
            Picasso.with(posterImage.getContext())
                    .load(posterUrl)
                    .into(posterImage);
            posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.CURRENT_MOVIE, (Parcelable) currentMovie);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, int position) {
        currentMovie = favoriteMovieEntityList.get(position);
        holder.bind(currentMovie);
    }

    @Override
    public int getItemCount() {
        return favoriteMovieEntityList != null ? favoriteMovieEntityList.size() : 0;
    }

    public void setClickListener(ListItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * The interface that receives onItemClickListener messages.
     */
    public interface ListItemClickListener {
        void onItemClickListener(int clickedItem);
    }

    /**
     * When data changes, this method updates the list of FavoriteMovieEntity
     * and notifies the adapter to use the new values on it
     */
    public void setFavoriteMovies(List<FavoriteMovieEntity> favoriteMovies) {
        favoriteMovieEntityList = favoriteMovies;
        notifyDataSetChanged();
    }

    public List<FavoriteMovieEntity> getFavoriteMovies() {
        return favoriteMovieEntityList;
    }

    // convenience method for getting data at click position
    FavoriteMovieEntity getItem(int id) {
        return favoriteMovieEntityList.get(id);
    }

}

