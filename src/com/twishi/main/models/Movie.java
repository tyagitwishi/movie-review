package com.twishi.main.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Movie {
    private String name;
    private String year;
    private List<String> genres;
    private Integer reviewByCritics;
    private Integer reviewScore;
    private Integer totalReviews;

    public Movie addMovie(String movieName, String year, List<String> genres) {
        Movie movie = new Movie();
        movie.setGenres(genres);
        movie.setYear(year);
        movie.setName(movieName);
        movie.setReviewByCritics(0);
        movie.setReviewScore(0);
        movie.setTotalReviews(0);
        return movie;
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

    public float getAverage(Movie movie) {
        return (float) movie.getReviewScore() / (float) movie.getTotalReviews();
    }
}
