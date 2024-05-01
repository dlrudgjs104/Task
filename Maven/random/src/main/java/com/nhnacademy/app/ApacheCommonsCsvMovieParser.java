package com.nhnacademy.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ApacheCommonsCsvMovieParser implements MovieParser {

    @Override
    public InputStream getMovieFileAsStream() {
        return getClass().getClassLoader().getResourceAsStream(MOVIE_FILE_NAME);
    }

    @Override
    public List<Movie> parse(String fileName) throws IOException {
        List<Movie> movies = new ArrayList<>();

        try (InputStream inputStream = getMovieFileAsStream();
                InputStreamReader csvData = new InputStreamReader(inputStream);
                CSVParser csvParser = CSVParser.parse(csvData, CSVFormat.EXCEL)) {

            List<CSVRecord> csvRecordList = csvParser.getRecords();

            for (int record_i = 1; record_i < csvRecordList.size(); record_i++) {
                CSVRecord csvRecord = csvRecordList.get(record_i);

                long movieId = Long.parseLong(csvRecord.get(0));
                String title = csvRecord.get(1);
                String genresLine = csvRecord.get(2);
                String[] genresArray = genresLine.split("\\|");
                Set<String> genres = new HashSet<>();

                for (int i = 0; i < genresArray.length; i++) {
                    genres.add(genresArray[i]);
                }
                Movie movie = new Movie(movieId, title, genres);
                movies.add(movie);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return movies;
    }

}

/*
 * 
 * 
 * List<Movie> movies = new ArrayList<>();
 * 
 * try (InputStream inputStream = getMovieFileAsStream();
 * InputStreamReader reader = new InputStreamReader(inputStream);
 * CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader()))
 * {
 * 
 * for (CSVRecord csvRecord : csvParser) {
 * int movieId = Integer.parseInt(csvRecord.get("movieId"));
 * String title = csvRecord.get("title");
 * String genresLine = csvRecord.get("genres");
 * String[] genresArray = genresLine.split("\\|");
 * Set<String> genres = new HashSet<>();
 * 
 * for (int i = 0; i < genresArray.length; i++) {
 * genres.add(genresArray[i]);
 * }
 * 
 * // 필요한 필드를 추가로 가져와서 Movie 객체를 생성하고 리스트에 추가
 * Movie movie = new Movie(movieId, title, genres);
 * movies.add(movie);
 * }
 * }
 * catch(Exception e){
 * System.out.println(e.getMessage());
 * }
 * 
 * return movies;
 */