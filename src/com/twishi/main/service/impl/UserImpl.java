package com.twishi.main.service.impl;

import com.twishi.main.models.Constants;
import com.twishi.main.models.User;

import java.util.HashMap;
import java.util.List;

public class UserImpl {

    public void addUser(String userName, HashMap<String, User> userMap) {
        User user = new User();
        userMap.put(userName, user.addUser(userName));
    }

    public User addMoviesReviewed(User user, String movieName) {
        List<String> moviesList = user.getMoviesReviewed();
        moviesList.add(movieName);
        user.setMoviesReviewed(moviesList);
        return user;
    }

    public void updateUserStatus(String userName, int reviewCount, HashMap<String, User> userMap) {
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
}
