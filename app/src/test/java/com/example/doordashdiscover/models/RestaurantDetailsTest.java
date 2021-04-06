package com.example.doordashdiscover.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RestaurantDetailsTest {
    @Test
    void isDisplayRatingEqual_returnTrue() {
        //Arrange
        RestaurantDetails restaurantDetails = new RestaurantDetails();
        restaurantDetails.setAverage_rating(3);
        restaurantDetails.setNumber_of_ratings(245);
        System.out.println(restaurantDetails.getDisplayRating());

        //Assert
        assertEquals(restaurantDetails.getDisplayRating(), "3.0 / 5.0 (245 ratings)");

        restaurantDetails.setAverage_rating(1.0);
        assertEquals(restaurantDetails.getDisplayRating(), "1.0 / 5.0 (245 ratings)");

        restaurantDetails.setNumber_of_ratings(45000);
        assertEquals(restaurantDetails.getDisplayRating(), "1.0 / 5.0 (45000 ratings)");
    }

    @Test
    void isDisplayRatingNotEqual_returnTrue() {
        //Arrange
        RestaurantDetails restaurantDetails = new RestaurantDetails();
        restaurantDetails.setAverage_rating(3);
        restaurantDetails.setNumber_of_ratings(245);
        System.out.println(restaurantDetails.getDisplayRating());

        //Assert
        assertNotEquals(restaurantDetails.getDisplayRating(), "3 / 5.0 (245 ratings)");
        assertNotEquals(restaurantDetails.getDisplayRating(), "3.0 / 5.0 (245 rating)");
        assertNotEquals(restaurantDetails.getDisplayRating(), "3.0 / 5 (245 ratings)");
    }
}
