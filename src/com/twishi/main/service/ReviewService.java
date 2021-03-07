package com.twishi.main.service;

import com.twishi.main.models.Movie;
import com.twishi.main.models.User;
import com.twishi.main.service.impl.MoviesImpl;
import com.twishi.main.service.impl.ReviewImpl;
import com.twishi.main.service.impl.UserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReviewService {

    private MoviesImpl moviesImpl = new MoviesImpl();
    private UserImpl userImpl = new UserImpl();
    private ReviewImpl reviewImpl = new ReviewImpl();

    private static HashMap<String, Movie> moviesMap = new LinkedHashMap<>();
    private static HashMap<String, User> userMap = new HashMap<>();
    private static HashMap<String, List<String>> yearMap = new LinkedHashMap<>();
    private static HashMap<String, List<String>> genreMap = new LinkedHashMap<>();

    public void addMovie(String sentence) {
        try {
            Pattern moviePattern = Pattern.compile("\"([^\"]*)\" released");
            Matcher m = moviePattern.matcher(sentence);
            String movieName = "";
            if (m.find()) {
                movieName = m.group(1);
            }

            Pattern yearPattern = Pattern.compile("[Y,y]ear (\\d{4})");
            m = yearPattern.matcher(sentence);
            String year = "";
            if (m.find()) {
                year = m.group(1);
            }

            sentence = sentence.substring(sentence.indexOf("for"));

            Pattern genrePattern = Pattern.compile("\"([^\"]*)\"");
            m = genrePattern.matcher(sentence);
            List<String> genres = new ArrayList<>();
            while (m.find()) {
                genres.add(m.group(1).toLowerCase());
            }

            addMovie(movieName, year, genres);
        } catch (Exception ex) {
            System.out.println("Not in the correct format");
        }
    }

    private void addMovie(String movieName, String year, List<String> genres) {
        if (moviesMap.containsKey(movieName)) {
            System.out.println("Movie with this name has already released in year " + moviesMap.get(movieName).getYear());
            return;
        }

        moviesImpl.addMovie(movieName, year, genres, moviesMap, yearMap, genreMap);
    }

    public void addUser(String userName) {
        if (userMap.containsKey(userName)) {
            System.out.println("User already exists");
            return;
        }

        userImpl.addUser(userName, userMap);
    }

    public void addReview(String userName, String movieName, int score) {
        if (!userMap.containsKey(userName)) {
            System.out.println("User is not added to the system yet");
            return;
        }
        if (!moviesMap.containsKey(movieName)) {
            System.out.println("Movie has not been released yet");
            return;
        }
        if (userMap.containsKey(userName) && userMap.get(userName).getMoviesReviewed().contains(movieName)) {
            System.out.println("Multiple reviews by a user on a movie are not allowed");
            return;
        }
        if (score < 1 || score > 10) {
            System.out.println("Review score entered is not between 0 and 10");
            return;
        }

        reviewImpl.addReview(userName, movieName, score, userMap, moviesMap);
    }

    public void getAvgReviewScoreByYear(String year) {
        if (!yearMap.containsKey(year)) {
            System.out.println("No movie added which was released in the year " + year);
            return;
        }

        reviewImpl.getAvgReviewScoreByYear(year, yearMap, moviesMap);
    }

    public void getAvgReviewScore(String movieName) {
        if (!moviesMap.containsKey(movieName)) {
            System.out.println(movieName + " movie has not been released yet");
            return;
        }
        reviewImpl.getAvgReviewScore(movieName, moviesMap);
    }

    public void listTopNByCritics(String genre, int n) {
        genre = genre.toLowerCase();
        if (!genreMap.containsKey(genre)) {
            System.out.println("No movies of genre " + genre + " have been released yet");
            return;
        }

        reviewImpl.listTopNByCritics(genre, n, moviesMap, genreMap);
    }
}
