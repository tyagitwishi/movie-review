package com.twishi.main;

import com.twishi.main.models.Constants;
import com.twishi.main.models.Movie;
import com.twishi.main.models.User;

import java.text.DecimalFormat;
import java.util.*;

public class MoviesImpl {

    DecimalFormat df = new DecimalFormat("0.##");

    private static HashMap<String, Movie> moviesMap = new LinkedHashMap<>();
    private static Movie movie = new Movie();

    private static HashMap<String, List<String>> yearMap = new LinkedHashMap<>();
    private static HashMap<String, List<String>> genreMap = new LinkedHashMap<>();

    private static HashMap<String, User> userMap = new HashMap<>();
    private static User user = new User();

    public void addMovie(String movieName, String year, List<String> genres) {
        if (checkIfMovieReleased(movieName)) {
            System.out.println("Movie with this name has already released in year " + moviesMap.get(movieName).getYear());
            return;
        }

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
    }

    public void addUser(String userName) {
        if (checkIfUserExists(userName)) {
            System.out.println("User already exists");
            return;
        }
        userMap.put(userName, user.addUser(userName));
    }

    private boolean checkIfUserExists(String userName) {
        return userMap.containsKey(userName);
    }

    private boolean checkIfMovieReleased(String movieName) {
        return moviesMap.containsKey(movieName);
    }

    public void addReview(String userName, String movieName, int score) {
        if (!checkIfUserExists(userName)) {
            System.out.println("User is not added to the system yet");
            return;
        }
        if (!checkIfMovieReleased(movieName)) {
            System.out.println("Movie has not been released yet");
            return;
        }
        if (userMap.containsKey(userName) && userMap.get(userName).getMoviesReviewed().contains(movieName)) {
            System.out.println("Multiple reviews by a user on a movie are not allowed");
            return;
        }

        User currentUser = userMap.get(userName);

        //add review score to movie
        boolean critic = currentUser.getLevel().equals("critic");
        movie.addReview(moviesMap.get(movieName), critic, score);

        //add movie to reviewed list for the user
        currentUser = user.addMovieReview(currentUser, movieName);

        updateUserStatus(userName, currentUser.getMoviesReviewed().size());
    }

    private void updateUserStatus(String userName, int reviewCount) {
        if (reviewCount == Constants.criticReviewCount) {
            userMap.get(userName).setLevel("critic");
        } else if (reviewCount == Constants.expertReviewCount) {
            userMap.get(userName).setLevel("expert");
        } else if (reviewCount == Constants.adminReviewCount) {
            userMap.get(userName).setLevel("admin");
        } else {
            return;
        }
        System.out.println(userName + " has published " + reviewCount
                + " reviews, promoted to " + userMap.get(userName).getLevel() + " now");

    }

    public void getAvgReviewScoreByYear(String year) {
        if (!yearMap.containsKey(year)) {
            System.out.println("No movie added which was released in the year " + year);
            return;
        }

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

    public void getAvgReviewScore(String movieName) {
        if (!checkIfMovieReleased(movieName)) {
            System.out.println(movieName + " movie has not been released yet");
            return;
        }
        System.out.println("Average review score for " + movieName + " is "
                + df.format(movie.getAverage(moviesMap.get(movieName))));
    }

    public void listTopNByCritics(String genre, int n) {
        if (!genreMap.containsKey(genre)) {
            System.out.println("No movies of genre " + genre + " has been released yet");
            return;
        }

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (String movies : genreMap.get(genre)) {
            temp.put(movies, moviesMap.get(movies).getReviewByCritics());
        }
        temp = sortByValue(temp);

        for (HashMap.Entry<String, Integer> en : temp.entrySet()) {
            if (en.getValue() > 0) {
                System.out.println(en.getKey());
                n--;
                if (n == 0) break;
            }
        }
        if (n > 0) {
            System.out.println("No movie of this genre has been reviewed by critics yet");
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
