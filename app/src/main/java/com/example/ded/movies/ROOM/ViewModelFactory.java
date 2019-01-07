package com.example.ded.movies.ROOM;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final int mId;

    public ViewModelFactory (AppDatabase database, int id){
        //two member variables. One for the database and one for the id
        AppDatabase mDb = database;
        mId = id;
    }
//
//    @Override
//    public <T extends ViewModel> T create(Class<T> modelClass) {
//        //noinspection unchecked
//        return (T) new AddTaskViewModel(mDb, mId);
//    }
}
