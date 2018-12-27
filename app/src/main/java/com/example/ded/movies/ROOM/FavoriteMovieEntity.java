package com.example.ded.movies.ROOM;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.widget.ImageView;

@Entity(tableName = "favorite_movies")
// PrimaryKey is the id of the favorite movie.
// with this the id of each favorite movie in the table will be unique
public class FavoriteMovieEntity {
    @NonNull
    //  @PrimaryKey @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) // default is false
    private int id;

    private String title;
    /**
     * title of the movie
     */
    private String overview;
    /**
     * movie overview
     */
    private String releaseDate;
    /**
     * release date of the movie
     */
    private String userRating;
    /*** user rating */
    private String poster;
    /**
     * movie poster
     */
    private String backdrop;
    /**
     * backdrop
     **/
    private String theMovieDbId;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTheMovieDbId() {
        return theMovieDbId;
    }

    public void setTheMovieDbId(String theMovieDbId) {
        this.theMovieDbId = theMovieDbId;
    }
}
