package com.nhnacademy.app;

import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.lang3.StringUtils;

public class App {
    public static void main(String[] args) {

        // MovieParser movieParser = new BasicMovieParser();
        MovieParser movieParser = new ApacheCommonsCsvMovieParser();

        try {
            List<Movie> movieList = movieParser.parse("movies.csv");
            for (Movie movie : movieList) {
                System.out.println(movie.getMovie());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class Practice1 {
    public static void main(String[] args) {
        // RandomDataGenerator 인스턴스 생성
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

        // 랜덤한 정수 생성
        int randomInt = randomDataGenerator.nextInt(1, 100); // 1 이상 100 미만의 정수
        System.out.println("Random integer: " + randomInt);

        // java.util.Random을 사용한 랜덤 값 생성
        Random random = new Random();
        int randomInt2 = random.nextInt(100); // 0 이상 100 미만의 정수
        System.out.println("Random integer with java.util.Random: " + randomInt2);
    }
}

class Practice2 {
    public static void main(String[] args) {
        String str = "";

        if (str.isEmpty()) {
            System.out.println("java.lang.String.isEmpty() 빈문자열 확인");
        }

        if (StringUtils.isEmpty(str)) {
            System.out
                    .println("Apache Commons Lang 3.x 의 org.apache.commons.lang3.StringUtils.isEmpty()를 이용한 empty 체크");
        }
    }
}
