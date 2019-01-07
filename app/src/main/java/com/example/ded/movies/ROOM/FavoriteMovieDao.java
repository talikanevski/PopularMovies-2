package com.example.ded.movies.ROOM;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

// Data Access Object class.
@SuppressWarnings("unused")
@Dao
public interface FavoriteMovieDao {

    //We define our queries as strings and pass them as a parameter to @Query.
    // Each @Query annotation is paired with a method.
    // When the paired method is called, the query gets executed.
    @Query("SELECT * FROM favorite_movies ORDER BY id")
    LiveData<List<FavoriteMovieEntity>> loadFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(FavoriteMovieEntity favoriteMovie);

    @SuppressWarnings("unused")
    @Query("SELECT * FROM favorite_movies ORDER BY id")
    List<FavoriteMovieEntity> loadFavoriteMoviesMy();

// --Commented out by Inspection START (07-Jan-19 09:44):
//    @Delete
//    void deleteMovie(FavoriteMovieEntity favoriteMovie);
// --Commented out by Inspection STOP (07-Jan-19 09:44)

    @Query("DELETE FROM favorite_movies WHERE theMovieDbId = :id")
    void deleteMovieMy(String id);

// --Commented out by Inspection START (07-Jan-19 09:52):
//    @Query("SELECT * FROM favorite_movies WHERE theMovieDbId = :id")
//    LiveData<FavoriteMovieEntity> loadFavoritesById (String id);
// --Commented out by Inspection STOP (07-Jan-19 09:52)

    @Query("SELECT * FROM favorite_movies WHERE theMovieDbId = :id")
    FavoriteMovieEntity loadFavoritesByIdMy (String id);
}
