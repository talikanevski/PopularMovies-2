package com.example.ded.movies;

public class Trailer {
    String trailerName;
    String trailerKey;

    public Trailer(String name, String key) {}

    public String getTrailerName () {return trailerName;}

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getTrailerKey () {return trailerKey;}

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }
}
