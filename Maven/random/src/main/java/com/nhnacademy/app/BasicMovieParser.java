package com.nhnacademy.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicMovieParser implements MovieParser {
    @Override
    public InputStream getMovieFileAsStream() {
        return getClass().getClassLoader().getResourceAsStream(MOVIE_FILE_NAME);
    }

    @Override
    public List<Movie> parse(String fileName) throws IOException {
        List<Movie> movies = new ArrayList<>();

        try (InputStream inputStream = getMovieFileAsStream(); 
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader)) 
        {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    if(parts[0].equals("movieId")){
                        continue;
                    }
                    int movieId = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String genresLine = parts[2];
                    String[] genresArray = genresLine.split("\\|");
                    Set<String> genres = new HashSet<>();

                    for (int i = 0; i < genresArray.length; i++) {
                        genres.add(genresArray[i]);
                    }
                    Movie movie = new Movie(movieId, title, genres);
                    movies.add(movie);
                }
            }
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        return movies;
    }
}
