package com.example.ded.movies.ROOM;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

// Data Access Object class.
@Dao
public interface FavoriteMovieDao {

    //We define our queries as strings and pass them as a parameter to @Query.
    // Each @Query annotation is paired with a method.
    // When the paired method is called, the query gets executed.
    // TODO  think if it's better to order by userRating or something else.
    @Query("SELECT * FROM favorite_movies ORDER BY id")
    LiveData<List<FavoriteMovieEntity>> loadFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(FavoriteMovieEntity favoriteMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)// TODO: to think if I need it or how can I use it
    void updateMovie(FavoriteMovieEntity favoriteMovie);

    @Delete
    void deleteMovie(FavoriteMovieEntity favoriteMovie);

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    FavoriteMovieEntity loadFavoritesById (int id);
}
