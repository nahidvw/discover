package com.example.doordashdiscover.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doordashdiscover.models.Restaurant;

import java.util.List;

public class RestaurantApiClient {
    private static RestaurantApiClient instance;
    private MutableLiveData<List<Restaurant>> mRestaurants;

    public static RestaurantApiClient getInstance() {
        if(instance == null) {
            instance = new RestaurantApiClient();
        }
        return instance;
    }

    private RestaurantApiClient() {
        mRestaurants = new MutableLiveData<>();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurants;
    }
}
