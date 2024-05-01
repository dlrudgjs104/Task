package com.nhnacademy.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface MovieParser {
    String MOVIE_FILE_NAME = "movies.csv";

    List<Movie> parse(String fileName) throws IOException;

    public InputStream getMovieFileAsStream();
}
