package com.twishi.main.service.impl;

import com.twishi.main.models.Movie;
import com.twishi.main.models.User;

import java.text.DecimalFormat;
import java.util.*;

public class ReviewImpl {
    DecimalFormat df = new DecimalFormat("0.##");

    private MoviesImpl moviesImpl = new MoviesImpl();
    private UserImpl userImpl = new UserImpl();

    public void addReview(String userName, String movieName, int score,
                          HashMap<String, User> userMap,
                          HashMap<String, Movie> moviesMap) {
        User currentUser = userMap.get(userName);

        //add review score to movie
        boolean critic = currentUser.getLevel().equals("critic");
        moviesImpl.addReview(moviesMap.get(movieName), critic, score);

        //add movie to reviewed list for the user
        currentUser = userImpl.addMoviesReviewed(currentUser, movieName);

        //update user status
        userImpl.updateUserStatus(userName, currentUser.getMoviesReviewed().size(), userMap);
    }

    public void getAvgReviewScoreByYear(String year,
                                        HashMap<String, List<String>> yearMap,
                                        HashMap<String, Movie> moviesMap) {
        Integer totalScore = 0;
        Integer totalReviews = 0;
        for (String movies : yearMap.get(year)) {
            totalScore += moviesMap.get(movies).getReviewScore();
            totalReviews += moviesMap.get(movies).getTotalReviews();
        }

        if (totalReviews == 0) {
            System.out.println("No reviews added for movies for the year " + year);
        } else {
            System.out.println("Average review score for year " + year + " is "
                    + df.format((float) totalScore / (float) totalReviews));
        }
    }

    public void getAvgReviewScore(String movieName, HashMap<String, Movie> moviesMap) {
        Movie movie = new Movie();
        System.out.println("Average review score for " + movieName + " is "
                + df.format(movie.getAverage(moviesMap.get(movieName))));
    }

    public void listTopNByCritics(String genre, int n,
                                  HashMap<String, Movie> moviesMap,
                                  HashMap<String, List<String>> genreMap) {
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (String movies : genreMap.get(genre)) {
            temp.put(movies, moviesMap.get(movies).getReviewByCritics());
        }
        int i = 0;
        temp = sortByValue(temp);

        List<String> moviesList = new ArrayList<>();

        for (HashMap.Entry<String, Integer> en : temp.entrySet()) {
            if (en.getValue() > 0) {
                moviesList.add(en.getKey());
                i++;
                if (n == i) break;
            } else {
                break;
            }
        }
        if (i == 0) {
            System.out.println("No movie of this genre has been reviewed by critics yet");
        } else {
            System.out.println("These are the top " + i + " movies reviewed by critics for the genre " + genre + ": " + moviesList);
        }
    }

    private HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        List<HashMap.Entry<String, Integer>> list =
                new LinkedList<HashMap.Entry<String, Integer>>(hm.entrySet());

        Collections.sort(list, new Comparator<HashMap.Entry<String, Integer>>() {
            public int compare(HashMap.Entry<String, Integer> o1,
                               HashMap.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (HashMap.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
