package com.example.ded.movies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * http://www.vogella.com/tutorials/AndroidParcelable/article.html
 **/
public class Movie implements Parcelable {
    private final String mTitle;
    /**
     * title of the movie
     */
    private final String mOverview;
    /**
     * movie overview
     */
    private final String mReleaseDate;
    /**
     * release date of the movie
     */
    private final String mUserRating;
    /*** user rating */
    private final String mPoster;
    /**
     * movie poster
     */
    private final String mBackdrop;
    /**
     * backdrop
     **/
    private String mId;
    /**
     *The Movie DB id
     **/

    public Movie(String title, String overview, String releaseDate, String userRating, String poster, String backdrop, String id) {
        mTitle = title;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mUserRating = userRating;
        mPoster = poster;
        mBackdrop = backdrop;
        mId = id;
    }

    private Movie(Parcel in) {
        mTitle = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mUserRating = in.readString();
        mPoster = in.readString();
        mBackdrop = in.readString();
        mId = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getId() {
        return mId;
    }

    public void setmId(String id) { this.mId = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mUserRating);
        parcel.writeString(mPoster);
        parcel.writeString(mBackdrop);
        parcel.writeString(mId);
    }
}
