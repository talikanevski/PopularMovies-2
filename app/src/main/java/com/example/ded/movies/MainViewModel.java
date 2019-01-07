package com.example.ded.movies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.ded.movies.ROOM.AppDatabase;
import com.example.ded.movies.ROOM.FavoriteMovieEntity;

import java.util.List;

class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    //Adding a favoriteMovies member variable for a list of FavoriteMovieEntity objects wrapped in a LiveData
    private final LiveData<List<FavoriteMovieEntity>> favoriteMovies;

    public MainViewModel(Application application) {
        super(application);
        // In the constructor use the loadFavoriteMovies of the favoriteMovieDao to initialize the tasks variable
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving favorite movies from the DataBase");
        favoriteMovies = database.favoriteMovieDao().loadFavoriteMovies();
    }

    // Creation of a getter for the favoriteMovies variable
    public LiveData<List<FavoriteMovieEntity>> getFavoriteMovies() {
        return favoriteMovies;
    }
}
