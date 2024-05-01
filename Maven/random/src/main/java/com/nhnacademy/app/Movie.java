package com.nhnacademy.app;

import java.util.Set;

public class Movie {
    private long movieId;
    private String title;
    private Set<String> genres;

    public Movie(long movieId, String title, Set<String> genres){
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }

    public String getMovie(){
        return String.format("movieId: %d, title: %s, genres: %s", movieId, title, genres);
    }
}
