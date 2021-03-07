package com.twishi.main.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private String username;
    private String level;
    private List<String> moviesReviewed;

    public User addUser(String userName) {
        User user=new User();
        user.setUsername(userName);
        user.setLevel("viewer");
        user.setMoviesReviewed(new ArrayList<>());
        return user;
    }

    public User addMovieReview(User user, String movieName) {
        List<String> moviesList = user.getMoviesReviewed();
        moviesList.add(movieName);
        user.setMoviesReviewed(moviesList);
        return user;
    }
}
