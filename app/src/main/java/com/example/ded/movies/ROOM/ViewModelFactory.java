package com.example.ded.movies.ROOM;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    //two member variables. One for the database and one for the id
    private final AppDatabase mDb;
    private final int mId;

    public ViewModelFactory (AppDatabase database, int id){
        mDb = database;
        mId = id;
    }
//
//    @Override
//    public <T extends ViewModel> T create(Class<T> modelClass) {
//        //noinspection unchecked
//        return (T) new AddTaskViewModel(mDb, mId);
//    }
}
