package com.example.doordashdiscover.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.requests.RestaurantApiClient;

import java.util.List;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private RestaurantApiClient mRestaurantApiClient;

    public static RestaurantRepository getInstance() {
        if(instance == null){
            instance = new RestaurantRepository();
        }
        return instance;
    }

    private RestaurantRepository() {
        mRestaurantApiClient = RestaurantApiClient.getInstance();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurantApiClient.getRestaurants();
    }
}
