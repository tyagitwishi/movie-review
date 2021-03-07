package com.twishi.main;

import java.util.ArrayList;
import java.util.List;

public class main {

    private static MoviesImpl moviesImpl = new MoviesImpl();

    public static void main(String[] args) {

        addMovie("\"Don\" released in Year 2006 for Genre \"Action\" & \"Comedy\"");
        addMovie("\"Tiger\" released in Year 2008 for Genre \"Drama\"");
        addMovie("\"Padmaavat\" released in Year 2006 for Genre \"Comedy\"");
        addMovie("\"Lunchbox\" released in Year 2021 for Genre \"Drama\"");
        addMovie("\"Guru\" released in Year 2006 for Genre \"Drama\"");
        addMovie("\"Metro\" released in Year 2006 for Genre \"Romance\"");
        listTopNByCritics("Romance", 3);
        moviesImpl.addUser("SRK");
        moviesImpl.addUser("Salman");
        moviesImpl.addUser("Deepika");
        moviesImpl.addReview("SRK", "Don", 2);
        moviesImpl.addReview("SRK", "Padmaavat", 8);
        moviesImpl.addReview("Salman", "Don", 5);
        moviesImpl.addReview("Deepika", "Don", 9);
        moviesImpl.addReview("Deepika", "Guru", 6);
        moviesImpl.addReview("SRK", "Don", 10);
        moviesImpl.addReview("Deepika", "Lunchbox", 5);
        moviesImpl.addReview("SRK", "Tiger", 5);
        moviesImpl.addReview("SRK", "Metro", 7);
        moviesImpl.getAvgReviewScoreByYear("2006");
        moviesImpl.getAvgReviewScore("Don");
        listTopNByCritics("comedy", 4);
    }

    private static void addMovie(String sentence) {
        String[] arr = sentence.split(" ");

        String movie = arr[0].substring(1, arr[0].length() - 1);
        String year = arr[4];
        List<String> genres = new ArrayList<>();
        for (int i = 7; i < arr.length; i += 2) {
            genres.add(arr[i].substring(1, arr[i].length() - 1).toLowerCase());
        }

        moviesImpl.addMovie(movie, year, genres);
    }

    private static void listTopNByCritics(String genre, int n) {
        moviesImpl.listTopNByCritics(genre.toLowerCase(), n);
    }
}
