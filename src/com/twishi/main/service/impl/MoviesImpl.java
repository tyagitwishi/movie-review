package com.twishi.main.service.impl;

import com.twishi.main.models.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MoviesImpl {

    public void addMovie(String movieName, String year, List<String> genres,
                         HashMap<String, Movie> moviesMap,
                         HashMap<String, List<String>> yearMap,
                         HashMap<String, List<String>> genreMap) {

        Movie movie = new Movie();
        moviesMap.put(movieName, movie.addMovie(movieName, year, genres));

        //add movie to the year map
        if (!yearMap.containsKey(year)) {
            yearMap.put(year, new ArrayList<>(Arrays.asList(movieName)));
        } else {
            yearMap.get(year).add(movieName);
        }

        //add movie to the genre map
        for (String genre : genres) {
            if (!genreMap.containsKey(genre)) {
                genreMap.put(genre, new ArrayList<>(Arrays.asList(movieName)));
            } else {
                genreMap.get(genre).add(movieName);
            }
        }
        System.out.println(movieName + ", " + year + " added");
    }

    public Movie addReview(Movie movie, boolean critic, int review) {
        if (critic) {
            movie.setReviewScore(movie.getReviewScore() + 2 * review);
            movie.setReviewByCritics(movie.getReviewByCritics() + review);
        } else {
            movie.setReviewScore(movie.getReviewScore() + review);
        }
        movie.setTotalReviews(movie.getTotalReviews() + 1);

        return movie;
    }
}
