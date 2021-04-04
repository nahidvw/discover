package com.example.doordashdiscover.responses;

import com.example.doordashdiscover.models.RestaurantDetails;

public class RestaurantDetailsResponse {
    private RestaurantDetails restaurantDetails;

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    @Override
    public String toString() {
        return "RestaurantDetailsResponse{" +
                "restaurantDetails=" + restaurantDetails +
                '}';
    }
}
