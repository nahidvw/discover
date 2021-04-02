package com.example.doordashdiscover.responses;

import com.example.doordashdiscover.models.Restaurant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantResponse {
    @SerializedName("num_results")
    @Expose()
    private int count;

    @SerializedName("stores")
    @Expose()
    private List<Restaurant> restaurants;

    public int getCount() {
        return count;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    @Override
    public String toString() {
        return "RestaurantResponse{" +
                "count=" + count +
                ", restaurants=" + restaurants +
                '}';
    }
}
