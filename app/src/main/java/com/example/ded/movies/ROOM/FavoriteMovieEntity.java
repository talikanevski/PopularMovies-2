package com.example.ded.movies.ROOM;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.widget.ImageView;

@Entity(tableName = "favorite_movies")

public class FavoriteMovieEntity {
    //  @PrimaryKey @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) // default is false
    private int id;

    private final String title;
    /**
     * title of the movie
     */
    private final String overview;
    /**
     * movie overview
     */
    private final String releaseDate;
    /**
     * release date of the movie
     */
    private final String userRating;
    /*** user rating */
    private final String poster;
    /**
     * movie poster
     */
    private final String backdrop;
    /**
     * backdrop
     **/
    private final String theMovieDbId;
    /**
     *The Movie DB id
     **/

    // the limitation of Room is it can have only one constructor and I have two!
    //the first one I'll use to create a new favorite movie (it will receive it's id automatically)
    // and the second constructor I''ll use after the FavoriteMovieEntity was created, to be able to delete it if needed
    // so I just add @Ignore to the first one
    @Ignore
    public FavoriteMovieEntity(String title, String overview, String releaseDate, String userRating, String poster, ImageView backdrop, String theMovieDbId) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.poster = poster;
        this.backdrop = String.valueOf(backdrop);
        this.theMovieDbId = theMovieDbId;
    }

    public FavoriteMovieEntity(int id, String title, String overview, String releaseDate, String userRating, String poster, String backdrop, String theMovieDbId) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.poster = poster;
        this.backdrop = backdrop;
        this.theMovieDbId = theMovieDbId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getPoster() {
        return poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getTheMovieDbId() {
        return theMovieDbId;
    }

}
