package com.example.ded.movies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
public class AppExecutors {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;

    private AppExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor()
                );
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

// --Commented out by Inspection START (07-Jan-19 09:42):
//    public Executor mainThread() {
//        return mainThread;
//    }
// --Commented out by Inspection STOP (07-Jan-19 09:42)

// --Commented out by Inspection START (07-Jan-19 09:51):
//    public Executor networkIO() {
//        return networkIO;
//    }
// --Commented out by Inspection STOP (07-Jan-19 09:51)

// --Commented out by Inspection START (07-Jan-19 10:20):
//    private static class MainThreadExecutor implements Executor {
//        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
//
//        @Override
//        public void execute(@NonNull Runnable command) {
//            mainThreadHandler.post(command);
//        }
//    }
// --Commented out by Inspection STOP (07-Jan-19 10:20)
}
