package com.twishi.main;

import com.twishi.main.service.ReviewService;

public class main {

    private static ReviewService reviewService = new ReviewService();

    public static void main(String[] args) {

        reviewService.addMovie("\"Don\" released in Year 2006 for Genres \"Action\" & \"Comedy\"");
        reviewService.addMovie("\"Tiger\" released in year 2008 for Genre \"Drama\"");
        reviewService.addMovie("\"Padmaavat\" released in Year 2006 for Genre \"Comedy\"");
        reviewService.addMovie("\"Lunchbox\" released in Year 2021 for Genre \"Drama\"");
        reviewService.addMovie("\"Guru\" released in Year 2006 for Genre \"Drama\"");
        reviewService.addMovie("\"Metro\" released in Year 2006 for Genre \"Romance\"");
        reviewService.listTopNByCritics("Romance", 3);
        reviewService.addUser("SRK");
        reviewService.addUser("Salman");
        reviewService.addUser("Deepika");
        reviewService.addReview("SRK", "Don", 2);
        reviewService.addReview("SRK", "Padmaavat", 8);
        reviewService.addReview("Salman", "Don", 5);
        reviewService.addReview("Deepika", "Don", 9);
        reviewService.addReview("Deepika", "Guru", 6);
        reviewService.addReview("SRK", "Don", 10);
        reviewService.addReview("Deepika", "Lunchbox", 5);
        reviewService.addReview("SRK", "Tiger", 5);
        reviewService.addReview("SRK", "Metro", 7);
        reviewService.getAvgReviewScoreByYear("2006");
        reviewService.getAvgReviewScore("Don");
        reviewService.listTopNByCritics("comedy", 4);
    }
}
